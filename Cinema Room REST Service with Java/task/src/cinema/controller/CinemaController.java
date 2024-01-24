package cinema.controller;

import cinema.dto.CinemaDTO;
import cinema.dto.SeatDTO;
import cinema.model.Cinema;
import cinema.model.Seat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CinemaController {

    private final Cinema cinema;

    public CinemaController() {
        int totalRows = 9;
        int totalColumns = 9;
        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= totalRows; row++) {
            for (int col = 1; col <= totalColumns; col++) {
                seats.add(new Seat(row, col));
            }
        }

        this.cinema = new Cinema(totalRows, totalColumns, seats);
    }

    @GetMapping("/seats")
    public CinemaDTO getSeats() {
        List<SeatDTO> seatDTOs = cinema.getSeats().stream()
                .map(seat -> new SeatDTO(seat.getRow(), seat.getColumn(), seat.getPrice()))
                .collect(Collectors.toList());
        return new CinemaDTO(cinema.getRows(), cinema.getColumns(), seatDTOs);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat requestSeat) {
        Seat seat = cinema.findSeat(requestSeat.getRow(), requestSeat.getColumn());

        if (seat == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "The number of a row or a column is out of bounds!"));
        }

        if (!seat.isPurchased()) {
            String token = cinema.purchaseSeat(seat.getRow(), seat.getColumn());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "ticket", new SeatDTO(seat.getRow(), seat.getColumn(), seat.getPrice())
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "The ticket has been already purchased!"));
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnSeat(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        Seat seat = cinema.returnSeat(token);

        if (seat != null) {
            return ResponseEntity.ok(Map.of(
                    "ticket", new SeatDTO(seat.getRow(), seat.getColumn(), seat.getPrice())
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Wrong token!"));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(value = "password", required = false) String password) {
        final String correctPassword = "super_secret";

        if (password == null || !password.equals(correctPassword)) {
            return ResponseEntity.status(401).body(Map.of("error", "The password is wrong!"));
        }

        int income = cinema.calculateIncome();
        int availableSeats = cinema.countAvailableSeats();
        int purchasedTickets = cinema.countPurchasedTickets();

        return ResponseEntity.ok(Map.of(
                "income", income,
                "available", availableSeats,
                "purchased", purchasedTickets
        ));
    }
}
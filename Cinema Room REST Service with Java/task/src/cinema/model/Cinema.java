package cinema.model;

import java.util.List;
import java.util.UUID;

public class Cinema {
    private int rows;
    private int columns;
    private List<Seat> seats;

    public Cinema(int rows, int columns, List<Seat> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public Seat findSeat(int row, int column) {
        return seats.stream()
                .filter(seat -> seat.getRow() == row && seat.getColumn() == column)
                .findFirst()
                .orElse(null);
    }

    public String purchaseSeat(int row, int column) {
        Seat seat = findSeat(row, column);
        if (seat != null && !seat.isPurchased()) {
            seat.setPurchased(true);
            String token = UUID.randomUUID().toString();
            seat.setToken(token);
            return token;
        }
        return null;
    }

    public Seat returnSeat(String token) {
        for (Seat seat : seats) {
            if (seat.getToken() != null && seat.getToken().equals(token)) {
                seat.setPurchased(false);
                seat.setToken(null);
                return seat;
            }
        }
        return null;
    }

    public int calculateIncome() {
        return seats.stream()
                .filter(Seat::isPurchased)
                .mapToInt(Seat::getPrice)
                .sum();
    }

    public int countAvailableSeats() {
        return (int) seats.stream()
                .filter(seat -> !seat.isPurchased())
                .count();
    }

    public int countPurchasedTickets() {
        return seats.size() - countAvailableSeats();
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
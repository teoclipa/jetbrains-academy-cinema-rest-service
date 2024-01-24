# Cinema Ticketing System

This Java Spring Boot project implements a RESTful service for managing a cinema ticketing system. The service provides endpoints for viewing available seats, purchasing tickets, processing refunds, and accessing cinema statistics. It's designed to simulate a real-world scenario of a cinema hall, showcasing various aspects of Spring Boot and REST API development.

## Features

The cinema ticketing system offers the following features:

### View Available Seats
- **Endpoint**: `GET /seats`
- **Description**: Retrieves the current seating arrangement of the cinema. It shows all seats, marking them as either available or purchased, along with the pricing strategy.

### Purchase Tickets
- **Endpoint**: `POST /purchase`
- **Request Body**: Contains the selected seat's row and column.
- **Response**: Returns a unique token for the purchased ticket along with the seat details. If the seat is already purchased or the seat details are invalid, an appropriate error message is returned.
- **Functionality**: Allows customers to purchase tickets for available seats. Each ticket purchase is uniquely identified by a generated token.

### Ticket Refund
- **Endpoint**: `POST /return`
- **Request Body**: Requires the token of the purchased ticket.
- **Response**: On successful processing, it returns the details of the refunded ticket. If the token is invalid, it returns an error message.
- **Functionality**: Provides the ability to refund tickets. The token is used to validate and process the refund.

### Cinema Statistics
- **Endpoint**: `GET /stats`
- **Parameters**: Requires a `password` parameter for authentication.
- **Response**: On successful authentication, it returns the cinema's statistics, including total income, number of available seats, and number of purchased tickets. If the authentication fails, it returns an error message.
- **Functionality**: Allows authorized personnel (e.g., theatre managers) to view cinema statistics. The endpoint is secured with a password.

## Security and Authentication
The `/stats` endpoint is secured with basic authentication, requiring a predefined password (`super_secret`) to access sensitive cinema statistics.

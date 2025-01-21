package main.java.controller;


import main.java.service.*;
import main.java.service.impl.*;


public class UserController {
    private CinemaService cinemaService;
    private MovieService movieService;
    private ShowingService showingService;
    private SeatService seatService;
    private TicketService ticketService;
    private UserService userService;
    public UserController() {
        movieService = new MovieServiceImpl();
        showingService = new ShowingServiceImpl();
        seatService = new SeatServiceImpl();
        ticketService = new TicketServiceImpl();
        cinemaService = new CinemaServiceImpl();
        userService = new UserServiceImpl();
    }
}
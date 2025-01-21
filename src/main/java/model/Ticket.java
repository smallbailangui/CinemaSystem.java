package main.java.model;


public class Ticket {
    private int ticketId;
    private int seatId;
    private int userId;
    private int showingId;
    private String paymentTime;

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPayment_time(String payment_time) {
        this.paymentTime = payment_time;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    private int ticketPrice;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowingId() {
        return showingId;
    }

    public void setShowingId(int showingId) {
        this.showingId = showingId;
    }
}

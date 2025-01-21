package main.java.model;


public class Seat {
    private int seatId;
    private int isAvailable;
    private int showingId;
    private int seat_tpye;
    private String seatNumber;



    public void setSeat_type(int seat_tpye) {
        this.seat_tpye = seat_tpye;
    }

    public int getSeat_type() {
        return seat_tpye;
    }

    // Getters and Setters
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int isAvailable() {
        return isAvailable;
    }

    public void setAvailable(int available) {
        isAvailable = available;
    }

    public int getShowingId() {
        return showingId;
    }

    public void setShowingId(int showingId) {
        this.showingId = showingId;
    }


}

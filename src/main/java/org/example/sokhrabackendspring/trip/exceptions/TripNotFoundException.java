package org.example.sokhrabackendspring.trip.exceptions;

public class TripNotFoundException extends Exception {
  public TripNotFoundException() {
    super("Trip not found");
  }
}

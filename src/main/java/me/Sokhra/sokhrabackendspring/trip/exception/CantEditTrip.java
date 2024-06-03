package org.example.sokhrabackendspring.trip.exception;

public class CantEditTrip extends Exception {
  public CantEditTrip() {
    super("Can't edit trip");
  }
}

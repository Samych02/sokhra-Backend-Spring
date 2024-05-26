package org.example.sokhrabackendspring.trip.exceptions;

public class CantEditTrip extends Exception {
  public CantEditTrip() {
    super("Can't edit trip");
  }
}

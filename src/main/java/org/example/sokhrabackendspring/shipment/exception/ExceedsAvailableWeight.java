package org.example.sokhrabackendspring.shipment.exception;

public class ExceedsAvailableWeight extends Exception {
  public ExceedsAvailableWeight() {
    super("Exceeds available weight");
  }
}

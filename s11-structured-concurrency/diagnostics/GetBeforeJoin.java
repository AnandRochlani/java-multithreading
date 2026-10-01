// Asking a card for its answer before join (java --enable-preview).
void main() throws Exception {
    try (var booking = StructuredTaskScope.open()) {
        var seat = booking.fork(() -> "S7-42");
        Thread.sleep(100);                                           // the card has certainly finished by now
        IO.println("seat: " + seat.get());                           // but main has not joined
        booking.join();
    }
}

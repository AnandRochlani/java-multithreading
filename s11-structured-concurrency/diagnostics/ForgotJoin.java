// Forking and never joining (java --enable-preview): the closing brace notices.
void main() throws Exception {
    try (var booking = StructuredTaskScope.open()) {
        booking.fork(() -> "S7-42");
        IO.println("forked, and walking away without join");
    }
    IO.println("never printed?");
}

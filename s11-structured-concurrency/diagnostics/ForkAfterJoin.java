// A card handed in after join (java --enable-preview).
void main() throws Exception {
    try (var booking = StructuredTaskScope.open()) {
        booking.fork(() -> "S7-42");
        booking.join();
        booking.fork(() -> "one more card");
    }
}

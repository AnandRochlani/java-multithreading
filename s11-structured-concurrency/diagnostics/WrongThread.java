// Only the thread that opened the scope may fork, join or close it (java --enable-preview).
void main() throws Exception {
    try (var booking = StructuredTaskScope.open()) {
        Thread helper = Thread.ofVirtual().start(() -> {
            try {
                booking.fork(() -> "S7-42");                         // a different thread tries to fork
            } catch (RuntimeException e) {
                IO.println("helper's fork threw: " + e);
            }
        });
        helper.join();
        booking.join();
        IO.println("the owner's join still works");
    }
}

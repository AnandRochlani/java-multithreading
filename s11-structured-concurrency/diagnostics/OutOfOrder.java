// Two scopes, closed in the wrong order without try-with-resources (java --enable-preview).
void main() throws Exception {
    var booking = StructuredTaskScope.open();
    var quotes = StructuredTaskScope.open();                         // opened inside the booking
    quotes.fork(() -> { Thread.sleep(1_000); return 1250; });
    quotes.join();
    try {
        booking.close();                                             // the outer scope, while the inner is still open
    } catch (RuntimeException e) {
        IO.println("booking.close() threw: " + e);
    }
    IO.println("quotes cancelled? " + quotes.isCancelled());
}

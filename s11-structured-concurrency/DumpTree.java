// A named booking scope, and a fare card that opens a scope of its own; everyone naps 5 s so jcmd can dump the tree (java --enable-preview).
void main() throws Exception {
    IO.println("pid " + ProcessHandle.current().pid() + ", dump me now");
    try (var booking = StructuredTaskScope.open(cf -> cf.withName("booking S7-42")
            .withThreadFactory(Thread.ofVirtual().name("booking-card-", 1).factory()))) {
        booking.fork(() -> { Thread.sleep(5_000); return "S7-42"; });                 // booking-card-1: the seat
        booking.fork(() -> {                                                           // booking-card-2: the fare,
            try (var quotes = StructuredTaskScope.open(cf -> cf.withName("fare quotes")   // with a scope of its own
                    .withThreadFactory(Thread.ofVirtual().name("quote-", 1).factory()))) {
                quotes.fork(() -> { Thread.sleep(5_000); return 1250; });
                quotes.fork(() -> { Thread.sleep(5_000); return 1300; });
                quotes.join();
            }
            return 1250;
        });
        booking.join();
    }
    IO.println("booked");
}

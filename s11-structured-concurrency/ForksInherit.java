// Who can read the binding (java --enable-preview): a scope's forks inherit it; a desk's card and a thread you start do not.
static final ScopedValue<String> PASSENGER = ScopedValue.newInstance();

void main() throws Exception {
    String who = ScopedValue.where(PASSENGER, "Sophie").call(() -> booking());   // call, because booking() throws
    IO.println("booking() returned " + who + "; after the call, bound? " + PASSENGER.isBound());
}

static String booking() throws Exception {
    try (var booking = StructuredTaskScope.open()) {                       // opened while Sophie is bound
        var seat = booking.fork(() -> PASSENGER.orElse("(not bound)"));
        booking.join();
        IO.println("a fork in the scope reads:        " + seat.get());
    }
    try (var desk = Executors.newVirtualThreadPerTaskExecutor()) {         // Section 10's virtual desk
        var slip = desk.submit(() -> PASSENGER.orElse("(not bound)"));
        IO.println("a card on the virtual desk reads: " + slip.get());
    }
    Thread clerk = Thread.ofVirtual().start(() ->                         // a clerk hired by hand
            IO.println("a thread started by hand reads:   " + PASSENGER.orElse("(not bound)")));
    clerk.join();
    return PASSENGER.get();
}

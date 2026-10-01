// A value bound for one call (ScopedValue, final since Java 25: no flag). Sophie's name reaches a method two calls down.
static final ScopedValue<String> PASSENGER = ScopedValue.newInstance();

void main() {
    ScopedValue.where(PASSENGER, "Sophie").run(() -> book());             // bound for this one call
    IO.println("after the call: bound? " + PASSENGER.isBound() + ", orElse: " + PASSENGER.orElse("nobody"));
    IO.println(PASSENGER.get());                                           // nothing is bound here
}

static void book() {                                                       // book never mentions a passenger
    printTicket();
}

static void printTicket() {
    IO.println("ticket S7-42 for " + PASSENGER.get() + ", bound? " + PASSENGER.isBound());
}

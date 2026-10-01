// The Thread Local way to pass the passenger (no flag): anyone can set it, and it stays after the call unless removed.
static final ThreadLocal<String> PASSENGER = new ThreadLocal<>();

void main() {
    PASSENGER.set("Sophie");
    book();
    IO.println("after the call: " + PASSENGER.get());                     // still there: nobody removed it
}

static void book() {
    PASSENGER.set("Tom");                                                   // a faraway method changes it
    IO.println("ticket S7-42 for " + PASSENGER.get());
}

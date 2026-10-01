// Rebinding for one inner call (no flag): the inner call sees the new value, and the old one comes back when it returns.
static final ScopedValue<String> PASSENGER = ScopedValue.newInstance();

void main() {
    ScopedValue.where(PASSENGER, "Sophie").run(() -> {
        IO.println("booking for " + PASSENGER.get());
        String friend = ScopedValue.where(PASSENGER, "Ravi").call(() -> ticket());   // rebound for one inner call
        IO.println("  " + friend);
        IO.println("back to " + PASSENGER.get());
    });
}

static String ticket() {
    return "a ticket for " + PASSENGER.get();
}

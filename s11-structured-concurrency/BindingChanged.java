// A scope keeps the bindings it was opened with (java --enable-preview): fork under a different binding, and fork refuses.
static final ScopedValue<String> PASSENGER = ScopedValue.newInstance();

void main() throws Exception {
    ScopedValue.where(PASSENGER, "Sophie").call(() -> {
        try (var booking = StructuredTaskScope.open()) {                   // opened while Sophie is bound
            booking.fork(() -> "seat for " + PASSENGER.get());
            ScopedValue.where(PASSENGER, "Ravi").run(() ->
                    booking.fork(() -> "seat for " + PASSENGER.get()));     // forked while Ravi is bound
            booking.join();
        }
        return null;
    });
}

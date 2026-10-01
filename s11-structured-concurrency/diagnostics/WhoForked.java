// Who runs a forked card? (java --enable-preview) The default scope forks each card on a new virtual thread.
void main() throws Exception {
    IO.println("owner: " + Thread.currentThread());
    try (var booking = StructuredTaskScope.open()) {
        var seat = booking.fork(() -> "seat card on " + Thread.currentThread() + ", virtual? " + Thread.currentThread().isVirtual());
        var fare = booking.fork(() -> "fare card on " + Thread.currentThread() + ", virtual? " + Thread.currentThread().isVirtual());
        booking.join();
        IO.println(seat.get());
        IO.println(fare.get());
    }
}

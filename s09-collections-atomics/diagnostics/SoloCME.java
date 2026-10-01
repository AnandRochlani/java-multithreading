void main() {
    Map<String, String> register = new HashMap<>();
    for (int b = 1; b <= 5; b++) {
        register.put("booking-" + b, "window-1");
    }
    IO.println("the loop visits them in this order: " + register.keySet());
    try {
        for (String booking : register.keySet()) {                    // ONE thread: loop over the map...
            if (booking.equals("booking-3")) {
                register.remove(booking);                             // ...and change it under the loop
            }
        }
        IO.println("removed booking-3, no exception");
    } catch (ConcurrentModificationException e) {
        IO.println("removing booking-3 mid-loop threw: " + e);
    }
    String last = null;
    for (String booking : register.keySet()) {
        last = booking;                                               // whichever key the loop visits last
    }
    for (String booking : register.keySet()) {
        if (booking.equals(last)) {
            register.remove(booking);                                 // the same mistake, on the last key
        }
    }
    IO.println("removed " + last + " (the last key) mid-loop: no exception, map now " + register.keySet());
}

static final int BOOKINGS = 200_000;
static final Map<String, Integer> fares = Collections.synchronizedMap(new HashMap<>());   // one lock around every call
static volatile int written = 0;                                  // how far Meena has got (read without the lock)
static volatile int walks = 0;                                    // how many whole walks Tom has finished

void main() throws InterruptedException {
    Thread meena = new Thread(() -> {                             // Meena writes 200,000 fares
        for (int b = 1; b <= BOOKINGS; b++) {
            fares.put("booking-" + b, 1250);
            written = b;
        }
    }, "Meena");
    Thread tom = new Thread(() -> {                               // Tom only reads, walking the whole map again and again
        while (written < BOOKINGS) {
            long total = 0;
            synchronized (fares) {                                // the javadoc's rule: hold the map's own lock for the whole walk
                for (int fare : fares.values()) {
                    total += fare;
                }
            }
            walks++;
        }
    }, "Tom");
    meena.setDaemon(true);                                        // so main can walk away and the JVM still exits
    tom.setDaemon(true);
    meena.start();
    tom.start();
    for (int s = 1; s <= 3; s++) {
        Thread.sleep(1000);
        IO.println("after " + s + " s: " + written + " of " + BOOKINGS + " fares written, Tom has walked the map " + walks + " times, no exception");
    }
    IO.println("Meena: " + meena.getState() + ", Tom: " + tom.getState());
}

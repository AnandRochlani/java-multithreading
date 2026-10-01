static final AtomicInteger ticketsSold = new AtomicInteger();   // the turnstile: no key anywhere
static final int CLERKS = 10;
static final int PER_CLERK = 100_000;

static void sell() {                          // no synchronized
    ticketsSold.incrementAndGet();            // one click, and nobody can cut it in half
}

record BookingClerk(String window) implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < PER_CLERK; i++) {
            sell();
        }
    }
}

void main() throws InterruptedException {
    long start = System.currentTimeMillis();

    Thread[] clerks = new Thread[CLERKS];
    for (int i = 0; i < CLERKS; i++) {
        clerks[i] = new Thread(new BookingClerk("window-" + (i + 1)), "window-" + (i + 1));
        clerks[i].start();
    }
    for (Thread clerk : clerks) {
        clerk.join();                                  // every clerk has gone home
    }

    long elapsed = System.currentTimeMillis() - start;
    IO.println("expected : " + CLERKS * PER_CLERK);
    IO.println("register : " + ticketsSold.get());
    IO.println("time     : " + elapsed + " ms");
}

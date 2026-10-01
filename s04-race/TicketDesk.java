static int ticketsSold = 0;
static final int CLERKS = 10;
static final int PER_CLERK = 100_000;

static final Object DESK = new Object();      // a key, and nothing else

static void sell() {
    synchronized (DESK) {
        ticketsSold++;
    }
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
    IO.println("register : " + ticketsSold);
    IO.println("time     : " + elapsed + " ms");
}

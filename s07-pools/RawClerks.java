static final int PASSENGERS = 200;

static void card(int i) {
    nap(100);                                                 // the same card
    if (i % 50 == 0) IO.println("card " + i + " served by " + Thread.currentThread().getName());
}

void main() throws InterruptedException {
    long start = System.nanoTime();
    Thread[] clerks = new Thread[PASSENGERS];
    for (int i = 0; i < PASSENGERS; i++) {
        int n = i + 1;
        clerks[i] = new Thread(() -> card(n));                // a clerk per passenger
        clerks[i].start();
    }
    for (Thread clerk : clerks) clerk.join();
    IO.println(PASSENGERS + " cards with a clerk each took " + (System.nanoTime() - start) / 1_000_000 + " ms");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

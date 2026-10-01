// Roster200Virtual, printing the whole thread instead of its name
static final int PASSENGERS = 200;

static void card(int i) {
    nap(100);
    if (i % 50 == 0) IO.println("card " + i + " served by " + Thread.currentThread());
}

void main() {
    try (ExecutorService desk = Executors.newVirtualThreadPerTaskExecutor()) {
        for (int i = 1; i <= PASSENGERS; i++) {
            int n = i;
            desk.execute(() -> card(n));
        }
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

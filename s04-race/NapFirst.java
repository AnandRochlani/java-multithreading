static int ticketsSold = 0;

static void sellSlowly() {
    nap(50);                                      // moved: now before the read
    int seen = ticketsSold;
    ticketsSold = seen + 1;
}

void main() throws InterruptedException {
    Thread meena = new Thread(() -> sellSlowly(), "Meena");
    Thread tom   = new Thread(() -> sellSlowly(), "Tom");
    meena.start();
    tom.start();
    meena.join();
    tom.join();
    IO.println("2 tickets sold, register says " + ticketsSold);
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

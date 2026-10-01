static int ticketsSold = 0;

static synchronized void sellSlowly() {
    String me = Thread.currentThread().getName();
    int seen = ticketsSold;                       // read
    IO.println(me + " reads  " + seen);
    nap(50);                                      // the gap, stretched so we can watch it
    ticketsSold = seen + 1;                       // add, and write
    IO.println(me + " writes " + (seen + 1));
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

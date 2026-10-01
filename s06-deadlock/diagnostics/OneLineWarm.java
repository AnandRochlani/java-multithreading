static final Object CASH   = new Object();
static final Object LEDGER = new Object();

void main() throws InterruptedException {
    Thread meena = new Thread(() -> {
        synchronized (CASH) {
            IO.println("Meena: holds the cash key");         // one log line, nothing else changed
            synchronized (LEDGER) { }                        // take both keys, then leave
        }
    }, "Meena");
    Thread tom = new Thread(() -> {
        synchronized (LEDGER) {
            synchronized (CASH) { }                          // the same bug: the opposite order
        }
    }, "Tom");
    IO.println("opening the counters");                     // warm up printing before the clerks start
    meena.start();
    tom.start();
    meena.join();
    tom.join();
    IO.println("both clerks went home");
}

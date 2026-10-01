static final Object CASH   = new Object();
static final Object LEDGER = new Object();

void main() throws InterruptedException {
    Thread meena = new Thread(() -> {
        synchronized (CASH) {
            synchronized (LEDGER) { }                        // take both keys, then leave
        }
    }, "Meena");
    Thread tom = new Thread(() -> {
        synchronized (LEDGER) {
            synchronized (CASH) { }                          // the same bug: the opposite order
        }
    }, "Tom");
    meena.start();
    tom.start();
    meena.join();
    tom.join();
    IO.println("both clerks went home");
}

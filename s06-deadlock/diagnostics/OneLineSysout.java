static final Object CASH   = new Object();
static final Object LEDGER = new Object();

void main() throws InterruptedException {
    Thread meena = new Thread(() -> {
        synchronized (CASH) {
            System.out.println("Meena: holds the cash key");
            synchronized (LEDGER) { }
        }
    }, "Meena");
    Thread tom = new Thread(() -> {
        synchronized (LEDGER) {
            synchronized (CASH) { }
        }
    }, "Tom");
    meena.start();
    tom.start();
    meena.join();
    tom.join();
    IO.println("both clerks went home");
}

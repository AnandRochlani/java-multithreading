static final Object CASH   = new Object();
static final Object LEDGER = new Object();

void main() throws InterruptedException {
    Thread meena = new Thread(() -> {
        synchronized (CASH) {
            synchronized (LEDGER) {
                try {
                    LEDGER.wait();                            // hands back the ledger key, and only that one
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }, "Meena");
    Thread tom = new Thread(() -> {
        synchronized (LEDGER) {
            IO.println("Tom: got the ledger key");
        }
        synchronized (CASH) {
            IO.println("Tom: got the cash key");
        }
    }, "Tom");
    meena.start();
    Thread.sleep(100);                                        // Meena takes both keys and sits down to wait
    tom.start();
    Thread.sleep(100);
    IO.println("Meena: " + meena.getState() + ", Tom: " + tom.getState());
    tom.join();
    IO.println("Tom went home");
}

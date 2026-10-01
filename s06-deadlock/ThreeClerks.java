static final Object CASH   = new Object();
static final Object LEDGER = new Object();
static final Object STAMP  = new Object();                    // a third key: the rubber-stamp drawer

static void take(Object first, Object second) {
    synchronized (first) {
        nap(100);
        synchronized (second) { }
    }
}

void main() throws InterruptedException {
    Thread meena = new Thread(() -> take(CASH, LEDGER), "Meena");
    Thread tom   = new Thread(() -> take(LEDGER, STAMP), "Tom");
    Thread kavya = new Thread(() -> take(STAMP, CASH), "Kavya");
    meena.start();
    tom.start();
    kavya.start();
    Thread.sleep(500);
    IO.println("Meena: " + meena.getState() + ", Tom: " + tom.getState() + ", Kavya: " + kavya.getState());
    meena.join();
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

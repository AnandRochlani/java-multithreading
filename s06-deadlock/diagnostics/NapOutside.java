static final Object CASH   = new Object();                    // the one key to the cash drawer
static final Object LEDGER = new Object();                    // the one key to the ledger cupboard

static void sell(Object first, Object second) {               // take one key, then the other
    String me = Thread.currentThread().getName();
    nap(100);                                                 // the nap moved outside the lock
    synchronized (first) {
        IO.println(me + ": holds the " + name(first) + " key, wants the " + name(second) + " key");
        synchronized (second) {
            IO.println(me + ": sold a ticket");
        }
    }
}

void main() throws InterruptedException {
    Thread meena = new Thread(() -> sell(CASH, LEDGER), "Meena");   // money first
    Thread tom   = new Thread(() -> sell(LEDGER, CASH), "Tom");     // seat first
    meena.start();
    tom.start();
    Thread.sleep(500);
    IO.println("Meena: " + meena.getState() + ", Tom: " + tom.getState());
    meena.join();
    tom.join();
    IO.println("both clerks went home");
}

static String name(Object key) { return key == CASH ? "cash" : "ledger"; }

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

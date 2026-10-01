static final ReentrantLock CASH   = new ReentrantLock();      // a lock you hold in your hand, not a block
static final ReentrantLock LEDGER = new ReentrantLock();

static void sell(ReentrantLock first, ReentrantLock second) {
    String me = Thread.currentThread().getName();
    first.lock();                                             // outside the try: never unlock what you never got
    try {
        IO.println(me + ": holds the " + name(first) + " key, wants the " + name(second) + " key");
        nap(100);
        if (second.tryLock(1, TimeUnit.SECONDS)) {            // wait at most one second for the other key
            try {
                IO.println(me + ": sold a ticket");
            } finally {
                second.unlock();
            }
        } else {
            IO.println(me + ": gave up on the " + name(second) + " key, putting the " + name(first) + " key back");
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    } finally {
        first.unlock();                                       // however we leave, the first key goes back
    }
}

void main() throws InterruptedException {
    Thread meena = new Thread(() -> sell(CASH, LEDGER), "Meena");
    Thread tom   = new Thread(() -> sell(LEDGER, CASH), "Tom");     // still the opposite order
    meena.start();
    tom.start();
    meena.join();
    tom.join();
    IO.println("nobody is stuck");
}

static String name(ReentrantLock key) { return key == CASH ? "cash" : "ledger"; }

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

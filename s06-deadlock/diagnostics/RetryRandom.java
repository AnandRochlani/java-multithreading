static final ReentrantLock CASH   = new ReentrantLock();
static final ReentrantLock LEDGER = new ReentrantLock();
static final boolean RANDOM_BACKOFF = true;

static void sell(ReentrantLock first, ReentrantLock second) {
    String me = Thread.currentThread().getName();
    for (int attempt = 1; attempt <= 10; attempt++) {
        first.lock();
        try {
            nap(100);
            if (second.tryLock(100, TimeUnit.MILLISECONDS)) {
                try {
                    IO.println(me + ": sold a ticket on attempt " + attempt);
                    return;
                } finally {
                    second.unlock();
                }
            }
            IO.println(me + ": attempt " + attempt + " failed, backing off");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } finally {
            first.unlock();
        }
        nap(RANDOM_BACKOFF ? ThreadLocalRandom.current().nextInt(0, 100) : 50);
    }
    IO.println(me + ": gave up after 10 attempts");
}

void main() throws InterruptedException {
    Thread meena = new Thread(() -> sell(CASH, LEDGER), "Meena");
    Thread tom   = new Thread(() -> sell(LEDGER, CASH), "Tom");
    meena.start();
    tom.start();
    meena.join();
    tom.join();
}

static void nap(long ms) {
    try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}

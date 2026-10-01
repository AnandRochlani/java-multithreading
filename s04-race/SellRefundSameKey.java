static int ticketsSold = 0;
static final Object DESK = new Object();

static void sell() {
    synchronized (DESK) {                        // the same key as refund
        ticketsSold++;
    }
}

static void refund() {
    synchronized (DESK) {                        // locks DESK
        ticketsSold--;
    }
}

void main() throws InterruptedException {
    List<Thread> clerks = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
        clerks.add(new Thread(() -> { for (int n = 0; n < 100_000; n++) sell(); },   "seller-" + i));
        clerks.add(new Thread(() -> { for (int n = 0; n < 100_000; n++) refund(); }, "refunder-" + i));
    }
    for (Thread t : clerks) t.start();
    for (Thread t : clerks) t.join();
    IO.println("5 x 100000 sold, 5 x 100000 refunded, register says " + ticketsSold);
}

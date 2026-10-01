static final Object DESK = new Object();

static void sellSlowly() {
    synchronized (DESK) {
        nap(300);                                 // holds the key for 300 ms
    }
}

void main() throws InterruptedException {
    Thread[] clerks = new Thread[10];
    for (int i = 0; i < 10; i++) {
        clerks[i] = new Thread(() -> sellSlowly(), "window-" + (i + 1));
        clerks[i].start();
    }
    nap(100);
    for (Thread c : clerks) {
        IO.println(c.getName() + " : " + c.getState());
    }
    for (Thread c : clerks) {
        c.join();
    }
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

void main() throws InterruptedException {
    Thread worker = new Thread(() -> nap(3000), "Tom");
    worker.start();

    Thread waiter  = new Thread(() -> join(worker, 0),    "plain join");
    Thread timed   = new Thread(() -> join(worker, 1500), "join(1500)");
    waiter.start();
    timed.start();

    nap(300);
    IO.println("plain join  -> " + waiter.getState());
    IO.println("join(1500)  -> " + timed.getState());
    IO.println("worker      -> " + worker.getState());
}

void join(Thread t, long ms) {
    try {
        if (ms == 0) t.join(); else t.join(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

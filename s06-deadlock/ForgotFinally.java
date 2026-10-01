static final ReentrantLock CASH = new ReentrantLock();

void main() throws InterruptedException {
    Thread meena = new Thread(() -> {
        CASH.lock();
        IO.println("Meena: holds the cash key");
        if (true) throw new IllegalStateException("the till jammed");   // no finally, so no unlock
        CASH.unlock();
    }, "Meena");
    meena.start();
    meena.join();
    IO.println("Meena: " + meena.getState() + ", cash key locked: " + CASH.isLocked());

    Thread tom = new Thread(() -> {
        CASH.lock();
        IO.println("Tom: holds the cash key");
        CASH.unlock();
    }, "Tom");
    tom.start();
    Thread.sleep(300);
    IO.println("Tom: " + tom.getState());
    tom.join();
}

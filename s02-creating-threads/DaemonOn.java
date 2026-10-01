void main() {
    Thread clerk = new Thread(() -> {
        nap(1500);
        IO.println("Tom finished the late shift");
    }, "Tom");
    clerk.setDaemon(true);
    clerk.start();
    IO.println("main is done");
}

void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

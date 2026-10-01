static final Queue<String> tray = new ArrayDeque<>();

void main() throws InterruptedException {
    Thread ravi = new Thread(() -> {
        try { Thread.sleep(1000); } catch (InterruptedException e) { return; }
        synchronized (tray) { tray.add("slip #1"); }
    }, "Ravi");
    ravi.start();

    long checks = 0;
    String slip = null;
    while (slip == null) {                    // Meena checks, and checks, and checks
        synchronized (tray) {
            checks++;
            slip = tray.poll();
        }
    }
    IO.println("Meena got " + slip + " after " + String.format("%,d", checks) + " checks");
    ravi.join();
}

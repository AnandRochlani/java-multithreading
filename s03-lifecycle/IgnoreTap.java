void main() throws InterruptedException {
    Thread tom = new Thread(() -> {
        for (int round = 1; round <= 3; round++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                IO.println("Tom: tapped on the shoulder, ignoring it");   // swallowed
            }
            IO.println("Tom: still selling, round " + round);
        }
    }, "Tom");
    tom.start();
    Thread.sleep(100);
    tom.interrupt();
    tom.join();
}

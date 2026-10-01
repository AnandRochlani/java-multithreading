void main() throws InterruptedException {
    Thread tom = new Thread(() -> nap(3000), "Tom");
    tom.start();
    boolean done = tom.join(Duration.ofMillis(500));          // wait at most half a second
    IO.println("Tom finished within 500 ms? " + done + " (Tom is " + tom.getState() + ")");
}

void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

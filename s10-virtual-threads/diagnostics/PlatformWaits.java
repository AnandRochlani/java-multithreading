// NobodyWaits with the one word changed: a platform thread, which is non-daemon by default
void main() {
    Thread.ofPlatform().start(() -> {
        nap(100);
        IO.println("the platform clerk served the card");
    });
    IO.println("main is finished");
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

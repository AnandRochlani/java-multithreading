Runnable clerkAt(String window) {
    return () -> {
        for (int ticket = 1; ticket <= 2; ticket++) {
            nap(300);
            IO.println(window + " sold ticket " + ticket);
        }
    };
}

void main() {
    new Thread(clerkAt("window-1"), "window-1").start();
    new Thread(clerkAt("window-2"), "window-2").start();
}

void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

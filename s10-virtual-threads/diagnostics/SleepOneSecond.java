// How late does a one-second sleep wake, on main (a platform thread) and on a virtual thread? Five of each.
void main() throws Exception {
    for (int i = 0; i < 5; i++) {
        long t = System.nanoTime();
        Thread.sleep(1_000);
        IO.println(String.format("platform sleep(1000): %.1f ms", (System.nanoTime() - t) / 1e6));
    }
    for (int i = 0; i < 5; i++) {
        Thread v = Thread.ofVirtual().start(() -> {
            long t = System.nanoTime();
            try { Thread.sleep(1_000); } catch (InterruptedException e) { }
            IO.println(String.format("virtual  sleep(1000): %.1f ms", (System.nanoTime() - t) / 1e6));
        });
        v.join();
    }
}

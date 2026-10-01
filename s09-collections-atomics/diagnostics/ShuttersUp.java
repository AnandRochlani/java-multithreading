void main() throws InterruptedException {
    CountDownLatch arrived = new CountDownLatch(3);               // the station opens when three clerks are in
    long start = System.nanoTime();
    for (String name : List.of("Meena", "Tom", "Kavya")) {
        long commute = switch (name) { case "Meena" -> 100; case "Tom" -> 200; default -> 300; };
        Thread.ofPlatform().name(name).start(() -> {
            nap(commute);
            IO.println(ms(start) + name + " is at the window");
            arrived.countDown();                                  // one fewer to wait for
        });
    }
    arrived.await();                                              // main waits here until the count reaches zero
    IO.println(ms(start) + "station master: all three in, shutters up");
}

static String ms(long start) { return String.format("%4d ms  ", (System.nanoTime() - start) / 1_000_000); }

static void nap(long ms) {
    try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}

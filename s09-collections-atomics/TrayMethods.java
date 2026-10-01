void main() throws InterruptedException {
    BlockingQueue<String> tray = new ArrayBlockingQueue<>(1);
    tray.put("slip #1");                                          // the tray is now full
    try {
        tray.add("slip #2");
    } catch (IllegalStateException e) {
        IO.println("add on a full tray throws     : " + e);
    }
    IO.println("offer on a full tray returns  : " + tray.offer("slip #2"));
    long start = System.nanoTime();
    boolean in = tray.offer("slip #2", 100, TimeUnit.MILLISECONDS);
    IO.println("offer, waiting 100 ms, returns: " + in + " after " + (System.nanoTime() - start) / 1_000_000 + " ms");

    Thread ravi = new Thread(() -> {
        try {
            tray.put("slip #2");                                  // waits for as long as it takes
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }, "printer");
    ravi.start();
    Thread.sleep(100);
    IO.println("put on a full tray, 100 ms on : the printer is " + ravi.getState());
    IO.println("Meena takes                   : " + tray.take());
    ravi.join();
    IO.println("and the printer's put is done : tray = " + tray);

    tray.clear();                                                 // now the other side: an EMPTY tray
    try {
        tray.remove();
    } catch (NoSuchElementException e) {
        IO.println("remove on an empty tray throws: " + e);
    }
    IO.println("poll on an empty tray returns : " + tray.poll());
    start = System.nanoTime();
    String slip = tray.poll(100, TimeUnit.MILLISECONDS);
    IO.println("poll, waiting 100 ms, returns : " + slip + " after " + (System.nanoTime() - start) / 1_000_000 + " ms");
}

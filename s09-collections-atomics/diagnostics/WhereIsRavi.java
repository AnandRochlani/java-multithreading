void main() throws InterruptedException {
    BlockingQueue<String> tray = new ArrayBlockingQueue<>(1);
    tray.put("slip #1");                                          // the tray is now full
    Thread ravi = new Thread(() -> {
        try {
            tray.put("slip #2");                                  // waits here while the tray is full
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }, "printer");
    ravi.start();
    Thread.sleep(100);
    IO.println("the printer is " + ravi.getState() + ", and here is where:");
    for (StackTraceElement f : ravi.getStackTrace()) IO.println("    at " + f);
    tray.take();
    ravi.join();
}

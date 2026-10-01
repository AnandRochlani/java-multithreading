void main() throws InterruptedException {
    ExecutorService desk = Executors.newFixedThreadPool(1);
    Runnable mine = () -> nap(500);
    for (int i = 0; i < 3; i++) desk.submit(mine);
    Thread.sleep(100);
    List<Runnable> back = desk.shutdownNow();
    IO.println("handed back " + back.size() + "; first is " + back.get(0).getClass().getName()
            + "; same object I submitted? " + (back.get(0) == mine));
    ExecutorService desk2 = Executors.newFixedThreadPool(1);
    for (int i = 0; i < 3; i++) desk2.execute(mine);
    Thread.sleep(100);
    back = desk2.shutdownNow();
    IO.println("with execute: handed back " + back.size() + "; same object I handed in? " + (back.get(0) == mine));
}

static void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

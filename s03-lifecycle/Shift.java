void main() throws InterruptedException {
    Thread meena = new Thread(() -> nap(700), "Meena");       // one chai break

    IO.println("hired         : " + meena.getState());
    meena.start();
    IO.println("just started  : " + meena.getState());
    nap(200);                                                  // main pauses, so it can look mid-break
    IO.println("mid-break     : " + meena.getState());
    meena.join();
    IO.println("after join    : " + meena.getState());
    IO.println("still alive?  : " + meena.isAlive());

    meena.start();                                             // a second shift for the same thread?
}

void nap(long ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

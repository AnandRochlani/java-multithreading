void main() {
    Thread clerk = new Thread(() -> IO.println("run by " + Thread.currentThread().getName()), "window-9");
    Runnable card = clerk;                          // a Thread is also a Runnable
    new Thread(card, "window-1").start();
}

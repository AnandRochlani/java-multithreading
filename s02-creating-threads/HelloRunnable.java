void main() {
    Runnable job = () -> IO.println("hello from " + Thread.currentThread().getName());
    Thread clerk = new Thread(job);
    clerk.start();
    IO.println("main carries on");
}

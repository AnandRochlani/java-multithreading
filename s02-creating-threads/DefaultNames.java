void main() {
    Runnable job = () -> IO.println("I am " + Thread.currentThread().getName());
    new Thread(job).start();
    new Thread(job).start();
    new Thread(job).start();
}

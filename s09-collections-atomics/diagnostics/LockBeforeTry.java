static final ReentrantLock lock = new ReentrantLock();

void main() throws InterruptedException {
    Thread.currentThread().interrupt();                           // someone tapped main on the shoulder
    lock.lockInterruptibly();                                     // RIGHT place: the line before the try
    try {
        IO.println("got the key");
    } finally {
        lock.unlock();                                            // runs only once we hold the key
    }
}

static final ReentrantLock lock = new ReentrantLock();

void main() throws InterruptedException {
    Thread.currentThread().interrupt();                           // someone tapped main on the shoulder
    try {
        lock.lockInterruptibly();                                 // WRONG place: inside the try
        IO.println("got the key");
    } finally {
        lock.unlock();                                            // runs even though we never got the key
    }
}

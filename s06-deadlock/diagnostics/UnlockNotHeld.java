static final ReentrantLock CASH = new ReentrantLock();

void main() {
    CASH.unlock();                                            // never called lock()
}

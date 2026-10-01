// Run with -Djdk.traceVirtualThreadLocals=true : the JDK prints a stack trace when a virtual thread sets a ThreadLocal
static final ThreadLocal<byte[]> BUFFER = ThreadLocal.withInitial(() -> new byte[1024]);

void main() throws InterruptedException {
    Thread.ofVirtual().start(() -> BUFFER.get()[0]++).join();   // one virtual card, one buffer
}

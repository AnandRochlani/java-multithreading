void main() {
    Thread clerk = Thread.ofPlatform().unstarted(() -> {});   // the kind you have hired all course
    Thread passenger = Thread.ofVirtual().unstarted(() -> {}); // the new kind
    IO.println("platform thread: isVirtual " + clerk.isVirtual() + ", isDaemon " + clerk.isDaemon());
    IO.println("virtual thread:  isVirtual " + passenger.isVirtual() + ", isDaemon " + passenger.isDaemon());
    try {
        passenger.setDaemon(false);                           // ask for a thread the JVM must wait for
    } catch (IllegalArgumentException e) {
        IO.println("setDaemon(false) on a virtual thread: " + e);
    }
}

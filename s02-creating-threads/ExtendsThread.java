class ClerkThread extends Thread {
    ClerkThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        IO.println(getName() + " is selling tickets");
    }
}

void main() {
    new ClerkThread("window-1").start();
    new ClerkThread("window-2").start();
}

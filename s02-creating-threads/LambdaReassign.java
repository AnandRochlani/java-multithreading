Runnable clerkAt(String window) {
    window = window.toUpperCase();
    return () -> IO.println(window + " sold a ticket");
}

void main() {
    new Thread(clerkAt("window-1")).start();
}

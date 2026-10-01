void main() {
    for (Thread.State s : Thread.State.values()) {
        IO.println(s.ordinal() + "  " + s);
    }
}

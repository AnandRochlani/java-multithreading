void main() {
    IO.println("cores     : " + Runtime.getRuntime().availableProcessors());
    IO.println("processes : " + ProcessHandle.allProcesses().count());
}

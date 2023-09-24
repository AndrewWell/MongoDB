public class Time {
    private long startTime;
    private long finishTime;

    public Time() {
        this.startTime = 0;
        this.finishTime = 0;
    }

    public void startTime() {
        this.startTime = System.nanoTime();
    }

    public void finishTime() {
        this.finishTime = System.nanoTime();
    }

    public long getLeadTime() {
        return this.finishTime - this.startTime;
    }
}

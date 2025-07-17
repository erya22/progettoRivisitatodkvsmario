package model;
public class FloatingScore {
    private int x, y;
    private int score;
    private long timestamp;
    private static final long DURATION = 2000; // 2 secondi

    public FloatingScore(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - timestamp > DURATION;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getScore() { return score; }
}

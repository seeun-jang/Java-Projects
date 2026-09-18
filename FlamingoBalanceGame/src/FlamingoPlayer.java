public class FlamingoPlayer {

    private double balance;
    private double velocity;

    public FlamingoPlayer() {
        balance = 0;
        velocity = 0;
    }

    public void update(double windForce) {
        velocity += windForce;
        velocity *= 0.98;

        balance += velocity;
    }

    public void moveLeft() {
        velocity -= 0.20;
    }

    public void moveRight() {
        velocity += 0.20;
    }

    public boolean isFallen() {
        return Math.abs(balance) >= 30;
    }

    public double getBalance() {
        return balance;
    }

    public void reset() {
        balance = 0;
        velocity = 0;
    }
}

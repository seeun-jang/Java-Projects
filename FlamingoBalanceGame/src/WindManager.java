import java.util.Random;

public class WindManager {

    private final Random random;
    private double windForce;
    private int changeCounter;

    public WindManager() {
        random = new Random();
        windForce = 0;
        changeCounter = 0;
    }

    public void update() {
        changeCounter++;

        if (changeCounter >= 40) {
            windForce = (random.nextDouble() - 0.5) * 0.08;
            changeCounter = 0;
        }
    }

    public double getWindForce() {
        return windForce;
    }

    public String getWindDirection() {
        if (windForce < -0.01) {
            return "LEFT";
        } else if (windForce > 0.01) {
            return "RIGHT";
        } else {
            return "CALM";
        }
    }

    public void reset() {
        windForce = 0;
        changeCounter = 0;
    }
}

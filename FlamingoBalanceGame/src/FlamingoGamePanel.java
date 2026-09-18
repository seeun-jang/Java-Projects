import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FlamingoGamePanel extends JPanel {

    private final FlamingoPlayer player;
    private final WindManager windManager;
    private final Timer gameTimer;

    private boolean leftPressed;
    private boolean rightPressed;
    private boolean gameOver;

    private long startTime;
    private int score;

    public FlamingoGamePanel() {

        player = new FlamingoPlayer();
        windManager = new WindManager();

        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(255, 240, 245));

        setupKeyBindings();

        startTime = System.currentTimeMillis();

        gameTimer = new Timer(16, e -> updateGame());
        gameTimer.start();
    }

    private void setupKeyBindings() {

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "leftPressed");

        getActionMap().put("leftPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPressed = true;
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "leftReleased");

        getActionMap().put("leftReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPressed = false;
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "rightPressed");

        getActionMap().put("rightPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPressed = true;
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "rightReleased");

        getActionMap().put("rightReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPressed = false;
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "restart");

        getActionMap().put("restart", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
    }

    private void updateGame() {

        if (gameOver) {
            repaint();
            return;
        }

        windManager.update();

        if (leftPressed) {
            player.moveLeft();
        }

        if (rightPressed) {
            player.moveRight();
        }

        player.update(windManager.getWindForce());

        score = (int) ((System.currentTimeMillis() - startTime) / 1000);

        if (player.isFallen()) {
            gameOver = true;
        }

        repaint();
    }

    private void restartGame() {

        player.reset();
        windManager.reset();

        leftPressed = false;
        rightPressed = false;
        gameOver = false;

        score = 0;
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawTitle(g2);
        drawWindInfo(g2);
        drawBalanceGauge(g2);
        drawGround(g2);
        drawFlamingo(g2);
        drawScore(g2);

        if (gameOver) {
            drawGameOver(g2);
        }
    }

    private void drawTitle(Graphics2D g2) {

        g2.setColor(new Color(90, 50, 70));
        g2.setFont(new Font("Arial", Font.BOLD, 28));

        g2.drawString("FLAMINGO BALANCE", 270, 45);

        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString(
                "A : Left   /   D : Right",
                315,
                75
        );
    }

    private void drawWindInfo(Graphics2D g2) {

        g2.setColor(new Color(80, 100, 140));
        g2.setFont(new Font("Arial", Font.BOLD, 18));

        String wind = "Wind: " + windManager.getWindDirection();

        g2.drawString(wind, 30, 110);
    }

    private void drawBalanceGauge(Graphics2D g2) {

        int x = 200;
        int y = 140;
        int width = 400;

        g2.setColor(Color.DARK_GRAY);
        g2.drawLine(x, y, x + width, y);

        g2.setColor(new Color(80, 170, 100));
        g2.fillRect(x + width / 2 - 2, y - 12, 4, 24);

        double balance = player.getBalance();

        int markerX = x + width / 2 + (int) (balance * 6);

        markerX = Math.max(x, Math.min(x + width, markerX));

        g2.setColor(new Color(230, 80, 120));
        g2.fillOval(markerX - 8, y - 8, 16, 16);

        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Arial", Font.PLAIN, 14));

        g2.drawString("LEFT", x - 10, y + 30);
        g2.drawString("BALANCED", x + 165, y + 30);
        g2.drawString("RIGHT", x + width - 40, y + 30);
    }

    private void drawGround(Graphics2D g2) {

        g2.setColor(new Color(140, 200, 160));
        g2.fillRect(0, 500, getWidth(), 100);

        g2.setColor(new Color(100, 160, 120));
        g2.fillRect(0, 500, getWidth(), 5);
    }

    private void drawFlamingo(Graphics2D g2) {

        int centerX = 400;
        int groundY = 500;

        double angle = Math.toRadians(player.getBalance());

        Graphics2D flamingo = (Graphics2D) g2.create();

        flamingo.rotate(angle, centerX, groundY);

        flamingo.setStroke(new BasicStroke(5));
        flamingo.setColor(new Color(220, 90, 120));

        // 한쪽 다리
        flamingo.drawLine(
                centerX,
                groundY,
                centerX,
                groundY - 100
        );

        // 몸
        flamingo.setColor(new Color(255, 140, 170));
        flamingo.fillOval(
                centerX - 45,
                groundY - 190,
                90,
                100
        );

        // 목
        flamingo.setStroke(new BasicStroke(18));
        flamingo.drawLine(
                centerX + 15,
                groundY - 170,
                centerX + 35,
                groundY - 250
        );

        // 머리
        flamingo.fillOval(
                centerX + 15,
                groundY - 285,
                50,
                50
        );

        // 눈
        flamingo.setColor(Color.BLACK);
        flamingo.fillOval(
                centerX + 48,
                groundY - 268,
                6,
                6
        );

        // 부리
        flamingo.setColor(new Color(60, 50, 50));

        int[] beakX = {
                centerX + 63,
                centerX + 100,
                centerX + 62
        };

        int[] beakY = {
                groundY - 270,
                groundY - 260,
                groundY - 248
        };

        flamingo.fillPolygon(beakX, beakY, 3);

        flamingo.dispose();
    }

    private void drawScore(Graphics2D g2) {

        g2.setColor(new Color(90, 50, 70));
        g2.setFont(new Font("Arial", Font.BOLD, 22));

        g2.drawString(
                "SURVIVAL : " + score + " sec",
                30,
                560
        );
    }

    private void drawGameOver(Graphics2D g2) {

        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 45));

        g2.drawString("GAME OVER", 265, 260);

        g2.setFont(new Font("Arial", Font.BOLD, 24));

        g2.drawString(
                "Survival Time: " + score + " sec",
                285,
                310
        );

        g2.setFont(new Font("Arial", Font.PLAIN, 20));

        g2.drawString(
                "Press R to restart",
                315,
                350
        );
    }
}

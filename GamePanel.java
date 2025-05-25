import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final int TILE_SIZE = 25;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int DELAY = 150;

    private final Timer timer;
    private final Snake snake;
    private Food food;
    private Direction currentDirection = Direction.RIGHT;
    private boolean running = true;
    private SafeZone safeZone;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        snake = new Snake(new Position(WIDTH / 2, HEIGHT / 2));
        safeZone = new SafeZone(WIDTH, HEIGHT);
        spawnFood();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void spawnFood() {
        Random rand = new Random();
        Position foodPos;
        do {
            foodPos = new Position(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
        } while (snake.contains(foodPos));
        int foodType = rand.nextInt(2); // 0 = shrink, 1 = expand
        int scoreValue = foodType == 0 ? -1 : 1;
        food = new Food(foodPos, scoreValue);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move(currentDirection);

            if (snake.isOutOfBounds(WIDTH, HEIGHT) || snake.hasSelfCollision() ||
                !safeZone.isInside(snake.getHead())) {
                running = false;
                timer.stop();
            }

            if (snake.getHead().equals(food.getPosition())) {
                snake.grow();
                if (food.getScoreValue() < 0) {
                    safeZone.shrink();
                } else {
                    safeZone.expand(WIDTH, HEIGHT);
                }
                spawnFood();
            }

            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw safe zone
        g.setColor(Color.DARK_GRAY);
        g.fillRect(safeZone.getX() * TILE_SIZE, safeZone.getY() * TILE_SIZE,
                   safeZone.getWidth() * TILE_SIZE, safeZone.getHeight() * TILE_SIZE);

        // Draw snake
        for (Position p : snake.getBody()) {
            g.setColor(Color.GREEN);
            g.fillRect(p.getX() * TILE_SIZE, p.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw food
        g.setColor(food.getScoreValue() > 0 ? Color.BLUE : Color.RED);
        g.fillOval(food.getPosition().getX() * TILE_SIZE,
                   food.getPosition().getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Game over
        if (!running) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            FontMetrics metrics = getFontMetrics(g.getFont());
            String msg = "Game Over";
            g.drawString(msg, (WIDTH * TILE_SIZE - metrics.stringWidth(msg)) / 2,
                         HEIGHT * TILE_SIZE / 2);
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (currentDirection != Direction.DOWN)
                        currentDirection = Direction.UP;
                    break;
                case KeyEvent.VK_DOWN:
                    if (currentDirection != Direction.UP)
                        currentDirection = Direction.DOWN;
                    break;
                case KeyEvent.VK_LEFT:
                    if (currentDirection != Direction.RIGHT)
                        currentDirection = Direction.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (currentDirection != Direction.LEFT)
                        currentDirection = Direction.RIGHT;
                    break;
            }
        }
    }
}

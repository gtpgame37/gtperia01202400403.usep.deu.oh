import java.util.Deque;
import java.util.LinkedList;

public class Snake {
    private Deque<Position> body;
    private Direction currentDirection;
    private boolean grow;

    public Snake(Position startPosition) {
        body = new LinkedList<>();
        body.addFirst(startPosition);
        currentDirection = Direction.RIGHT;
        grow = false;
    }

    public void setDirection(Direction newDirection) {
        if ((currentDirection == Direction.UP && newDirection == Direction.DOWN) ||
            (currentDirection == Direction.DOWN && newDirection == Direction.UP) ||
            (currentDirection == Direction.LEFT && newDirection == Direction.RIGHT) ||
            (currentDirection == Direction.RIGHT && newDirection == Direction.LEFT)) {
            return;
        }
        this.currentDirection = newDirection;
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public void move(Direction dir) {
        Position head = body.peekFirst();
        Position newHead = head.move(dir.getDx(), dir.getDy());

        body.addFirst(newHead);

        if (!grow) {
            body.removeLast();
        } else {
            grow = false;
        }
    }

    public void grow() {
        grow = true;
    }

    public boolean hasSelfCollision() {
        Position head = body.peekFirst();
        return body.stream().skip(1).anyMatch(p -> p.equals(head));
    }

    public boolean isOutOfBounds(int width, int height) {
        Position head = body.peekFirst();
        return head.getX() < 0 || head.getX() >= width || head.getY() < 0 || head.getY() >= height;
    }

    public boolean contains(Position pos) {
        return body.contains(pos);
    }

    public Position getHead() {
        return body.peekFirst();
    }

    public Deque<Position> getBody() {
        return body;
    }
}

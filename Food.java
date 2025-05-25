public class Food {
    private Position position;
    private int scoreValue; // can be used to determine shrinking/growing zone

    public Food(Position position, int scoreValue) {
        this.position = position;
        this.scoreValue = scoreValue;
    }

    public Position getPosition() {
        return position;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public void setScoreValue(int value) {
        this.scoreValue = value;
    }

    @Override
    public String toString() {
        return "Food at " + position.toString() + " worth " + scoreValue + " points";
    }
}

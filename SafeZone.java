public class SafeZone {
    private int x, y, width, height;

    public SafeZone(int gridWidth, int gridHeight) {
        this.x = 0;
        this.y = 0;
        this.width = gridWidth;
        this.height = gridHeight;
    }

    public void shrink() {
        if (width > 5 && height > 5) {
            x++;
            y++;
            width -= 2;
            height -= 2;
        }
    }

    public void expand(int maxWidth, int maxHeight) {
        if (width < maxWidth && height < maxHeight) {
            x = Math.max(0, x - 1);
            y = Math.max(0, y - 1);
            width = Math.min(maxWidth, width + 2);
            height = Math.min(maxHeight, height + 2);
        }
    }

    public boolean isInside(Position pos) {
        return pos.getX() >= x && pos.getX() < x + width &&
               pos.getY() >= y && pos.getY() < y + height;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

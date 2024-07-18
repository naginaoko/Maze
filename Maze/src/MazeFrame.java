import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MazeFrame extends MyFrame {
    private MazeGenerator maze;

    public MazeFrame() {
        super();
        setSize(800, 800);  // ウィンドウサイズを設定
        maze = new MazeGenerator(15, 15);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    maze.generateMaze();
                    repaint();
                }
            }
        });
    }

    @Override
    public void run() {
        while (true) {
            clear();
            drawMaze();
            sleep(0.1);
        }
    }

    private void drawMaze() {
        int cellSize = Math.min(getWidth() / maze.getWidth(), getHeight() / maze.getHeight());
        int offsetX = (getWidth() - maze.getWidth() * cellSize) / 2;
        int offsetY = (getHeight() - maze.getHeight() * cellSize) / 2;

        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                switch (maze.getCell(x, y)) {
                    case MazeGenerator.START:
                        setColor(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue());
                        break;
                    case MazeGenerator.GOAL:
                        setColor(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue());
                        break;
                    case MazeGenerator.WALL:
                        setColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue());
                        break;
                    case MazeGenerator.PATH:
                        setColor(Color.WHITE.getRed(), Color.WHITE.getGreen(), Color.WHITE.getBlue());
                        break;
                }
                fillRect(x * cellSize + offsetX, y * cellSize + offsetY, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        new MazeFrame();
    }
}

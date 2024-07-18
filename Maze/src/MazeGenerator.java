import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    public static final int WALL = 0;
    public static final int PATH = 1;
    public static final int START = 2;
    public static final int GOAL = 3;
    private int[][] maze;
    private int width, height;
    private Random random;

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new int[height][width];
        this.random = new Random();
        generateMaze();
    }

    public void generateMaze() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = WALL;
            }
        }

        int startX = 1;
        int startY = 1;
        maze[startY][startX] = START;

        int goalX = width - 2;
        int goalY = height - 2;

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});
        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];
            List<int[]> neighbors = new ArrayList<>();
            if (x > 2 && maze[y][x - 2] == WALL) neighbors.add(new int[]{x - 2, y});
            if (x < width - 3 && maze[y][x + 2] == WALL) neighbors.add(new int[]{x + 2, y});
            if (y > 2 && maze[y - 2][x] == WALL) neighbors.add(new int[]{x, y - 2});
            if (y < height - 3 && maze[y + 2][x] == WALL) neighbors.add(new int[]{x, y + 2});
            if (neighbors.isEmpty()) {
                stack.pop();
            } else {
                int[] next = neighbors.get(random.nextInt(neighbors.size()));
                int nx = next[0];
                int ny = next[1];
                maze[ny][nx] = PATH;
                maze[ny + (y - ny) / 2][nx + (x - nx) / 2] = PATH;
                stack.push(next);
            }
        }

        ensurePath(startX, startY, goalX, goalY);
        maze[goalY][goalX] = GOAL;
    }

    private void ensurePath(int startX, int startY, int goalX, int goalY) {
        boolean[][] visited = new boolean[height][width];
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});
        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int x = current[0];
            int y = current[1];
            if (x == goalX && y == goalY) {
                break;
            }
            visited[y][x] = true;
            List<int[]> neighbors = getNeighbors(x, y, visited);
            for (int[] neighbor : neighbors) {
                if (!visited[neighbor[1]][neighbor[0]]) {
                    stack.push(neighbor);
                }
            }
        }
    }

    private List<int[]> getNeighbors(int x, int y, boolean[][] visited) {
        List<int[]> neighbors = new ArrayList<>();
        if (x > 0 && maze[y][x - 1] == PATH && !visited[y][x - 1]) neighbors.add(new int[]{x - 1, y});
        if (x < width - 1 && maze[y][x + 1] == PATH && !visited[y][x + 1]) neighbors.add(new int[]{x + 1, y});
        if (y > 0 && maze[y - 1][x] == PATH && !visited[y - 1][x]) neighbors.add(new int[]{x, y - 1});
        if (y < height - 1 && maze[y + 1][x] == PATH && !visited[y + 1][x]) neighbors.add(new int[]{x, y + 1});
        return neighbors;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCell(int x, int y) {
        return maze[y][x];
    }
}

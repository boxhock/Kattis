package me.jonashals;

import java.io.IOException;
import java.util.ArrayDeque;

public class Grid {
    private int width;
    private int height;
    private int[][] grid;
    private boolean[][] visited;

    private class Position {
        private int n;
        private int m;
        private int dist;

        Position(int n, int m, int dist) {
            this.n = n;
            this.m = m;
            this.dist = dist;
        }
    }

    private Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
        this.visited = new boolean[width][height];
    }

    private void setGrid(int n, int m, int k) {
        this.grid[n][m] = k;
    }

    private int searchLength() {
        ArrayDeque<Position> queue = new ArrayDeque<>(width*height);
        Position pos = new Position(0, 0, 0);
        queue.add(pos);
        visited[0][0] = true;
        int k;

        while (queue.size() != 0) {
            pos = queue.poll();
            if (pos.n == width-1 && pos.m == height-1) return pos.dist;
            k = grid[pos.n][pos.m];

            if (pos.n+k < width && !visited[pos.n+k][pos.m]) {
                queue.add(new Position(pos.n+k, pos.m, pos.dist+1));
                visited[pos.n+k][pos.m] = true;
            }

            if (pos.n-k >= 0 && !visited[pos.n-k][pos.m]) {
                queue.add(new Position(pos.n-k, pos.m, pos.dist+1));
                visited[pos.n-k][pos.m] = true;
            }

            if (pos.m+k < height && !visited[pos.n][pos.m+k]) {
                queue.add(new Position(pos.n, pos.m+k, pos.dist+1));
                visited[pos.n][pos.m+k] = true;
            }

            if (pos.m-k >= 0 && !visited[pos.n][pos.m-k]) {
                queue.add(new Position(pos.n, pos.m-k, pos.dist+1));
                visited[pos.n][pos.m-k] = true;
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        Reader io = new Reader();
        int n = io.nextInt();
        int m = io.nextInt();
        Grid grid = new Grid(n, m);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid.setGrid(i, j, io.nextDigit());
            }
        }

        io.print(grid.searchLength());
        io.close();
    }
}

package me.jonashals;

import java.io.IOException;
import java.util.ArrayDeque;

public class WheresMyInternet {
    private ArrayDeque<Integer>[] adj;
    private boolean[] visited;
    private int n;

    private WheresMyInternet(int n) {
        this.n = n;
        this.adj = new ArrayDeque[n];
        this.visited = new boolean[n+1];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayDeque<>();
        }
    }

    private void addEdge(int a, int b) {
        adj[a-1].add(b-1);
        adj[b-1].add(a-1);
    }

    private void search() {
        ArrayDeque<Integer> queue = new ArrayDeque<>(n);
        queue.add(0);
        visited[0] = true;
        int visits = 1;

        while (queue.size() != 0) {
            for (int i : adj[queue.poll()]) {
                if (!visited[i]) {
                    visited[i] = true;
                    visits++;
                    queue.add(i);
                }
            }
        }

        if (visits == n) {
            visited[n] = true;
        }
    }

    private boolean[] getConnected() {
        search();
        return this.visited;
    }

    public static void main(String[] args) throws IOException {
        Reader io = new Reader();
        int n = io.nextInt();
        int M = io.nextInt();
        WheresMyInternet graph = new WheresMyInternet(n);

        for (int i = 0; i < M; i++) {
            graph.addEdge(io.nextInt(), io.nextInt());
        }

        boolean[] connected = graph.getConnected();
        StringBuilder sb = new StringBuilder();

        if (connected[n]) {
            sb.append("Connected");
        } else {
            for (int i = 0; i < n; i++) {
                if (!connected[i]) {
                    sb.append(i+1).append("\n");
                }
            }
        }
        io.print(sb.toString());
        io.close();
    }
}

package me.jonashals;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

public class Bumped {
    private City[] cities;

    private Bumped(int n) {
        this.cities = new City[n];
        for (int i = 0; i < n; i++) {
            cities[i] = new City();
        }
    }

    private void addRoad(int a, int b, long cost) {
        cities[a].roads.add(new Road(b, cost, false));
        cities[b].roads.add(new Road(a, cost, false));
    }

    private void addFlight(int a, int b) {
        cities[a].roads.add(new Road(b, 0, true));
    }

    private class Road {
        private long weight;
        private int dest;
        private boolean isFlight;

        Road(int dest, long weight, boolean isFlight) {
            this.weight = weight;
            this.dest = dest;
            this.isFlight = isFlight;
        }
    }

    private class City {
        long dist = Long.MAX_VALUE;
        long fdist = Long.MAX_VALUE;
        boolean visited = false;
        boolean fvisited = false;
        ArrayDeque<Road> roads = new ArrayDeque<>();
    }

    private class Path implements Comparable<Path> {
        int dest;
        long cost;

        Path(int dest, long cost) {
            this.dest = dest;
            this.cost = cost;
        }

        @Override
        public int compareTo(Path o) {
            return Long.compare(this.cost, o.cost);
        }
    }

    private long dijsktra(int src, int dest) {
        PriorityQueue<Path> queue = new PriorityQueue<>(cities.length);
        cities[src].dist = 0;
        queue.add(new Path(src, 0));
        City cdest = null;

        while (!queue.isEmpty()) {
            Path path = queue.poll();
            City cur = cities[path.dest];

            if (path.dest == dest && (cdest = cur) != null) break;

            for (Road r : cur.roads) {
                long weight;
                City rdest = cities[r.dest];
                if (r.isFlight) {
                    if (cur.dist != Long.MAX_VALUE &&
                            cur.dist + r.weight < rdest.fdist && !rdest.fvisited) {
                        weight = cur.dist + r.weight;
                        rdest.fdist = weight;
                        cur.fvisited = true;
                        queue.add(new Path(r.dest, weight));
                    }
                } else {
                    if (cur.dist != Long.MAX_VALUE &&
                            cur.dist + r.weight < rdest.dist && !rdest.visited) {
                        weight = cur.dist + r.weight;
                        rdest.dist = weight;
                        cur.visited = true;
                        queue.add(new Path(r.dest, weight));
                    } else if (cur.fdist != Long.MAX_VALUE &&
                            cur.fdist + r.weight < rdest.fdist && !rdest.fvisited) {
                        weight = cur.fdist + r.weight;
                        rdest.fdist = weight;
                        cur.fvisited = true;
                        queue.add(new Path(r.dest, weight));
                    }
                }
            }
        }

        if (cdest == null) return 0;
        return Math.min(cdest.dist, cdest.fdist);
    }

    public static void main(String[] args) throws IOException {
        Reader io = new Reader();
        int n = io.nextInt();
        int m = io.nextInt();
        int f = io.nextInt();
        int s = io.nextInt();
        int t = io.nextInt();
        Bumped bp = new Bumped(n);

        for (int line = 0; line < m; line++) {
            bp.addRoad(io.nextInt(), io.nextInt(), io.nextLong());
        }

        for (int line = 0; line < f; line++) {
            bp.addFlight(io.nextInt(), io.nextInt());
        }

        io.print(bp.dijsktra(s, t));
        io.close();
    }
}

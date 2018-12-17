package me.jonashals;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Locale;
import java.util.PriorityQueue;

public class GetShorty {
    private Intersection[] intersections;
    private int n;
    private int avg;

    private class Intersection {
        int x;
        double size = 0;
        boolean visited = false;
        ArrayDeque<Corridor> corridors;

        Intersection(int x) {
            this.x = x;
            this.corridors = new ArrayDeque<>(avg);
        }
    }

    private class Path implements Comparable<Path> {
        int x;
        double size;

        Path(int x, double size) {
            this.x = x;
            this.size = size;
        }

        @Override
        public int compareTo(Path o) {
            return Double.compare(o.size, this.size);
        }
    }

    private class Corridor {
        int dest;
        double factor;

        Corridor(int dest, double factor) {
            this.dest = dest;
            this.factor = factor;
        }
    }

    private GetShorty(int n, int m) {
        this.n = n;
        this.avg = m/n;
        this.intersections = new Intersection[n];
        for (int i = 0; i < n; i++) {
            this.intersections[i] = new Intersection(i);
        }
    }

    private void addCorridor(int x, int y, double factor) {
        intersections[x].corridors.add(new Corridor(y, factor));
        intersections[y].corridors.add(new Corridor(x, factor));
    }

    private double calc() {
        int end = n-1;

        PriorityQueue<Path> inters = new PriorityQueue<>();
        Intersection inter = intersections[0];
        inter.size = 1;
        Path path = new Path(0, 1);
        inters.add(path);

        while (!inters.isEmpty() && (path = inters.poll()).x != end) {
            inter = intersections[path.x];
            inter.visited = true;

            for (Corridor cor :
                    inter.corridors) {
                double thisSize = path.size * cor.factor;
                Intersection dest = intersections[cor.dest];
                if (thisSize > dest.size && !dest.visited && inter.size != 0) {
                    dest.size = thisSize;
                    inters.add(new Path(cor.dest, thisSize));
                }
            }
        }

        return path.size;
    }

    public static void main(String[] args) throws IOException {
        Reader io = new Reader();
        int n;
        int m;
        while ((n = io.nextInt()) != 0 && (m = io.nextInt()) != 0) {
            GetShorty shorty = new GetShorty(n, m);
            for (int i = 0; i < m; i++) {
                String[] line = io.readLine().split(" ");
                int x = Integer.parseInt(line[0]);
                int y = Integer.parseInt(line[1]);
                double f = 1.0;
                if (line.length == 3) {
                    f = Double.parseDouble(line[2]);
                }
                shorty.addCorridor(x, y , f);
            }
            io.print(String.format(Locale.ROOT, "%.4f", shorty.calc())+"\n");
        }
        io.close();
    }
}

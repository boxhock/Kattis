package me.jonashals;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class BuildDeps {
    private Map<String, File> files;
    private ArrayDeque<String> queue = new ArrayDeque<>();

    private class File {
        String name;
        boolean visited = false;
        ArrayDeque<File> dependents = new ArrayDeque<>();

        File(String name) {
            this.name = name;
        }
    }

    private BuildDeps(int n) {
        this.files = new HashMap<>(n*2);
    }

    private File getFile(String filename) {
        File file;
        if ((file = files.get(filename)) != null) {
            return file;
        }
        files.put(filename, (file = new File(filename)));
        return file;
    }

    private void addFile(String filename, ArrayDeque<String> dependencies) {
        File file = getFile(filename);

        for (String dependency :
                dependencies) {
            getFile(dependency).dependents.add(file);
        }
    }

    private void visit(File file) {
        File dep;
        while (!file.dependents.isEmpty()) {
            dep = file.dependents.poll();
            if (!dep.visited) {
                visit(dep);
                dep.visited = true;
            }
        }
        queue.addFirst(file.name);
    }

    private String getChanges(String changedFile) {
        visit(files.get(changedFile));

        StringBuilder output = new StringBuilder();

        while (!queue.isEmpty()) {
            output.append(queue.poll());
            output.append("\n");
        }

        return output.toString();
    }

    public static void main(String[] args) throws IOException {
        Reader io = new Reader();
        int n = io.nextInt();
        BuildDeps bd = new BuildDeps(n);

        for (int i = 0; i < n; i++) {
            bd.addFile(io.readUntilDelimiter(':'), io.readWords());
        }

        io.print(bd.getChanges(io.readWord()));
        io.close();
    }
}

package me.jonashals;

import java.io.IOException;

public class Cold {
    public static void main(String[] args) throws IOException {
        Reader io = new Reader();
        int n = io.nextInt();
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (io.nextInt() < 0) count++;
        }
        io.print(count);
        io.close();
    }
}

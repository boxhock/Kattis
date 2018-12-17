package me.jonashals;

import java.io.IOException;

public class R2 {
    public static void main(String[] args) throws IOException {
        Reader io = new Reader();
        io.print(-(io.nextInt())+io.nextInt()*2);
        io.close();
    }
}

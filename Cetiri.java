package me.jonashals;

import java.io.IOException;
import java.util.Arrays;

public class Cetiri {
    public static void main(String[] args) throws IOException {
        Reader io = new Reader();
        int[] in = new int[3];
        for (int i = 0; i < 3; i++) {
            in[i] = io.nextInt();
        }
        Arrays.sort(in);
        int diff = 0, changes = 0;
        for (int i = 0; i < 2; i++) {
            int thisDiff = in[i+1] - in[i];
            if (thisDiff == diff || diff == 0) {
                diff = thisDiff;
            } else if (in[i+1]-in[i]-diff == diff) {
                io.print(in[i]+diff);
                io.close();
                return;
            } else {
                diff = thisDiff;
                changes++;
            }
        }
        if (changes > 0) {
            io.print(in[0] + diff);
        } else {
            io.print(in[2] + diff);
        }
        io.close();
    }
}

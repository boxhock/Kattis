package me.jonashals;

import java.io.*;
import java.util.ArrayDeque;

/**
 * Class for very fast input reading.
 * Source: https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
 *
 * (With slight modifications)
 *
 * @author Rishabh Mahrsee
 */
public class Reader extends PrintWriter {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    Reader() {
        super(new BufferedOutputStream(System.out));
        din = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    Reader(String file_name) throws IOException {
        super(new BufferedOutputStream(System.out));
        din = new DataInputStream(new FileInputStream(file_name));
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    String readLine() throws IOException {
        byte[] buf = new byte[Byte.MAX_VALUE]; // line length
        int cnt = 0, c;
        while ((c = read()) != -1)
        {
            if (c == '\n')
                break;
            buf[cnt++] = (byte) c;
        }
        return new String(buf, 0, cnt);
    }

    String readUntilDelimiter(char delim) throws IOException {
        byte[] buf = new byte[Byte.MAX_VALUE]; // line length
        int cnt = 0, c;
        while ((c = read()) != -1)
        {
            if (c == '\n' || c == delim)
                break;
            buf[cnt++] = (byte) c;
        }
        return new String(buf, 0, cnt);
    }

    String readWord() throws IOException {
        byte[] buf = new byte[64]; // line length
        int cnt = 0, c;
        while ((c = read()) != -1)
        {
            if (c == '\n' || c == ' ')
                break;
            buf[cnt++] = (byte) c;
        }
        return new String(buf, 0, cnt);
    }

    ArrayDeque<String> readWords() throws IOException {
        ArrayDeque<String> strings = new ArrayDeque<>();
        byte[] buf = new byte[64]; // line length
        int c = 0;
        while (c != '\n') {
            int cnt = 0;
            while ((c = read()) != -1)
            {
                if (c == '\n' || c == ' ')
                    break;
                buf[cnt++] = (byte) c;
            }
            if (cnt > 0) {
                strings.add(new String(buf, 0, cnt));
            }
        }
        return strings;
    }

    String[] readLines(int n) throws IOException {
        String[] set = new String[n];
        for (int i = 0; i < n; i++) {
            set[i] = readLine();
        }
        return set;
    }

    private String nextWord() throws IOException {
        byte[] buf = new byte[64]; // word length
        int cnt = 0, c;
        while ((c = read()) != -1)
        {
            if (c == ' ' || c == '\n')
                break;
            buf[cnt++] = (byte) c;
        }
        return new String(buf, 0, cnt);
    }

    int[] nextCharCodes() throws IOException {
        String word = nextWord();
        int len = word.length();
        int[] charArr = new int[len];
        for (int i = 0; i < len; i++) {
            charArr[i] = (int) word.charAt(i);
        }
        return charArr;
    }

    int nextDigit() throws IOException {
        byte c = read();
        while (c < '0' || c > '9')
            c = read();
        return c - '0';
    }

    int nextInt() throws IOException {
        int ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        }  while ((c = read()) >= '0' && c <= '9');

        if (neg)
            return -ret;
        return ret;
    }

    long nextLong() throws IOException {
        long ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (neg)
            return -ret;
        return ret;
    }

    double nextDouble() throws IOException {
        double ret = 0, div = 1;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();

        do {
            ret = ret * 10 + c - '0';
        }
        while ((c = read()) >= '0' && c <= '9');

        if (c == '.')
        {
            while ((c = read()) >= '0' && c <= '9')
            {
                ret += (c - '0') / (div *= 10);
            }
        }

        if (neg)
            return -ret;
        return ret;
    }

    private void fillBuffer() throws IOException {
        bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
        if (bytesRead == -1)
            buffer[0] = -1;
    }

    private byte read() throws IOException {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }

    public void close() {
        super.close();
        if (din == null)
            return;
        try {
            din.close();
        } catch (IOException e) {
            //
        }
    }
}

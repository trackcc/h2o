package h2o.common.util.id;

import java.util.Random;

public final class RandomString {

    private RandomString() {
    }

    private final static char[] c = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


    private final static char[] d = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


    public static String makeCode(int len) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < len; i++) {
            int r = rand.nextInt(c.length);
            sb.append(c[r]);
        }

        return sb.toString();
    }


    public static String makeNumberCode(int len) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < len; i++) {
            int r = rand.nextInt(d.length);
            sb.append(d[r]);
        }

        return sb.toString();
    }

    public static String makeUuid() {
        return UuidUtil.getUuid();
    }


}

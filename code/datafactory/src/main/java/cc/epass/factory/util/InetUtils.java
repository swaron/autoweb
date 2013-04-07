package cc.epass.factory.util;

public class InetUtils {
    public static String print(long i) {
        return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
    }

    public static Long parse(String addr) {
        String[] addrArray = addr.split("\\.");
        long num = 0;
        for (int i = 0, index = addrArray.length - 1; i < addrArray.length; i++, index--) {
            num += Integer.parseInt(addrArray[index]) % 256 * Math.pow(256, i);
        }
        return num;
    }
}

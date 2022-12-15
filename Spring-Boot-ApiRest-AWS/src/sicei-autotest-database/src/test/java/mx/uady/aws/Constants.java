package mx.uady.aws;

import java.util.Random;

public class Constants {

    public static String URL = "http:// ec2-52-90-112-156.compute-1.amazonaws.com :8080";
    public static Random random;

    static {
        random = new Random();
    }

    public static int getRandomId() {
        return random.nextInt(1000000);
    }

    public static int getRandomHoras() {
        return random.nextInt(50);
    }

    public static double getPromedio() {
        return Math.round(random.nextDouble() * 100d) / 10d;
    }
}

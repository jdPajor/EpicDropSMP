package pl.jdPajor.epicDropSMP.include;

import java.util.Random;

public class RandomUtil {
	
	private static  Random rand = new Random();

    public static int getRandInt( int min,  int max) throws IllegalArgumentException {
        return RandomUtil.rand.nextInt(max - min + 1) + min;
    }

    public static Double getRandDouble( double min,  double max) throws IllegalArgumentException {
        return RandomUtil.rand.nextDouble() * (max - min) + min;
    }

    public static Float getRandFloat( float min,  float max) throws IllegalArgumentException {
        return RandomUtil.rand.nextFloat() * (max - min) + min;
    }

}

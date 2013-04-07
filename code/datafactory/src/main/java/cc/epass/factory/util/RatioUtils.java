package cc.epass.factory.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

public abstract class RatioUtils {
    public static int[] toAccumulationArray(List<Integer> amount) {
        int[] accumulation = new int[amount.size()];
        int sum = 0;
        for (int i = 0; i < amount.size(); i++) {
            sum += amount.get(i);
            accumulation[i] = sum;
        }
        return accumulation;
    }
    public static int[] toAccumulationArray(int[] amount) {
        int[] accumulation = new int[amount.length];
        int sum = 0;
        for (int i = 0; i < amount.length; i++) {
            sum += amount[i];
            accumulation[i] = sum;
        }
        return accumulation;
    }

    public static int randomSelect(int[] accumulation) {
        int randomPos = RandomUtils.nextInt(accumulation[accumulation.length - 1]);
        int selected = Arrays.binarySearch(accumulation, randomPos);
        if (selected < 0) {
            selected = -selected - 1;
        }else{
            selected +=1;
        }
        if (selected >= accumulation.length) {
            throw new IllegalStateException("array not sorted. or the data in array are not accumulation");
        }
        return selected;
    }
}

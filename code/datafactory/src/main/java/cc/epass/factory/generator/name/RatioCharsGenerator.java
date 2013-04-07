package cc.epass.factory.generator.name;

import java.util.List;

import org.springframework.util.Assert;

import cc.epass.factory.util.RatioUtils;

public class RatioCharsGenerator {
    private int[] accumulation;
    private String[] chars;

    public RatioCharsGenerator(String[] items, int[] amount) {
        this.chars = items;
        this.accumulation = RatioUtils.toAccumulationArray(amount);
        Assert.isTrue(chars.length == accumulation.length);
    }
    public RatioCharsGenerator(List<String> items, List<Integer> amount) {
        this.chars = items.toArray(new String[items.size()]);
        this.accumulation = RatioUtils.toAccumulationArray(amount);
        Assert.isTrue(chars.length == accumulation.length);
    }

    public String next() {
        int selected = RatioUtils.randomSelect(accumulation);
        return chars[selected];
    }
}

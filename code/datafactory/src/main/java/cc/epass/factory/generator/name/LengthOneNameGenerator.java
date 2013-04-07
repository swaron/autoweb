package cc.epass.factory.generator.name;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Account;

@Component
public class LengthOneNameGenerator {
    @Autowired
    NameResource nameResource;
    // boyword,boyname1,boyname2,normal-name-word
    int sourceRatio[] = { 5, 3, 2, 1 };
    int[] accumulationRatio;

    @PostConstruct
    public void initialize() {
        accumulationRatio = RatioUtils.toAccumulationArray(sourceRatio);
    }
    
    public void setNameResource(NameResource nameResource) {
        this.nameResource = nameResource;
    }
    public void setSourceRatio(int[] sourceRatio) {
        this.sourceRatio = sourceRatio;
        initialize();
    }
    
    public String next(Account account, Gender gender, String surName) {
        if (gender == Gender.male) {
            int selected = RatioUtils.randomSelect(accumulationRatio);
            if (selected == 0) {
                ArrayList<Character> chars = nameResource.getPopBoyChars();
                Character c = chars.get(RandomUtils.nextInt(chars.size()));
                return c.toString();
            }
            if (selected == 1) {
                return nameResource.getPopBoyChars1().next();
            }
            if (selected == 2) {
                return nameResource.getPopBoyChars2().next();
            }
            if (selected == 3) {
                ArrayList<Character> chars = nameResource.getNormalNameWords();
                Character c = chars.get(RandomUtils.nextInt(chars.size()));
                return c.toString();
            }
        } else {
            int selected = RatioUtils.randomSelect(sourceRatio);
            if (selected == 0) {
                ArrayList<Character> chars = nameResource.getPopGirlChars();
                Character c = chars.get(RandomUtils.nextInt(chars.size()));
                return c.toString();
            }
            if (selected == 1) {
                return nameResource.getPopGirlChars1().next();
            }
            if (selected == 2) {
                return nameResource.getPopGirlChars2().next();
            }
            if (selected == 3) {
                ArrayList<Character> chars = nameResource.getNormalNameWords();
                Character c = chars.get(RandomUtils.nextInt(chars.size()));
                return c.toString();
            }

        }
        //will never return null
        return null;
    }
}

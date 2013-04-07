package cc.epass.factory.generator.name;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Account;

@Service
public class SurNameGenerator {
    // for example if male:female is 5.13:4.87,then this array will be [5.13,10]
    private int[] surNameRatioAccumulation;
    private Integer surNameRatioSum;
    private String[] surName;
    private String[] surName2;
    private double surName2Ratio = (13.7 - 11.7) / 13.7;

    @PostConstruct
    public void initialize() {
        try {
            ClassPathResource name1 = new ClassPathResource("surname1.txt", this.getClass());
            List<String> lines = FileUtils.readLines(name1.getFile());
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<Integer> amount = new ArrayList<Integer>();
            for (String l : lines) {
                if(!l.trim().isEmpty()){
                    String[] split = StringUtils.split(l);
                    names.add(split[0]);
                    amount.add(Integer.parseInt(split[1]));
                }
            }
            surName = names.toArray(new String[names.size()]);
            
            surNameRatioAccumulation = RatioUtils.toAccumulationArray(amount);
            surNameRatioSum = surNameRatioAccumulation[surNameRatioAccumulation.length-1];
            
            ClassPathResource name2 = new ClassPathResource("surname2.txt", this.getClass());
            String name2txt = FileUtils.readFileToString(name2.getFile());
            String[] split = StringUtils.split(name2txt);
            ArrayList<String> name2s = new ArrayList<String>();
            for (String name : split) {
                if(!ArrayUtils.contains(surName, name)){
                    name2s.add(name);
                }
            }
            surName2 = name2s.toArray(new String[name2s.size()]);
        } catch (IOException e) {
            throw new BeanInitializationException("failed to initialize SurNameGenerator", e);
        }
    }


    public String next(Account account) {
        if (surNameRatioAccumulation == null) {
            initialize();
        }

        if (RandomUtils.nextDouble() < surName2Ratio) {
            return next2();
        } else {
            return next1();
        }
    }

    private String next2() {
        int index = RandomUtils.nextInt(surName2.length);
        return surName2[index];
    }

    public String next1() {
        int selected = RatioUtils.randomSelect(surNameRatioAccumulation);
        return surName[selected];
    }
}

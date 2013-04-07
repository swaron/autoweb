package cc.epass.factory.generator.name;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.epass.schemas.checking.v10.Account;

@Service
public class GivenNameGenerator {
    public double girlLengthOneRatio = 0.2;
    public double boyLengthOneRatio = 0.16;
    @Autowired
    LengthOneNameGenerator oneNameGenerator;
    @Autowired
    LengthTwoNameGenerator twoNameGenerator;
    public String next(Account account, Gender gender, String surName) {
        double ratio = girlLengthOneRatio;
        if(gender == Gender.male){
            ratio = boyLengthOneRatio;
        }
        if(RandomUtils.nextDouble() < ratio){
            return oneNameGenerator.next(account,gender,surName);
        }else{
            return twoNameGenerator.next(account,gender,surName);
        }
    }
}

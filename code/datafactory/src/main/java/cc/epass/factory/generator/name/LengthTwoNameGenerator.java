package cc.epass.factory.generator.name;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.schemas.checking.v10.Account;

@Component
public class LengthTwoNameGenerator {

    @Autowired
    PredefinedLengthTwoNameGenerator generator1;

    @Autowired
    ConstructedLengthTwoNameGenerator generator2;
    double predefinedNameRatio = 0.05;

    public String next(Account account, Gender gender, String surName) {
        if (RandomUtils.nextDouble() < predefinedNameRatio) {
            return generator1.next(account, gender, surName);
        } else {
            return generator2.next(account, gender, surName);
        }
    }
}

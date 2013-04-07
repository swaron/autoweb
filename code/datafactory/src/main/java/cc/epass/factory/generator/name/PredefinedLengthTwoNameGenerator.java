package cc.epass.factory.generator.name;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.schemas.checking.v10.Account;

@Component
public class PredefinedLengthTwoNameGenerator {
    @Autowired
    NameResource nameResource;

    public String next(Account account, Gender gender, String surName) {
        if (gender == Gender.male) {
            List<String> popBoyNames = nameResource.getPopBoyNames();
            int index = RandomUtils.nextInt(popBoyNames.size());
            return popBoyNames.get(index);
        } else {
            List<String> popGirlNames = nameResource.getPopGirlNames();
            int index = RandomUtils.nextInt(popGirlNames.size());
            return popGirlNames.get(index);
        }
    }
}

package cc.epass.factory.generator.name;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.epass.factory.generator.account.AccountTypeGenerator;
import cc.epass.schemas.checking.v10.Account;

@Service
public class AccountNameGenerator {
    @Autowired
    SurNameGenerator surNameGenerator;
    @Autowired
    GivenNameGenerator givenNameGenerator;

    public String next(Account account) {
        if(account.getAccountType() == AccountTypeGenerator.MerchantType){
            return "商户_"+ RandomStringUtils.randomNumeric(6);
        }
        Gender gender = Gender.fromValue(RandomUtils.nextInt(2));
        String surName = surNameGenerator.next(account);
        String givenName = givenNameGenerator.next(account, gender, surName);
        return surName + givenName;
    }
}

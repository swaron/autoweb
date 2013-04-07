package cc.epass.factory.generator.account;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import cc.epass.schemas.checking.v10.Account;

@Service
public class BankCardNumGenerator {
    public static final int length = 19;
    
    public String next(Account account) {
        return RandomStringUtils.randomNumeric(length);
    }
}

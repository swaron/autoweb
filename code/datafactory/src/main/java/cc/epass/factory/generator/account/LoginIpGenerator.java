package cc.epass.factory.generator.account;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.schemas.checking.v10.Account;

@Component
public class LoginIpGenerator {
    public double sameAsRegIpRatio = 0.10;
    public double randomLoginIpRatio = 0.10;
    @Autowired
    IpGenerator ipGenerator;

    public String next(Account account) {
        if (RandomUtils.nextDouble() < sameAsRegIpRatio) {
            if (account.getAccountRegIp() != null) {
                return account.getAccountRegIp();
            }
        }
        if (RandomUtils.nextDouble() < randomLoginIpRatio) {
            return ipGenerator.genRandomChinaIp();
        }
        return ipGenerator.next(account);
    }

}

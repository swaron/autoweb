package cc.epass.factory.generator.account;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Account;

@Component
public class LogFailCountGenerator {
    int[] ratios = {200,1,2,2,3,2,1,1};
    int[] ratioAccumulation = RatioUtils.toAccumulationArray(ratios);
    int[] failCount = {0,1,2,3,4,5,6,-1};
    public Integer next(Account account) {
        int select = RatioUtils.randomSelect(ratioAccumulation);
        if(select == ratios.length -1 ){
            return RandomUtils.nextInt(100000);
        }
        return failCount[select];
    }
}

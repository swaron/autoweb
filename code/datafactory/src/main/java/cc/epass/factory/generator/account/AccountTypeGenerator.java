package cc.epass.factory.generator.account;

import org.springframework.stereotype.Service;

import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Account;

@Service
public class AccountTypeGenerator {
    public int[] ratio = { 1, 200};
    public int[] status = { MerchantType, CustomerType };
    public int[] ratioArray = RatioUtils.toAccumulationArray(ratio);
    public static final int CustomerType = 1;
    public static final int MerchantType = 2;
    
    public Integer next(Account account) {
        int rand = RatioUtils.randomSelect(ratioArray);
        return status[rand];
    }
    
    
}

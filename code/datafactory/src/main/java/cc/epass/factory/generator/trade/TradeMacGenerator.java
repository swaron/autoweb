package cc.epass.factory.generator.trade;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.generator.account.MacGenerator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeMacGenerator extends Generator {
    @Autowired
    MacGenerator macGenerator;
    
    double sameAsPayerMacRate = 0.99; 

    public String next(Trade trade, TAccount payer, TAccount payee) {
        if(RandomUtils.nextDouble()< sameAsPayerMacRate){
            return payer.getMac();
        }else{
            return macGenerator.next(null);
        }
    }
}

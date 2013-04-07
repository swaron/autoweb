package cc.epass.factory.generator.trade;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.generator.account.ExploreInfoGenerator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeExploreInfoGenerator extends Generator {
    @Autowired
    ExploreInfoGenerator exploreInfoGenerator;
    
    double sameAsPayerRate = 0.99; 

    public String next(Trade trade, TAccount payer, TAccount payee) {
        if(RandomUtils.nextDouble()< sameAsPayerRate){
            return payer.getExploreInfo();
        }else{
            return exploreInfoGenerator.next(null);
        }
    }
}

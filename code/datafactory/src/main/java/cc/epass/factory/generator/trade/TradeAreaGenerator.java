package cc.epass.factory.generator.trade;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.generator.account.AddressGenerator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeAreaGenerator extends Generator {
    @Autowired
    AddressGenerator addressGenerator;
    
    double sameAsSourceRate = 0.99; 

    public String next(Trade trade, TAccount payer, TAccount payee) {
        if(RandomUtils.nextDouble()< sameAsSourceRate){
            return payer.getAccountArea();
        }else{
            return addressGenerator.next().getZip();
        }
    }
}

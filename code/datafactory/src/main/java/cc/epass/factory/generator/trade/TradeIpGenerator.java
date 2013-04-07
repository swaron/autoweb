package cc.epass.factory.generator.trade;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.generator.account.IpGenerator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeIpGenerator extends Generator {
    @Autowired
    IpGenerator ipGenerator;
    
    double sameAsRegIpRate = 0.2; 

    public String next(Trade trade, TAccount payer, TAccount payee) {
        if(RandomUtils.nextDouble()< sameAsRegIpRate){
            return payer.getAccountRegIp();
        }else{
            return ipGenerator.genRandomChinaIp();
        }
    }
}

package cc.epass.factory.generator.trade;

import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeStatusCodeGenerator extends Generator {
    int[] typeRatios = { 500, 1 };
    // @1000 安全,@2风险代码，ext-- 500：1
    public static final int SECURE_CODE = 1000;
    public static final int RISK_1_CODE = 2;
    int[] ratioAccumulation = RatioUtils.toAccumulationArray(typeRatios);
    int[] typeArray = { SECURE_CODE, RISK_1_CODE };

    public int next(Trade trade, TAccount payer, TAccount payee) {
        int select = RatioUtils.randomSelect(ratioAccumulation);
        return typeArray[select];
    }
}

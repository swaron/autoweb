package cc.epass.factory.generator.trade;

import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeKindGenerator extends Generator {
    int[] typeRatios = { 1, 10, 50 };
    // @1 手机支付,2互联网支付,3 充值预付卡， 1：10：50
    public static final int BY_MOBILE = 1;
    public static final int BY_WEB = 2;
    public static final int BY_CARD = 3;
    int[] ratioAccumulation = RatioUtils.toAccumulationArray(typeRatios);
    int[] typeArray = { BY_MOBILE, BY_WEB, BY_CARD };

    public int next(Trade trade, TAccount payer, TAccount payee) {
        int select = RatioUtils.randomSelect(ratioAccumulation);
        return typeArray[select];
    }
}

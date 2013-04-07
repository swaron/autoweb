package cc.epass.factory.generator.trade;

import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeTypeGenerator extends Generator {
    int[] typeRatios = { 2, 1, 5, 8 };
    // @1 提现,2 退款,3 充值, 4 消费
    public static final int WITH_DRAW = 1;
    public static final int REFUND = 2;
    public static final int DEPOSIT = 3;
    public static final int CONSUME = 4;
    int[] ratioAccumulation = RatioUtils.toAccumulationArray(typeRatios);
    int[] typeArray = { WITH_DRAW, REFUND, DEPOSIT, CONSUME };

    public int next(Trade trade, TAccount merchant, TAccount payee) {
        int select = RatioUtils.randomSelect(ratioAccumulation);
        int type = typeArray[select];
        return type;
    }
  
}

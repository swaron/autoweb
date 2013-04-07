package cc.epass.factory.generator.trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeAmountGenerator extends Generator {
    @Autowired
    TurnOverSingleGenerator turnOverSingleGenerator;

    public Double next(Trade trade, TAccount payer, TAccount payee) {
        Double total = trade.getTurnOverSingle();
        for (int i = 1; i < trade.getTradeNumbers(); i++) {
            total += turnOverSingleGenerator.next(trade, payer, payee);
        }
        return total;
    }

}

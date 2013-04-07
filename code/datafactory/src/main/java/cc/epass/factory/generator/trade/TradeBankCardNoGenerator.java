package cc.epass.factory.generator.trade;

import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeBankCardNoGenerator extends Generator {

    public String next(Trade trade, TAccount payer, TAccount payee) {
        return payer.getAccountBankCardNo();
    }
}

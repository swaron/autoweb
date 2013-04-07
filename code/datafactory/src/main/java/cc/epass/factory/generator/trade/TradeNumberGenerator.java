package cc.epass.factory.generator.trade;

import java.util.List;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeNumberGenerator extends Generator {
    RandomData randomData = new RandomDataImpl();

    public Integer next(Trade trade, TAccount payer, TAccount payee) {
        double randomNext = randomData.nextExponential(1);
        int floor = (int) randomNext + 1;
        return floor;
    }
}

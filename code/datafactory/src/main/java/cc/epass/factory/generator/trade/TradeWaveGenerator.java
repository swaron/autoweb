package cc.epass.factory.generator.trade;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.commons.math.stat.StatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeWaveGenerator extends Generator {
    @Autowired
    TurnOverSingleGenerator turnOverSingleGenerator;
    
    RandomData randomData = new RandomDataImpl();

    public Double next(Trade trade, TAccount payer, TAccount payee) {
        Integer tradeNum = trade.getTradeNumbers();
        double[] values = new double[tradeNum];
        for (int i = 0; i < values.length; i++) {
            values[i] = turnOverSingleGenerator.next(trade, payer,payee) / turnOverSingleGenerator.getMean();
        }
        return StatUtils.variance(values);
    }

    public static void main(String[] args) {
        RandomData randomData = new RandomDataImpl();
        for (int i = 0; i < 1000; i++) {
            double x = Math.abs(randomData.nextGaussian(500, 3500));
            if (x > 10000) {
                System.out.println(x);
            }
        }
    }
}

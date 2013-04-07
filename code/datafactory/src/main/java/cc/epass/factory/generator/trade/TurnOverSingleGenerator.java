package cc.epass.factory.generator.trade;

import java.util.List;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.commons.math.util.MathUtils;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TurnOverSingleGenerator extends Generator {
    private int mean = 500;
    private int standardDeviation = 3500;
    RandomData randomData = new RandomDataImpl();

    public Double next(Trade trade, TAccount payer, TAccount payee) {
        double nextGaussian = Math.abs(randomData.nextGaussian(mean, standardDeviation));
        return MathUtils.round(nextGaussian, 2);
    }

    public int getMean() {
        return mean;
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

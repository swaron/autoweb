package cc.epass.factory.generator.trade;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeCardNoGenerator extends Generator {
    int[] typeRatios = { 100, 10, 1 };
    int[] ratioAccumulation = RatioUtils.toAccumulationArray(typeRatios);
    int[] typeArray = { 1234567, 7132654, 2613825 };
    String prefix = "16888861";

    public String next(Trade trade, TAccount payer, TAccount payee) {
        String bankCardNo = payer.getAccountBankCardNo();
        int select = RatioUtils.randomSelect(ratioAccumulation);
        String trail = StringUtils.substring(bankCardNo, -7);
        int trailInt = Integer.parseInt(trail);
        int seed = typeArray[select];
        int resultInt = (trailInt ^ seed) & 0x7FFFFF;
        String newTrail = StringUtils.leftPad(String.valueOf(resultInt), 7, '0');
        String middle = bankCardNo.substring(0, 4);
        return prefix.concat(middle).concat(newTrail);
    }
}

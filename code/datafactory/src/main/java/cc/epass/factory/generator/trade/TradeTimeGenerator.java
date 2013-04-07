package cc.epass.factory.generator.trade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.Generator;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Trade;

@Component
public class TradeTimeGenerator extends Generator {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Date next(Trade trade, TAccount payer, TAccount payee) {
        Date date = context.getEndDay();
        AtomicLong current = (AtomicLong) context.getSessionAttribute("current");
        int index = (int) current.getAndIncrement();
        int[] randomDiff = context.getRandomDiff();
        if (index < randomDiff.length) {
            Date tradeTime = DateUtils.addMilliseconds(date, -randomDiff[index]);
            return tradeTime;
        }
        return null;
    }
}

package cc.epass.factory.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;

import cc.epass.factory.core.GeneratorRuntime;
import cc.epass.factory.interfaces.TradeFactory;
import cc.epass.factory.repo.jdbc.AccountJdbcDao;
import cc.epass.factory.repo.jdbc.TradeJdbcDao;
import cc.epass.factory.session.GenerateRequest;
import cc.epass.factory.session.RequestContext;
import cc.epass.schemas.checking.v10.Trade;

public class GenerateTrade4CurrentTest extends BaseTest {
    @Autowired
    TradeFactory tradeFactory;

    @Autowired
    AccountJdbcDao accountJdbcDao;
    
    @Autowired
    TradeJdbcDao tradeJdbcDao;

    @Autowired
    GeneratorRuntime runtime;

    @Autowired
    RequestContext requestContext;

    @Before
    public void beforeClass() {
        GenerateRequest request = new GenerateRequest();
        request.setAttribute("amountPerDay", 125000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = new Date();
        try {
             endDate = dateFormat.parse("2012-02-03");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.setAttribute("endDay", endDate);
        request.setAttribute("totalDay", 1);
        if (requestContext.getRequest() == null) {
            requestContext.initContext(request);
        }
    }

    @Test
    @Repeat(3)
    public void testGenerateOneTrade() throws Exception {
        Trade trade = tradeFactory.next(requestContext);
        System.out.println(objectMapper.writeValueAsString(trade));
        trade = tradeJdbcDao.add(trade);
    }

    @Test
    public void testGenerateNTrade_current() throws Exception {
        org.springframework.util.StopWatch stopWatch = new org.springframework.util.StopWatch();
        int batchSize = 1000;
        int total = 125000;
        for (int i = 0; i < total/batchSize; i++) {
            stopWatch.start("task " + String.valueOf(i));
            List<Trade> trades = new ArrayList<Trade>();
            for (int j = 0; j < batchSize; j++) {
                Trade trade = tradeFactory.next(requestContext);
                trades.add(trade);
            }
            tradeJdbcDao.addCurrent(trades);
            stopWatch.stop();
            System.out.println("iteration "+i+" generated " + batchSize + " Trade_Current in " + stopWatch.getLastTaskTimeMillis() + " ms.");
        }
        /*  List<Trade> lastList = tradeFactory.lastList(requestContext, total);
        tradeJdbcDao.add(lastList);*/
    }

}

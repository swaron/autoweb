package cc.epass.factory.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Repeat;

import cc.epass.factory.core.GeneratorRuntime;
import cc.epass.factory.interfaces.AccountFactory;
import cc.epass.factory.repo.jdbc.AccountJdbcDao;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.factory.session.GenerateRequest;
import cc.epass.factory.session.RequestContext;
import cc.epass.schemas.checking.v10.Account;

public class GenerateAccountTest extends BaseTest {
    @Autowired
    AccountFactory accountFactory;
    
    @Autowired
    AccountJdbcDao accountJdbcDao;

    @Autowired
    GeneratorRuntime runtime;
    
    @Test
    @Repeat(10)
    public void testGenerateOneAccount() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        GenerateRequest request = new GenerateRequest();
        RequestContext requestContext = runtime.createContext(request);
        Account account = accountFactory.next(requestContext);
        Assert.assertNotNull(account.getAccountArea());
        System.out.println(objectMapper.writeValueAsString(account));
        accountJdbcDao.add(account);
        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

    @Test
    public void testGenerateNAccount() throws Exception {
        org.springframework.util.StopWatch stopWatch = new org.springframework.util.StopWatch();
        GenerateRequest request = new GenerateRequest();
        RequestContext context = runtime.createContext(request);
        int batchSize = 1000;
        int total = 500000;
        for (int i = 0; i < total/batchSize; i++) {
            stopWatch.start("task " + String.valueOf(i));
            List<Account> accounts = new ArrayList<Account>();
            for (int j = 0; j < batchSize; j++) {
                Account next = accountFactory.next(context);
                accounts.add(next);
            }
            accountJdbcDao.add(accounts);
            stopWatch.stop();
            System.out.println("iteration "+i+" generated " + batchSize + " account in " + stopWatch.getLastTaskTimeMillis() + " ms.");
        }
        System.out.println(stopWatch.prettyPrint());
    }
}

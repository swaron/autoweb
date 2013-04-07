package cc.epass.factory.generator.account;

import static org.junit.Assert.*;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;

import cc.epass.factory.generator.account.MobileNumGenerator;
import cc.epass.factory.generator.account.AddressGenerator.Address;
import cc.epass.factory.test.BaseTest;
import cc.epass.schemas.checking.v10.Account;

public class MobileNumGeneratorTest extends BaseTest{

    @Autowired
    MobileNumGenerator generator;
    
    @Test
    @Repeat(10)
    public void testGetMobileNumList() {
        StopWatch watch = new StopWatch();
        watch.start();
        Account account = new Account();
        generator.next(account);
        watch.stop();
        System.out.println(watch.toString());
    }

}

package cc.epass.factory.generator.account;

import static org.junit.Assert.*;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;

import cc.epass.factory.generator.account.AddressGenerator;
import cc.epass.factory.generator.account.AddressGenerator.Address;
import cc.epass.factory.test.BaseTest;

public class AddressGeneratorTest extends BaseTest {

    @Autowired
    AddressGenerator addressGenerator;
    
    @Test
    @Repeat(20)
    public void testNext() {
        StopWatch watch = new StopWatch();
        watch.start();
        Address next = addressGenerator.next();
        watch.stop();
        System.out.println(watch.toString());
    }

}

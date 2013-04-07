package cc.epass.factory.generator.account;

import static org.junit.Assert.*;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;

import cc.epass.factory.generator.account.IpGenerator;
import cc.epass.factory.test.BaseTest;

public class IpGeneratorTest extends BaseTest{

    @Autowired
    IpGenerator generator;
    
    @Test
    @Repeat(5)
    public void testGenRandomChinaIp() {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(generator.genRandomChinaIp());
        watch.stop();
        System.out.println(watch.toString());
    }
}

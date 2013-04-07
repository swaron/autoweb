package cc.epass.factory.generator.name;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import cc.epass.factory.test.BaseTest;
import cc.epass.schemas.checking.v10.Account;

public class SurNameGeneratorTest extends BaseTest{

    @Autowired
    SurNameGenerator generator;
    
    @Test
    public void testNext() {
        Account account = new Account();
        String next = generator.next(account);
        Assert.notNull(next);
        System.out.println(next);
    }

}

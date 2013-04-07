package cc.epass.factory.generator.account;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cc.epass.factory.test.BaseTest;

public class IdGeneratorTest extends BaseTest{
    @Autowired
    IdGenerator idGenerator;

    
    @Test
    public void testNext() {
        boolean result = idGenerator.validateId("34052419800101001X");
//        assertTrue(result);
    }
}

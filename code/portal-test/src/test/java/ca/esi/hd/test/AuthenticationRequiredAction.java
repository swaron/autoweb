package ca.esi.hd.test;

import org.testng.annotations.BeforeClass;

public class AuthenticationRequiredAction extends BaseTest {

    @BeforeClass(alwaysRun = true)
    @Override
    public void tryLoginIfNot() {
        super.tryLoginIfNot();
    }
}

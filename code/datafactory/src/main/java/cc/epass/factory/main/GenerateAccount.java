package cc.epass.factory.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import cc.epass.factory.core.GeneratorRuntime;
import cc.epass.factory.interfaces.AccountFactory;
import cc.epass.factory.repo.jdbc.AccountJdbcDao;
import cc.epass.factory.session.GenerateRequest;
import cc.epass.factory.session.RequestContext;
import cc.epass.schemas.checking.v10.Account;

public class GenerateAccount {
    GeneratorRuntime runtime = new GeneratorRuntime();
    AccountFactory accountFactory;
    AccountJdbcDao accountJdbcDao;
    
    public static void main(String[] args) {
        GenerateAccount gen = new GenerateAccount();
        gen.execute();

    }

    private void execute() {
        String[] configLocations = new String[] { "app-context.xml", "cache-context.xml", "datasource.xml" };
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
        accountFactory = context.getBean("accountFactory", AccountFactory.class);
        accountJdbcDao = context.getBean("accountJdbcDao", AccountJdbcDao.class);
        GenerateRequest request = new GenerateRequest();
        RequestContext requestContext = runtime.createContext(request);
        Account account = accountFactory.next(requestContext);
        accountJdbcDao.add(account);
        context.close();
        
    }
}

package cc.epass.factory.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import cc.epass.factory.generator.account.*;
import cc.epass.factory.generator.account.AddressGenerator.Address;
import cc.epass.factory.generator.name.AccountNameGenerator;
import cc.epass.factory.session.RequestContext;
import cc.epass.schemas.checking.v10.Account;
import cc.epass.schemas.checking.v10.ObjectFactory;

@Service
public class AccountFactory {
    @Autowired
    AddressGenerator addressGenerator;

    @Autowired
    AccountTypeGenerator accountTypeGenerator;
    
    @Autowired
    AccountStatusGenerator accountStatusGenerator;
    
    @Autowired
    BankCardNumGenerator bankCardNumGenerator;
    
    @Autowired
    AccountNumGenerator accountNumGenerator;
    
    @Autowired
    AccountNameGenerator accountNameGenerator;
    
    @Autowired
    PasswordGenerator passwordGenerator;
    
    @Autowired
    IpGenerator ipGenerator;
    
    @Autowired
    LoginIpGenerator loginIpGenerator;
    
    @Autowired
    LogFailCountGenerator failCountGenerator;
    
    @Autowired
    MobileNumGenerator mobileNumGenerator;
    
    @Autowired
    MailGenerator mailGenerator;
    
    @Autowired
    MacGenerator macGenerator;
    
    @Autowired
    ExploreInfoGenerator exploreInfoGenerator;
    
    ObjectFactory objectFactory = new ObjectFactory();
    
    public Account next(RequestContext context) {
        Account account = objectFactory.createAccount();
        StopWatch watch = new StopWatch("generate account");
        //zipcode, e.g.:454450,100084
        watch.start("Addr");
        Address addr = addressGenerator.next();
        account.setAccountArea(addr.getZip());
        account.setAccountPostArea(addr.getPostAddr());
        account.setAccountApplayArea(addr.getShippingAddr());
        watch.stop();
        watch.start("AccountType");
        account.setAccountType(accountTypeGenerator.next(account));
        watch.stop();
        watch.start("AccountStatus");
        account.setAccountStatus(accountStatusGenerator.next(account));
        watch.stop();
        watch.start("BankCardNum");
        account.setAccountBankCardNo(bankCardNumGenerator.next(account));
        watch.stop();
        watch.start("AccountNum");
        account.setAccountNo(accountNumGenerator.next(account));
        watch.stop();
        watch.start("Name");
        account.setAccountName(accountNameGenerator.next(account));
        watch.stop();
        watch.start("RegIp");
        account.setAccountRegIp(ipGenerator.next(account));
        watch.stop();
        watch.start("LoginIp");
        account.setAccountLoginIp(loginIpGenerator.next(account));
        watch.stop();
        watch.start("Password");
        account.setPassword(passwordGenerator.next(account));
        watch.stop();
        watch.start("LogFailCount");
        account.setLogFailCount(failCountGenerator.next(account));
        watch.stop();
        watch.start("Phoneno");
        account.setPhoneno(mobileNumGenerator.next(account));
        watch.stop();
        watch.start("Mail");
        account.setMail(mailGenerator.next(account));
        watch.stop();
        watch.start("Mac");
        account.setMac(macGenerator.next(account));
        watch.stop();
        watch.start("ExploreInfo");
        account.setExploreInfo(exploreInfoGenerator.next(account));
//        System.out.println(watch.prettyPrint());
        return account;
    }
    public List<Account> lastList(RequestContext context, int n) {
        // TODO Auto-generated method stub
        return null;
    }
}

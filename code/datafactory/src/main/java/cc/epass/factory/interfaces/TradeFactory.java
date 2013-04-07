package cc.epass.factory.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.epass.factory.generator.trade.*;
import cc.epass.factory.repo.jdbc.AccountJdbcDao;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.factory.session.RequestContext;
import cc.epass.schemas.checking.v10.Account;
import cc.epass.schemas.checking.v10.ObjectFactory;
import cc.epass.schemas.checking.v10.Trade;
import cc.epass.schemas.util.XmlDateUtils;

@Service
public class TradeFactory {
    @Autowired
    AccountJdbcDao accountJdbcDao;
    @Autowired
    TradeTimeGenerator tradeTimeGenerator;
    @Autowired
    TradeNumberGenerator tradeNumberGenerator;
    @Autowired
    TurnOverSingleGenerator turnOverSingleGenerator;
    @Autowired
    TradeAmountGenerator tradeAmountGenerator;
    @Autowired
    TradeWaveGenerator tradeWaveGenerator;
    @Autowired
    TradeAreaGenerator tradeAreaGenerator;
    @Autowired
    TradeTypeGenerator tradeTypeGenerator;
    @Autowired
    TradeKindGenerator tradeKindGenerator;
    @Autowired
    TradeStatusCodeGenerator tradeStatusCodeGenerator;
    @Autowired
    TradeCardNoGenerator tradeCardNoGenerator;
    @Autowired
    TradeBankCardNoGenerator tradeBankCardNoGenerator;
    @Autowired
    TradeIpGenerator tradeIpGenerator;
    @Autowired
    TradeMacGenerator tradeMacGenerator;
    @Autowired
    TradeExploreInfoGenerator exploreInfoGenerator;
    
    ObjectFactory objectFactory = new ObjectFactory();

    public Trade next(RequestContext context) {
        List<TAccount> pair = accountJdbcDao.findAccountPair();
        TAccount merchant = pair.get(0);
        TAccount account = pair.get(1);
        Trade trade = objectFactory.createTrade();
        trade.setTradeType(tradeTypeGenerator.next(trade, merchant,account));
        trade.setTradeTime(XmlDateUtils.toXMLGregorianCalendar(tradeTimeGenerator.next(trade,merchant,account)));
        trade.setTradeNumbers(tradeNumberGenerator.next(trade,merchant,account));
        trade.setTurnOverSingle(turnOverSingleGenerator.next(trade, merchant, account));
        trade.setTradeAmount(tradeAmountGenerator.next(trade,merchant,account));
        trade.setTradeWave(tradeWaveGenerator.next(trade, merchant,account));
        trade.setTradeArea(tradeAreaGenerator.next(trade, merchant,account));
        trade.setTradeKind(tradeKindGenerator.next(trade, merchant,account));
        trade.setTradeStatusCode(tradeStatusCodeGenerator.next(trade, merchant,account));
        trade.setTradeCardNo(tradeCardNoGenerator.next(trade, merchant, account));
        setTradeAccounts(trade, merchant, account);
        trade.setTradeIp(tradeIpGenerator.next(trade, merchant, account));
        trade.setTradeMac(tradeMacGenerator.next(trade, merchant, account));
        trade.setExploreInfo(exploreInfoGenerator.next(trade, merchant, account));
//        trade.setTradeCreatedTime(XmlDateUtils.toXMLGregorianCalendar(new Date()));
//        trade.setTradeUpdatedTime(XmlDateUtils.toXMLGregorianCalendar(new Date()));
        
        return trade;
    }

    private void setTradeAccounts(Trade trade, TAccount merchant, TAccount account) {
        int type = trade.getTradeType();
        if(type  == TradeTypeGenerator.WITH_DRAW || type ==TradeTypeGenerator.CONSUME ){
            trade.setTradeBankCardNo(account.getAccountBankCardNo());
            trade.setPayerAccountNo(account.getAccountNo());
            trade.setPayerAccountId(account.getId());
            trade.setPayeeAccountNo(merchant.getAccountNo());
            trade.setPayeeAccountId(merchant.getId());
        }else{
            trade.setTradeBankCardNo(account.getAccountBankCardNo());
            trade.setPayerAccountNo(merchant.getAccountNo());
            trade.setPayerAccountId(merchant.getId());
            trade.setPayeeAccountNo(account.getAccountNo());
            trade.setPayeeAccountId(account.getId());
        }
    }

    public List<Trade> lastList(RequestContext context, int n) {
//        tradeJdbcDao.get("");
        // TODO Auto-generated method stub
        return null;
    }
}

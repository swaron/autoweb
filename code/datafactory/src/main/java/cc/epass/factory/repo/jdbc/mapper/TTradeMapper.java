package cc.epass.factory.repo.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cc.epass.factory.repo.model.TAccount;
import cc.epass.factory.repo.model.TTrade;

/**
 * The persistent class for the t_account database table.
 * 
 */
public class TTradeMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        TTrade trade = new TTrade();
        // TODO Auto-generated method stub
        trade.setId(rs.getLong("id"));
        trade.setTradeTime(rs.getString("tradeTime"));
        trade.setTradeNumbers(rs.getInt("tradeNumbers"));
        trade.setTurnOverSingle(rs.getDouble("turnOverSingle"));
        trade.setTradeAmount(rs.getDouble("tradeAmount"));
        trade.setTradeWave(rs.getDouble("tradeWave"));
        trade.setTradeArea(rs.getString("tradeArea"));
        trade.setTradeType(rs.getInt("tradeType"));
        trade.setTradeKind(rs.getInt("tradeKind"));
        trade.setTradeStatusCode(rs.getInt("tradeStatusCode"));
        trade.setTradeDirect(rs.getInt("tradeDirect"));
        trade.setTradeCardNo(rs.getString("tradeCardNo"));
        trade.setTradeBankCardNo(rs.getString("tradeBankCardNo"));
        trade.setPayerAccountNo(rs.getString("payeeAccountNo"));
        TAccount payer = new TAccount();
        payer.setId(rs.getLong("payerAccountId"));
        trade.setPayer(payer);
        trade.setPayeeAccountNo(rs.getString("payeeAccountNo"));
        TAccount payee = new TAccount();
        payee.setId(rs.getLong("payeeAccountId"));
        trade.setPayee(payee);
        trade.setTradeIp(rs.getString("tradeIp"));
        trade.setTradeMac(rs.getString("tradeMac"));
        trade.setExploreInfo(rs.getString("exploreInfo"));
        trade.setTradeCreatedTime(rs.getTimestamp("tradeCreatedTime"));
        trade.setTradeUpdatedTime(rs.getTimestamp("tradeUpdatedTime"));
        return trade;
    }
}

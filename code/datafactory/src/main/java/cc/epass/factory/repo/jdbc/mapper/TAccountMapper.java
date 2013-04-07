package cc.epass.factory.repo.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cc.epass.factory.repo.model.TAccount;

/**
 * The persistent class for the t_account database table.
 * 
 */
public class TAccountMapper implements RowMapper<TAccount> {

    @Override
    public TAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
        TAccount account = new TAccount();
        account.setId(rs.getLong("t_account_id"));
        account.setAccountArea(rs.getString("account_area"));
        account.setAccountPostArea(rs.getString("account_post_area"));
        account.setAccountApplayArea(rs.getString("account_applay_area"));
        account.setAccountType(rs.getInt("account_type"));
        account.setAccountStatus(rs.getInt("account_status"));
        account.setAccountBankCardNo(rs.getString("account_bank_card_no"));
        account.setAccountNo(rs.getString("account_no"));
        account.setAccountName(rs.getString("account_name"));
        account.setAccountRegIp(rs.getString("account_reg_ip"));
        account.setAccountLoginIp(rs.getString("account_login_ip"));
        account.setPassword(rs.getString("password"));
        account.setLogFailCount(rs.getInt("log_fail_count"));
        account.setPhoneno(rs.getString("phoneno"));
        account.setMail(rs.getString("mail"));
        account.setMac(rs.getString("mac"));
        account.setExploreInfo(rs.getString("explore_info"));
        account.setAccountCreatedTime(rs.getTimestamp("account_created_time"));
        account.setAccountUpdatedTime(rs.getTimestamp("account_updated_time"));
        return account;
    }
}

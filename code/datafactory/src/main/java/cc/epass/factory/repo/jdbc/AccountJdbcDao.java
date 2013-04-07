package cc.epass.factory.repo.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.buffer.BoundedFifoBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cc.epass.factory.generator.account.AccountTypeGenerator;
import cc.epass.factory.repo.jdbc.mapper.TAccountMapper;
import cc.epass.factory.repo.model.TAccount;
import cc.epass.schemas.checking.v10.Account;

@Repository
public class AccountJdbcDao {
    public static final int buffer_size = 1000;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private BoundedFifoBuffer buffer = new BoundedFifoBuffer(buffer_size);

    @Autowired
    @Qualifier("dataSource_easylife")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("t_account").usingGeneratedKeyColumns("t_account_id");
    }

    public Account add(Account a) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(a);
        Number newId = jdbcInsert.executeAndReturnKey(parameters);
        a.setId(newId.longValue());
        return a;
    }

    public List<TAccount> findAccountPair() {
        TAccount merchant = randomMerchantAccount();
        TAccount payee = randomNormalAccount();
        List<TAccount> pair = new ArrayList<TAccount>(2);
        pair.add(merchant);
        pair.add(payee);
        return pair;
    }

    @SuppressWarnings("unchecked")
    public TAccount randomNormalAccount() {
        if (buffer.isEmpty()) {
            String sql = "select * from t_account where account_type = ? order by rand() limit ?";
            List<TAccount> list = jdbcTemplate.query(sql, new Object[] {AccountTypeGenerator.CustomerType, buffer_size }, new TAccountMapper());
            buffer.addAll(list);
        }
        return (TAccount) buffer.remove();
    }
    
    @SuppressWarnings("unchecked")
    public TAccount randomMerchantAccount() {
        if (buffer.isEmpty()) {
            String sql = "select * from t_account where account_type = ?  order by rand() limit ?";
            List<TAccount> list = jdbcTemplate.query(sql, new Object[] {AccountTypeGenerator.MerchantType,buffer_size }, new TAccountMapper());
            buffer.addAll(list);
        }
        return (TAccount) buffer.remove();
    }

    public void add(List<Account> accounts) {
        SqlParameterSource[] batch = new SqlParameterSource[accounts.size()];
        for (int i = 0; i < batch.length; i++) {
            batch[i] = new BeanPropertySqlParameterSource(accounts.get(i));
        }
        jdbcInsert.executeBatch(batch);
    }
}

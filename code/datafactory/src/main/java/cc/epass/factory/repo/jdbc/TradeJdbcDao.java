package cc.epass.factory.repo.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cc.epass.schemas.checking.v10.Trade;

@Repository
public class TradeJdbcDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert4History;
    private SimpleJdbcInsert jdbcInsert4Current;

    @Autowired
    @Qualifier("dataSource_easylife")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert4History = new SimpleJdbcInsert(dataSource).withTableName("t_trade_history").usingGeneratedKeyColumns(
                "id");
        this.jdbcInsert4Current = new SimpleJdbcInsert(dataSource).withTableName("t_trade_current").usingGeneratedKeyColumns(
        "id");
    }
 

    public void addHistory(List<Trade> trades) {
        SqlParameterSource[] batch = new SqlParameterSource[trades.size()];
        for (int i = 0; i < batch.length; i++) {
            batch[i] = new BeanPropertySqlParameterSource(trades.get(i));
        }
        jdbcInsert4History.executeBatch(batch);
    }
    public void addCurrent(List<Trade> trades) {
        SqlParameterSource[] batch = new SqlParameterSource[trades.size()];
        for (int i = 0; i < batch.length; i++) {
            batch[i] = new BeanPropertySqlParameterSource(trades.get(i));
        }
        jdbcInsert4Current.executeBatch(batch);
    }

    public Trade add(Trade trade) throws IllegalAccessException, InvocationTargetException {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(trade);
        Number newId = jdbcInsert4History.executeAndReturnKey(parameters);
        trade.setId(newId.longValue());
        return trade;
    }

 
}

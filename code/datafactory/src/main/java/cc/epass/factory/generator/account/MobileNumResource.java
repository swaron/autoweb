package cc.epass.factory.generator.account;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MobileNumResource {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("dataSource_datafactory")
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Cacheable(value = "mobile_num")
    public List<String> getMobileNumList(String key) {
        String sql = "SELECT distinct mobile_num FROM mobile_location where city like ?";
        List<String> list = jdbcTemplate.queryForList(sql, new Object[] { "%" + key + "%" }, String.class);
        return list;
    }
}

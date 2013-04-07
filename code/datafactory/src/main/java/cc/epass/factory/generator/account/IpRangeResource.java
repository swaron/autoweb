package cc.epass.factory.generator.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cc.epass.factory.generator.account.IpGenerator.IpRange;

@Component
public class IpRangeResource {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("dataSource_datafactory")
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Cacheable(value = "ip_by_location")
    public List<IpRange> getIpListByAddress(String key) {
        String sql = "SELECT country,start_ip,end_ip FROM ip_addr where country like ?";
        List<IpRange> list = jdbcTemplate.query(sql, new Object[] { key + "%" }, mapper);
        return list;
    }

    @Cacheable(value = "china_ip")
    public List<IpRange> getChinaIpList() {
        String sql = "SELECT country,start_ip,end_ip FROM ip_addr where country like ? OR country like ? OR country like ?";
        return jdbcTemplate.query(sql, new Object[] { "%区%", "%市%", "%省%" }, mapper);
    }

    RowMapper<IpRange> mapper = new RowMapper<IpRange>() {
        public IpRange mapRow(ResultSet rs, int rowNum) throws SQLException {
            IpRange ipRange = new IpRange();
            ipRange.setLocation(rs.getString("country"));
            ipRange.setStartIp(rs.getString("start_ip"));
            ipRange.setEndIp(rs.getString("end_ip"));
            return ipRange;
        }
    };
}

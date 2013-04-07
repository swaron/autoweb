package cc.epass.factory.generator.account;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.math.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cc.epass.factory.util.InetUtils;
import cc.epass.schemas.checking.v10.Account;

@Component
public class IpGenerator {
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("dataSource_datafactory")
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired
    IpRangeResource ipRangeResource;

    public static class IpRange implements Serializable{
        private static final long serialVersionUID = -5161717855980846539L;
        String location;
        String startIp;
        String endIp;

        public IpRange(String location, String startIp, String endIp) {
            super();
            this.location = location;
            this.startIp = startIp;
            this.endIp = endIp;
        }

        public IpRange() {
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStartIp() {
            return startIp;
        }

        public void setStartIp(String startIp) {
            this.startIp = startIp;
        }

        public String getEndIp() {
            return endIp;
        }

        public void setEndIp(String endIp) {
            this.endIp = endIp;
        }

    }

    public String next(Account account) {
        String addr = account.getAccountPostArea();
        if (addr == null) {
            return genRandomChinaIp();
        }
        String result = null;
        if (addr.contains("县")) {
            String key = addr.substring(0, addr.indexOf("县") + 1);
            result = genRandomIpByAddress(key);
        } else if (addr.contains("区")) {
            String key = addr.substring(0, addr.indexOf("区") + 1);
            result = genRandomIpByAddress(key);
        } else if (addr.contains("市")) {
            String key = addr.substring(0, addr.indexOf("市") + 1);
            result = genRandomIpByAddress(key);
        } else if (addr.contains("省")) {
            String key = addr.substring(0, addr.indexOf("省") + 1);
            result = genRandomIpByAddress(key);
        }
        if (result == null) {
            result = genRandomChinaIp();
        }
        return result;
    }

    private String genRandomIpByAddress(String key) {
        List<IpRange> list = ipRangeResource.getIpListByAddress(key);
        if (!list.isEmpty()) {
            int index = RandomUtils.nextInt(list.size());
            IpRange ipRange = list.get(index);
            return randomSelectIp(ipRange);
        } else {
            return null;
        }
    }

    public String genRandomChinaIp() {
        List<IpRange> list = ipRangeResource.getChinaIpList();
        Assert.isTrue(!list.isEmpty());
        int index = RandomUtils.nextInt(list.size());
        IpRange ipRange = list.get(index);
        return randomSelectIp(ipRange);
    }


    public String randomSelectIp(IpRange ipRange) {
        Long start = InetUtils.parse(ipRange.getStartIp());
        Long end = InetUtils.parse(ipRange.getEndIp());
        int random = RandomUtils.nextInt(Long.valueOf(end - start + 1).intValue());
        Long selected = start + random;
        return InetUtils.print(selected);
    }
}

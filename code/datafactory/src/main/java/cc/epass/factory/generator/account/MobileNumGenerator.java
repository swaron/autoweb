package cc.epass.factory.generator.account;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cc.epass.schemas.checking.v10.Account;

@Component
public class MobileNumGenerator {
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("dataSource_datafactory")
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired
    MobileNumResource mobileNumResource;

    private int sampleCount;
    
    @PostConstruct
    public void initialize() {
        sampleCount = jdbcTemplate.queryForInt("select count(*) from mobile_location");
    }

    public String next(Account account) {
        String addr = account.getAccountPostArea();
        if (addr == null) {
            return genRandomMobileNum();
        }
        String result = null;
        if (addr.contains("省")) {
            addr = addr.substring(addr.indexOf("省") + 1);
        }
        if (addr.contains("市")) {
            addr = addr.substring(0, addr.indexOf("市") + 1);
        }
        result = genRandomMobileNumByLocation(addr);
        if (result == null) {
            result = genRandomMobileNum();
        }
        return result;
    }

    private String genRandomMobileNum() {
        String prefix = getRandomPrefix();
        if (prefix == null) {
            prefix = RandomStringUtils.randomNumeric(7);
        }
        return prefix + RandomStringUtils.randomNumeric(4);
    }

    private String genRandomMobileNumByLocation(String key) {
        String prefix = getRandomPrefixByKey(key);
        if (prefix == null) {
            prefix = getRandomPrefix();
        }
        return prefix + RandomStringUtils.randomNumeric(4);
    }

    private String getRandomPrefix() {
        String sql = "SELECT mobile_num FROM mobile_location OFFSET trunc(random()*?) LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new Object[] { sampleCount }, String.class);
    }

    private String getRandomPrefixByKey(String key) {
        List<String> list = mobileNumResource.getMobileNumList(key);
        if(list.isEmpty()){
            return null;
        }
        int index = RandomUtils.nextInt(list.size());
        return list.get(index);
    }
}

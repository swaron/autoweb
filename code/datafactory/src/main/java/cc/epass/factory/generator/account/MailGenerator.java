package cc.epass.factory.generator.account;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.collections.buffer.BoundedFifoBuffer;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cc.epass.schemas.checking.v10.Account;

@Component
public class MailGenerator {
    public static final int buffer_size = 1000;
    public static final int limited_email_count = 5000000;
    BoundedFifoBuffer buffer = new BoundedFifoBuffer(buffer_size);
    JdbcTemplate jdbcTemplate;
    private int sampleCount;

    @Autowired
    @Qualifier("dataSource_datafactory")
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void initialize() {
        sampleCount = jdbcTemplate.queryForInt("select count(*) from mobile_location");
        if(sampleCount > limited_email_count ){
            sampleCount = limited_email_count;
        }
    }

    @SuppressWarnings("unchecked")
    public String next(Account account) {
        if (buffer.isEmpty()) {
            String sql = "SELECT email FROM user_pw_ex OFFSET trunc(random()*?) LIMIT ?";
            List<String> emails = jdbcTemplate.queryForList(sql, new Object[] {sampleCount, buffer_size },
                    String.class);
            buffer.addAll(emails);
        }
        String email = (String) buffer.remove();
        if (email == null) {
            email = RandomStringUtils.randomAlphabetic(5) + "@null.com";
        }
        return email.trim();
    }

}

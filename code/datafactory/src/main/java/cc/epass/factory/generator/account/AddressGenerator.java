package cc.epass.factory.generator.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.collections.buffer.BoundedFifoBuffer;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressGenerator {
    JdbcTemplate jdbcTemplate;
    private Integer zipCodeCount;
    public static final int buffer_size = 1000;
    BoundedFifoBuffer buffer = new BoundedFifoBuffer(buffer_size);

    @Autowired
    @Qualifier("dataSource_datafactory")
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static double differentShippingRate = 0.001;

    public static class Address {
        String zip;
        String postAddr;
        String shippingAddr;

        public Address(String zip, String postAddr, String shippingAddr) {
            super();
            this.zip = zip;
            this.postAddr = postAddr;
            this.shippingAddr = shippingAddr;
        }

        public Address() {
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getPostAddr() {
            return postAddr;
        }

        public void setPostAddr(String postAddr) {
            this.postAddr = postAddr;
        }

        public String getShippingAddr() {
            return shippingAddr;
        }

        public void setShippingAddr(String shippingAddr) {
            this.shippingAddr = shippingAddr;
        }
    }

    @PostConstruct
    public void initialize() {
        zipCodeCount = jdbcTemplate.queryForInt("select count(*) from zip_code");
    }

    RowMapper<Address> mapper = new RowMapper<Address>() {
        public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
            Address addr = new Address();
            addr.setZip(rs.getString("zip_code"));
            String postAddr = rs.getString("district_name") + "," + rs.getString("sub_address") + " " + RandomStringUtils.randomNumeric(3);
            addr.setPostAddr(postAddr);
            addr.setShippingAddr(postAddr);
            return addr;
        }
    };

    public Address next() {
        Address addr = getRandomAddress();
        randomShippingAddrOccasionally(addr);
        return addr;
    }

    public void randomShippingAddrOccasionally(Address addr) {
        if (RandomUtils.nextDouble() < differentShippingRate) {
            // set to Another Address
            addr.setShippingAddr(getRandomAddress().getPostAddr());
        }
    }

    @SuppressWarnings("unchecked")
    public Address getRandomAddress() {
        if (buffer.isEmpty()) {
            String sql = "SELECT zip_code,district_name,sub_address FROM zip_code order by random() LIMIT ?";
            List<Address> addrs = jdbcTemplate.query(sql, new Object[] { buffer_size }, mapper);
            buffer.addAll(addrs);
        }
        return (Address) buffer.remove();
    }
}

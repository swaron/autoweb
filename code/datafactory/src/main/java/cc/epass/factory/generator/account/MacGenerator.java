package cc.epass.factory.generator.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import cc.epass.schemas.checking.v10.Account;

@Component
public class MacGenerator {
    List<MacInfo> macInfos;
    List<MacInfo> chinaMacInfos;

    public static class MacInfo {
        String mac;
        String company;
        String address;

        public MacInfo() {
        }

        public MacInfo(String mac, String company, String address) {
            super();
            this.mac = mac;
            this.company = company;
            this.address = address;
        }

    }

    @PostConstruct
    public void initialize() {
        try {
            List<MacInfo> infos = new ArrayList<MacGenerator.MacInfo>();
            List<MacInfo> chinaInfos = new ArrayList<MacGenerator.MacInfo>();
            String path = "oui.txt";
            ClassPathResource oui = new ClassPathResource(path, this.getClass());
            List<String> list = FileUtils.readLines(oui.getFile());
            StringBuilder buffer = new StringBuilder();
            for (String line : list) {
                if (line.isEmpty()) {
                    MacInfo info = tryBuildMacInfo(buffer.toString());
                    if (info != null) {
                        infos.add(info);
                        if (info.address.contains("CHINA")) {
                            chinaInfos.add(info);
                        }
                    }
                    buffer.delete(0, buffer.length());
                } else {
                    buffer.append(line).append('\n');
                }
            }
            macInfos = infos;
            chinaMacInfos = chinaInfos;
        } catch (IOException e) {
            throw new BeanInitializationException("initialize NameResource failed", e);
        }
    }

    private MacInfo tryBuildMacInfo(String lines) {
        String[] split = StringUtils.split(lines, "\n", 3);
        if(split.length <3){
            return null;
        }
        String line1 = split[0]; 
        String line3 = split[2];
        String[] line1Items = StringUtils.split(line1, null, 3);
        if(line1Items.length <3){
            return null;
        }
        String mac = line1Items[0];
        String company = line1Items[2];
        String address = line3;
        return new MacInfo(mac, company, address);
    }

    public String next(Account account) {
        StringBuilder builder = new StringBuilder();
        int index = RandomUtils.nextInt(chinaMacInfos.size());
        String prefix = chinaMacInfos.get(index).mac;
        builder.append(prefix);
        builder.append('-');
        builder.append(RandomStringUtils.random(2, "0123456789ABCDEF"));
        builder.append('-');
        builder.append(RandomStringUtils.random(2, "0123456789ABCDEF"));
        builder.append('-');
        builder.append(RandomStringUtils.random(2, "0123456789ABCDEF"));
        return builder.toString();
    }
}

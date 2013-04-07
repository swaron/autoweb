package cc.epass.factory.generator.account;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import cc.epass.schemas.checking.v10.Account;

@Component
public class IdGenerator {
    Map<String, String> districtMap;
    String[] districtArray;
    String weightStr = "7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2";
    String checkCharMap = "10X98765432";
    int[] weight;

    @PostConstruct
    public void initialize() {
        try {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            String path = "districtcode.txt";
            ClassPathResource codes = new ClassPathResource(path, this.getClass());
            List<String> list = FileUtils.readLines(codes.getFile());
            for (String line : list) {
                if (!line.isEmpty()) {
                    String[] split = StringUtils.split(line);
                    hashMap.put(split[0], split[1]);
                }
            }
            districtMap = hashMap;
            Set<String> keySet = districtMap.keySet();
            districtArray = keySet.toArray(new String[keySet.size()]);
        } catch (IOException e) {
            throw new BeanInitializationException("initialize Resource failed", e);
        }
    }

    public int[] getWeight() {
        if (weight == null) {
            int[] w= new int[17];
            String[] split = StringUtils.split(weightStr);
            for (int i = 0; i < split.length; i++) {
                w[i] = Integer.parseInt(split[i]);
            }
            weight = w;
        }
        return weight;
    }

    public boolean validateId(String id) {
        if (id == null) {
            return false;
        }
        if (id.length() == 15) {
            String area = id.substring(6);
            if (checkArea(area) == false) {
                return false;
            }
            String birthday = id.substring(6, 12);
            if (!checkBirthDay(birthday, "yyMMdd")) {
                return false;
            }
            String seq = id.substring(12, 15);
            if (!checkSeq(seq)) {
                return false;
            }
            return true;
        } else if (id.length() == 18) {
            String area = id.substring(0,6);
            if (!checkArea(area)) {
                return false;
            }
            String birthday = id.substring(6, 14);
            if (!checkBirthDay(birthday, "yyyyMMdd")) {
                return false;
            }
            String seq = id.substring(14, 17);
            if (!checkSeq(seq)) {
                return false;
            }
            if (!validateCheckSum(id)) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public String next(Account account) {
        int nextInt = RandomUtils.nextInt(districtArray.length);
        StringBuilder builder = new StringBuilder();
        String district = districtArray[nextInt];
        String birthday = "19991111";
        String seq = "123";
        builder.append(district).append(birthday).append(seq);
        char checkSum = calcCheckSum(builder.toString());
        builder.append(checkSum);
        return builder.toString();
    }

    private boolean validateCheckSum(String id) {
        char checkSum = calcCheckSum(id);
        return Character.toUpperCase(checkSum)  == Character.toUpperCase(id.charAt(17));
    }

    public char calcCheckSum(String id) {
        int[] n = new int[17];
        int[] w = getWeight();
        // int[] sum = new int[17];
        int sum = 0;
        for (int i = 0; i < n.length; i++) {
            char c = id.charAt(i);
            n[i] = Integer.parseInt(String.valueOf(c));
        }
        for (int i = 0; i < n.length; i++) {
            sum += n[i] * w[i];
        }
        int mod = sum % 11;
        char checkSum = checkCharMap.charAt(mod);
        return checkSum;
    }

    private boolean checkSeq(String seq) {
        try {
            int parseInt = Integer.parseInt(seq);
            if (parseInt % 2 == 0) {
                // this is a female
                return true;
            } else {
                // this is a male
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkBirthDay(String birthDay, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date parse = dateFormat.parse(birthDay);
            String reFormatted = dateFormat.format(parse);
            return birthDay.equals(reFormatted);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkArea(String area) {
        return districtMap.keySet().contains(area);
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = dateFormat.parse("2012-02-50");
        String format = dateFormat.format(parse);
        System.out.println(parse);
        System.out.println(format);
    }
}

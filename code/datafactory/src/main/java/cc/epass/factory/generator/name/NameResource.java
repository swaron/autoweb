package cc.epass.factory.generator.name;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class NameResource {
    ArrayList<Character> normalNameChars;
    ArrayList<Character> popBoyChars;
    RatioCharsGenerator popBoyChars1;
    RatioCharsGenerator popBoyChars2;
    ArrayList<Character> popGirlChars;
    RatioCharsGenerator popGirlChars1;
    RatioCharsGenerator popGirlChars2;
    List<String> popBoyNames;
    List<String> popGirlNames;

    public List<String> getPopBoyNames() {
        if (popBoyNames == null) {
            initialize();
        }
        return popBoyNames;
    }

    public List<String> getPopGirlNames() {
        if (popGirlNames == null) {
            initialize();
        }
        return popGirlNames;
    }

    public ArrayList<Character> getNormalNameWords() {
        if (normalNameChars == null) {
            initialize();
        }
        return normalNameChars;
    }

    public ArrayList<Character> getPopBoyChars() {
        if (popBoyChars == null) {
            initialize();
        }
        return popBoyChars;
    }

    public RatioCharsGenerator getPopBoyChars1() {
        if (popBoyChars1 == null) {
            initialize();
        }
        return popBoyChars1;
    }

    public RatioCharsGenerator getPopBoyChars2() {
        if (popBoyChars2 == null) {
            initialize();
        }
        return popBoyChars2;
    }

    public ArrayList<Character> getPopGirlChars() {
        if (popGirlChars == null) {
            initialize();
        }
        return popGirlChars;
    }

    public RatioCharsGenerator getPopGirlChars1() {
        if (popGirlChars1 == null) {
            initialize();
        }
        return popGirlChars1;
    }

    public RatioCharsGenerator getPopGirlChars2() {
        if (popGirlChars2 == null) {
            initialize();
        }
        return popGirlChars2;
    }

    public synchronized void initialize() {
        try {
            String path = "normal-name-word.txt";
            normalNameChars = readCharsFromResource(path);
            popBoyChars = readCharsFromResource("popboyword.txt");
            popBoyChars1 = createRatioCharsGenerator("popboyname1word.txt");
            popBoyChars2 = createRatioCharsGenerator("popboyname2word.txt");
            popGirlChars = readCharsFromResource("popgirlword.txt");
            popGirlChars1 = createRatioCharsGenerator("popgirlname1word.txt");
            popGirlChars2 = createRatioCharsGenerator("popgirlname2word.txt");
            popBoyNames = readTokenFromResource("popboyname.txt");
            popGirlNames = readTokenFromResource("popgirlname.txt");
        } catch (IOException e) {
            throw new BeanInitializationException("initialize NameResource failed", e);
        }
    }

    private List<String> readTokenFromResource(String path) throws IOException {
        ClassPathResource normalWord = new ClassPathResource(path, this.getClass());
        String words = FileUtils.readFileToString(normalWord.getFile());
        String[] split = StringUtils.split(words);
        return Arrays.asList(split);
    }

    public ArrayList<Character> readCharsFromResource(String path) throws IOException {
        ClassPathResource normalWord = new ClassPathResource(path, this.getClass());
        ArrayList<Character> chars = new ArrayList<Character>();
        String words = FileUtils.readFileToString(normalWord.getFile());
        for (int i = 0; i < words.length(); i++) {
            Character c = words.charAt(i);
            if (!Character.isWhitespace(c)) {
                chars.add(c);
            }
        }
        return chars;
    }

    public RatioCharsGenerator createRatioCharsGenerator(String path) throws IOException {
        ClassPathResource normalWord = new ClassPathResource(path, this.getClass());
        List<String> lines = FileUtils.readLines(normalWord.getFile(),"utf-8");
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> amount = new ArrayList<Integer>();
        for (String l : lines) {
            if (!l.trim().isEmpty()) {
                String[] split = StringUtils.split(l);
                names.add(split[0]);
                amount.add(Integer.parseInt(split[1]));
            }
        }
        return new RatioCharsGenerator(names, amount);
    }
}

package cc.epass.factory.generator.name;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.epass.schemas.checking.v10.Account;

@Component
public class ConstructedLengthTwoNameGenerator {
    @Autowired
    NameResource nameResource;
    LengthOneNameGenerator char1Generator;
    LengthOneNameGenerator char2Generator;

    // boyword,boyname1,boyname2,normal-name-word
    int char1SourceRatio[] = { 5, 3, 2, 2 };
    int char2SourceRatio[] = { 5, 2, 3, 2 };

    @PostConstruct
    public void initialize() {
        char1Generator = new LengthOneNameGenerator();
        char1Generator.setNameResource(nameResource);
        char1Generator.setSourceRatio(char1SourceRatio);
        char2Generator = new LengthOneNameGenerator();
        char2Generator.setNameResource(nameResource);
        char2Generator.setSourceRatio(char2SourceRatio);
    }

    public String next(Account account, Gender gender, String surName) {
        String firstChar = char1Generator.next(account, gender, surName);
        String secondChar = char2Generator.next(account, gender, surName);
        return firstChar + secondChar;
    }
}

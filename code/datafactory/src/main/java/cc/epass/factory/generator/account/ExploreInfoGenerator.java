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

import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Account;

@Component
public class ExploreInfoGenerator {
    String explores[] = {"ie6","ie7","ie8","ie9","firefox","chrome","safari","opera","360","sogou","maxthon","theworld","qq"};
    int ratios[] = { 2625, 2073, 646, 284, 89, 168, 269, 15, 2833, 673, 87, 76, 157 };
    int[] ratioAccumulation = RatioUtils.toAccumulationArray(ratios);


    public String next(Account account) {
        int i = RatioUtils.randomSelect(ratioAccumulation);
        return explores[i];
    }
}

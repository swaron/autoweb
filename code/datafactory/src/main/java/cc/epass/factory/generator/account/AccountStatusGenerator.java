package cc.epass.factory.generator.account;

import org.springframework.stereotype.Service;

import cc.epass.factory.util.RatioUtils;
import cc.epass.schemas.checking.v10.Account;

@Service
public class AccountStatusGenerator {
    public static final int NORMAL = 0;
    public static final int FROZEN = 1;
    public static final int DELETED = 2;

    public int[] statusRatio = { 96, 1, 3 };
    public int[] statusRatioAccumulation = RatioUtils.toAccumulationArray(statusRatio);
    public int[] status = { NORMAL, FROZEN, DELETED };

    public Integer next(Account account) {
        int selected = RatioUtils.randomSelect(statusRatioAccumulation);
        return status[selected];
    }
}

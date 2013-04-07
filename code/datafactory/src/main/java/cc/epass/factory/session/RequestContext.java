package cc.epass.factory.session;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

@Component
public class RequestContext {
    GenerateRequest request;
    GeneratorSession session = new GeneratorSession();
    private int amountPerDay;
    private int totalDay;
    private Date endDay;
    private int[] randomDiff;

    @PostConstruct
    public void initialzie() {
        // initContext(request);
        // initialSession(request);
    }

    public void initializeTradeTimeArray() {
        int totalMillis = (int) TimeUnit.DAYS.toMillis(totalDay);
        if (totalMillis <= 0) {
            throw new IllegalArgumentException("unsupported date range");
        }
        int[] ar = new int[totalDay * amountPerDay];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = RandomUtils.nextInt(totalMillis);
        }
        Arrays.sort(ar);
        ArrayUtils.reverse(ar);
        randomDiff = ar;
    }

    public GenerateRequest getRequest() {
        return request;
    }

    public void initContext(GenerateRequest request) {
        this.request = request;
        Integer amountPerDay = (Integer) request.getAttribute("amountPerDay");
        Date endDay = (Date) request.getAttribute("endDay");
        Integer totalDay = (Integer) request.getAttribute("totalDay");
        setAmountPerDay(amountPerDay);
        setEndDay(endDay);
        setTotalDay(totalDay);
        initialSession(request);
    }

    public void initialSession(GenerateRequest request) {
        AtomicLong currentToken = new AtomicLong();
        session.setAttribute("current", currentToken);
    }

    public int getAmountPerDay() {
        return amountPerDay;
    }

    public void setAmountPerDay(int amountPerDay) {
        this.amountPerDay = amountPerDay;
    }

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public int[] getRandomDiff() {
        if (randomDiff == null) {
            initializeTradeTimeArray();
        }
        return randomDiff;
    }

    public Object getSessionAttribute(String key) {
        return session.getAttribute(key);
    }
}

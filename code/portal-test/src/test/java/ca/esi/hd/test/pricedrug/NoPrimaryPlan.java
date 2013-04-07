package ca.esi.hd.test.pricedrug;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NoPrimaryPlan {
    @org.springframework.format.annotation.NumberFormat()
public static void main(String[] args) throws ParseException {
    NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
    


         Number number = nf.parse("12,3");
         System.out.println(number.doubleValue());
}
}

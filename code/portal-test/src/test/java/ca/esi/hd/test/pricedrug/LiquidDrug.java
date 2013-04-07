package ca.esi.hd.test.pricedrug;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LiquidDrug {
    public LiquidDrug() {
        System.out.println("outer");
    }
    public class Inner{
        public Inner() {
            System.out.println("inner");
        }
        public void infun() {
            infun2();
            outfun();
        }
        public void infun2() {
            System.out.println("infun2");
        }
    }
    public void outfun() {
        System.out.println("outfun");

    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        LiquidDrug drug = new LiquidDrug();
        String encode = URLEncoder.encode("1234", "utf-8");
        System.out.println(encode);
        Inner inner = drug.new Inner();
        inner.infun();
    }
}

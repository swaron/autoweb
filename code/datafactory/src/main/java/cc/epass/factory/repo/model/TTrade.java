package cc.epass.factory.repo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * The persistent class for the t_trade database table.
 * 
 */
@Entity
@Table(name = "t_trade")
public class TTrade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String exploreInfo;

    private String payeeAccountNo;

    private String payerAccountNo;

    private double tradeAmount;

    private String tradeArea;

    private String tradeBankCardNo;

    private String tradeCardNo;

    private Timestamp tradeCreatedTime;

    private int tradeDirect;

    private String tradeIp;

    private int tradeKind;

    private String tradeMac;

    private int tradeNumbers;

    private int tradeStatusCode;

    private String tradeTime;

    private int tradeType;

    private Timestamp tradeUpdatedTime;

    private double tradeWave;

    private double turnOverSingle;

    // bi-directional many-to-one association to TAccount
    @ManyToOne
    @JoinColumn(name = "payeeAccountId")
    private TAccount payee;

    // bi-directional many-to-one association to TAccount
    @ManyToOne
    @JoinColumn(name = "payerAccountId")
    private TAccount payer;

    public TTrade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExploreInfo() {
        return this.exploreInfo;
    }

    public void setExploreInfo(String exploreInfo) {
        this.exploreInfo = exploreInfo;
    }

    public String getPayeeAccountNo() {
        return this.payeeAccountNo;
    }

    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    public String getPayerAccountNo() {
        return this.payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo;
    }

    public double getTradeAmount() {
        return this.tradeAmount;
    }

    public void setTradeAmount(double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeArea() {
        return this.tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }

    public String getTradeBankCardNo() {
        return this.tradeBankCardNo;
    }

    public void setTradeBankCardNo(String tradeBankCardNo) {
        this.tradeBankCardNo = tradeBankCardNo;
    }

    public String getTradeCardNo() {
        return this.tradeCardNo;
    }

    public void setTradeCardNo(String tradeCardNo) {
        this.tradeCardNo = tradeCardNo;
    }

    public Timestamp getTradeCreatedTime() {
        return tradeCreatedTime;
    }

    public void setTradeCreatedTime(Timestamp tradeCreatedTime) {
        this.tradeCreatedTime = tradeCreatedTime;
    }

    public int getTradeDirect() {
        return this.tradeDirect;
    }

    public void setTradeDirect(int tradeDirect) {
        this.tradeDirect = tradeDirect;
    }

    public String getTradeIp() {
        return this.tradeIp;
    }

    public void setTradeIp(String tradeIp) {
        this.tradeIp = tradeIp;
    }

    public int getTradeKind() {
        return this.tradeKind;
    }

    public void setTradeKind(int tradeKind) {
        this.tradeKind = tradeKind;
    }

    public String getTradeMac() {
        return this.tradeMac;
    }

    public void setTradeMac(String tradeMac) {
        this.tradeMac = tradeMac;
    }

    public int getTradeNumbers() {
        return this.tradeNumbers;
    }

    public void setTradeNumbers(int tradeNumbers) {
        this.tradeNumbers = tradeNumbers;
    }

    public int getTradeStatusCode() {
        return this.tradeStatusCode;
    }

    public void setTradeStatusCode(int tradeStatusCode) {
        this.tradeStatusCode = tradeStatusCode;
    }

    public String getTradeTime() {
        return this.tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public int getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public Timestamp getTradeUpdatedTime() {
        return tradeUpdatedTime;
    }

    public void setTradeUpdatedTime(Timestamp tradeUpdatedTime) {
        this.tradeUpdatedTime = tradeUpdatedTime;
    }

    public double getTradeWave() {
        return this.tradeWave;
    }

    public void setTradeWave(double tradeWave) {
        this.tradeWave = tradeWave;
    }

    public double getTurnOverSingle() {
        return this.turnOverSingle;
    }

    public void setTurnOverSingle(double turnOverSingle) {
        this.turnOverSingle = turnOverSingle;
    }

    public TAccount getPayee() {
        return payee;
    }

    public void setPayee(TAccount payee) {
        this.payee = payee;
    }

    public TAccount getPayer() {
        return payer;
    }

    public void setPayer(TAccount payer) {
        this.payer = payer;
    }

}

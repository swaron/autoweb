package cc.epass.factory.repo.model;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the t_account database table.
 * 
 */
@Entity
@Table(name = "t_account")
public class TAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountApplayArea;

    private String accountArea;

    private String accountBankCardNo;

    private Timestamp accountCreatedTime;

    private String accountLoginIp;

    private String accountName;

    private String accountNo;

    private String accountPostArea;

    private String accountRegIp;

    private int accountStatus;

    private int accountType;

    private Timestamp accountUpdatedTime;

    private String exploreInfo;

    private int logFailCount;

    private String mac;

    private String mail;

    private String password;

    private String phoneno;

    // bi-directional many-to-one association to TTrade
    @OneToMany(mappedBy = "payee")
    private List<TTrade> payeeTrade;

    // bi-directional many-to-one association to TTrade
    @OneToMany(mappedBy = "payer")
    private List<TTrade> payerTrade;

    public TAccount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountApplayArea() {
        return this.accountApplayArea;
    }

    public void setAccountApplayArea(String accountApplayArea) {
        this.accountApplayArea = accountApplayArea;
    }

    public String getAccountArea() {
        return this.accountArea;
    }

    public void setAccountArea(String accountArea) {
        this.accountArea = accountArea;
    }

    public String getAccountBankCardNo() {
        return this.accountBankCardNo;
    }

    public void setAccountBankCardNo(String accountBankCardNo) {
        this.accountBankCardNo = accountBankCardNo;
    }

    public Timestamp getAccountCreatedTime() {
        return accountCreatedTime;
    }

    public void setAccountCreatedTime(Timestamp accountCreatedTime) {
        this.accountCreatedTime = accountCreatedTime;
    }

    public String getAccountLoginIp() {
        return this.accountLoginIp;
    }

    public void setAccountLoginIp(String accountLoginIp) {
        this.accountLoginIp = accountLoginIp;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountPostArea() {
        return this.accountPostArea;
    }

    public void setAccountPostArea(String accountPostArea) {
        this.accountPostArea = accountPostArea;
    }

    public String getAccountRegIp() {
        return this.accountRegIp;
    }

    public void setAccountRegIp(String accountRegIp) {
        this.accountRegIp = accountRegIp;
    }

    public int getAccountStatus() {
        return this.accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    public int getAccountType() {
        return this.accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public Timestamp getAccountUpdatedTime() {
        return accountUpdatedTime;
    }

    public void setAccountUpdatedTime(Timestamp accountUpdatedTime) {
        this.accountUpdatedTime = accountUpdatedTime;
    }

    public String getExploreInfo() {
        return this.exploreInfo;
    }

    public void setExploreInfo(String exploreInfo) {
        this.exploreInfo = exploreInfo;
    }

    public int getLogFailCount() {
        return this.logFailCount;
    }

    public void setLogFailCount(int logFailCount) {
        this.logFailCount = logFailCount;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneno() {
        return this.phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public List<TTrade> getPayeeTrade() {
        return payeeTrade;
    }

    public void setPayeeTrade(List<TTrade> payeeTrade) {
        this.payeeTrade = payeeTrade;
    }

    public List<TTrade> getPayerTrade() {
        return payerTrade;
    }

    public void setPayerTrade(List<TTrade> payerTrade) {
        this.payerTrade = payerTrade;
    }

}

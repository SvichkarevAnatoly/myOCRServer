package com.myocr.controller.json.onlinecashier;

import java.util.List;

public class Receipt {

    private Long operationType;
    private Long nds10;
    private String fiscalDriveNumber;
    private Long fiscalDocumentNumber;
    private String rawData;
    private String user;
    private List<Item> items = null;
    private Long nds18;
    private Long shiftNumber;
    private Long taxationType;
    private Long cashTotalSum;
    private Long receiptCode;
    private Long totalSum;
    private Long requestNumber;
    private String dateTime;
    private String userInn;
    private Long fiscalSign;
    private String kktRegId;
    private Long ecashTotalSum;
    private String operator;

    public Long getOperationType() {
        return operationType;
    }

    public void setOperationType(Long operationType) {
        this.operationType = operationType;
    }

    public Long getNds10() {
        return nds10;
    }

    public void setNds10(Long nds10) {
        this.nds10 = nds10;
    }

    public String getFiscalDriveNumber() {
        return fiscalDriveNumber;
    }

    public void setFiscalDriveNumber(String fiscalDriveNumber) {
        this.fiscalDriveNumber = fiscalDriveNumber;
    }

    public Long getFiscalDocumentNumber() {
        return fiscalDocumentNumber;
    }

    public void setFiscalDocumentNumber(Long fiscalDocumentNumber) {
        this.fiscalDocumentNumber = fiscalDocumentNumber;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getNds18() {
        return nds18;
    }

    public void setNds18(Long nds18) {
        this.nds18 = nds18;
    }

    public Long getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(Long shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public Long getTaxationType() {
        return taxationType;
    }

    public void setTaxationType(Long taxationType) {
        this.taxationType = taxationType;
    }

    public Long getCashTotalSum() {
        return cashTotalSum;
    }

    public void setCashTotalSum(Long cashTotalSum) {
        this.cashTotalSum = cashTotalSum;
    }

    public Long getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(Long receiptCode) {
        this.receiptCode = receiptCode;
    }

    public Long getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Long totalSum) {
        this.totalSum = totalSum;
    }

    public Long getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(Long requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserInn() {
        return userInn;
    }

    public void setUserInn(String userInn) {
        this.userInn = userInn;
    }

    public Long getFiscalSign() {
        return fiscalSign;
    }

    public void setFiscalSign(Long fiscalSign) {
        this.fiscalSign = fiscalSign;
    }

    public String getKktRegId() {
        return kktRegId;
    }

    public void setKktRegId(String kktRegId) {
        this.kktRegId = kktRegId;
    }

    public Long getEcashTotalSum() {
        return ecashTotalSum;
    }

    public void setEcashTotalSum(Long ecashTotalSum) {
        this.ecashTotalSum = ecashTotalSum;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}

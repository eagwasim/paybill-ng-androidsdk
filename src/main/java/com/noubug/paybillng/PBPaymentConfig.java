package com.noubug.paybillng;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by BW on 16/06/2017.
 */


public class PBPaymentConfig implements Serializable {

    @SerializedName("customer_email")
    private String customerEmail;
    @SerializedName("amount_in_kobo")
    private Long amountInKobo;
    @SerializedName("organization_code")
    private String organizationCode;
    @SerializedName("organization_unique_reference")
    private String organizationUniqueReference;
    @SerializedName("organization_public_key")
    private String organizationPublicKey;
    @SerializedName("sub_account_code")
    private String subAccountCode;
    @SerializedName("organization_transaction_charge")
    private Long organizationTransactionCharge;
    @SerializedName("payment_charge_bearer")
    private String paymentChangeBearer;

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getAmountInKobo() {
        return amountInKobo;
    }

    public void setAmountInKobo(Long amountInKobo) {
        this.amountInKobo = amountInKobo;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationUniqueReference() {
        return organizationUniqueReference;
    }

    public void setOrganizationUniqueReference(String organizationUniqueReference) {
        this.organizationUniqueReference = organizationUniqueReference;
    }

    public String getOrganizationPublicKey() {
        return organizationPublicKey;
    }

    public void setOrganizationPublicKey(String organizationPublicKey) {
        this.organizationPublicKey = organizationPublicKey;
    }

    public String getSubAccountCode() {
        return subAccountCode;
    }

    public void setSubAccountCode(String subAccountCode) {
        this.subAccountCode = subAccountCode;
    }

    public Long getOrganizationTransactionCharge() {
        return organizationTransactionCharge;
    }

    public void setOrganizationTransactionCharge(Long organizationTransactionCharge) {
        this.organizationTransactionCharge = organizationTransactionCharge;
    }

    public String getPaymentChangeBearer() {
        return paymentChangeBearer;
    }

    public void setPaymentChangeBearer(String paymentChangeBearer) {
        this.paymentChangeBearer = paymentChangeBearer;
    }
}

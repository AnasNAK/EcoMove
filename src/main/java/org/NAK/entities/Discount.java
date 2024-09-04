package org.NAK.entities;


import java.util.UUID;
import java.util.Date;
import org.NAK.enums.DISCOUNTTYPE;

public class Discount {

    private UUID discountId;
    private String offerName;
    private String description;
    private Date startDate;
    private Date endDate;
    private DISCOUNTTYPE discountType;
    private float discountValue;
    private String conditions;
    private Contract contract;


    public Discount() {
        this.discountId = UUID.randomUUID();
    }

    public UUID getDiscountId() {
        return discountId;
    }

    public void setDiscountId(UUID discountId) {
        this.discountId = discountId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DISCOUNTTYPE getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DISCOUNTTYPE discountType) {
        this.discountType = discountType;
    }

    public float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(float discountValue) {
        this.discountValue = discountValue;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
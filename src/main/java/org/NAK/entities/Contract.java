package org.NAK.entities;

import java.util.List;
import java.util.UUID;
import java.util.Date;
import org.NAK.enums.CONTRACTSTATUS;

public class Contract {
    private UUID contractId;
    private Date startDate;
    private Date endDate;
    private float specialPrice;
    private String agreementConditions;
    private boolean renewable;
    private CONTRACTSTATUS contractStatus;
    private Partner Partner;

    private List<Ticket> ticketList;


    public Contract() {
        this.contractId = UUID.randomUUID();
    }

    public UUID getContractId() {
        return contractId;
    }

    public void setContractId(UUID contractId) {
        this.contractId = contractId;
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

    public float getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(float specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getAgreementConditions() {
        return agreementConditions;
    }

    public void setAgreementConditions(String agreementConditions) {
        this.agreementConditions = agreementConditions;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public CONTRACTSTATUS getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(CONTRACTSTATUS contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Partner getIdPartner() {
        return Partner;
    }

    public void setIdPartner(Partner idPartner) {
        this.Partner = Partner;
    }

    public List<Ticket> getTicketList() {
        return this.ticketList;
    }

    public void setTicketList(Ticket ticket) {
        this.ticketList.add(ticket);
    }
}

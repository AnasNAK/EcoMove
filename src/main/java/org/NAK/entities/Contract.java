package org.NAK.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import org.NAK.enums.CONTRACTSTATUS;

public class Contract {
    private UUID contractId;
    private LocalDate startDate; // Update to LocalDate
    private LocalDate endDate;
    private float specialPrice;
    private String agreementConditions;
    private boolean renewable;
    private CONTRACTSTATUS contractStatus;
    private Partner partner;

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

    public LocalDate  getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate  startDate) {
        this.startDate = startDate;
    }

    public LocalDate  getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate  endDate) {
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

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public List<Ticket> getTicketList() {
        return this.ticketList;
    }

    public void setTicketList(Ticket ticket) {
        this.ticketList.add(ticket);
    }
}

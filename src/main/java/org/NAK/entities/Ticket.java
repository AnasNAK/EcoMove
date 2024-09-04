package org.NAK.entities;

import java.util.UUID;
import java.util.Date;
import org.NAK.enums.TRANSPORTTYPE;
import org.NAK.enums.TICKETSTATUS;

public class Ticket {

    private UUID ticketId;
    private TRANSPORTTYPE transportType;
    private float purchasePrice;
    private float resalePrice;
    private Date resaleDate;
    private TICKETSTATUS ticketStatus;
    private Contract contract;

    public Ticket() {
        this.ticketId = UUID.randomUUID();
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public TRANSPORTTYPE getTransportType() {
        return transportType;
    }

    public void setTransportType(TRANSPORTTYPE transportType) {
        this.transportType = transportType;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public float getResalePrice() {
        return resalePrice;
    }

    public void setResalePrice(float resalePrice) {
        this.resalePrice = resalePrice;
    }

    public Date getResaleDate() {
        return resaleDate;
    }

    public void setResaleDate(Date resaleDate) {
        this.resaleDate = resaleDate;
    }

    public TICKETSTATUS getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TICKETSTATUS ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
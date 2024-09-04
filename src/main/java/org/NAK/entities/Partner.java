package org.NAK.entities;

import org.NAK.enums.TRANSPORTTYPE;
import org.NAK.enums.PARTNERSHIPSTATUS;

import java.util.List;
import java.util.UUID;

public class Partner {


private UUID partnerId;
private String companyName;
private String comercialContact;
private TRANSPORTTYPE transportType;
private String geographicArea;
private String specialCondition;
private PARTNERSHIPSTATUS partnershipStatus;
private List<Contract> contractList;


public Partner (){
    this.partnerId = UUID.randomUUID();
}

    public UUID getPartnerId() {
         return partnerId;
    }

    public void setPartnerId(UUID partnerId){
    this.partnerId = partnerId;
    }

    public String getCompanyName(){
    return companyName;
    }

    public void setCompanyName(String companyName){
    this.companyName = companyName;
    }
    public String getComercialContact() {
        return comercialContact;
    }

    public void setComercialContact(String comercialContact) {
        this.comercialContact = comercialContact;
    }

    public TRANSPORTTYPE getTransportType() {
        return transportType;
    }

    public void setTransportType(TRANSPORTTYPE transportType) {
        this.transportType = transportType;
    }

    public String getGeographicArea() {
        return geographicArea;
    }

    public void setGeographicArea(String geographicArea) {
        this.geographicArea = geographicArea;
    }

    public String getSpecialCondition() {
        return specialCondition;
    }

    public void setSpecialCondition(String specialCondition) {
        this.specialCondition = specialCondition;
    }

    public PARTNERSHIPSTATUS getPartnershipStatus() {
        return partnershipStatus;
    }

    public void setPartnershipStatus(PARTNERSHIPSTATUS partnershipStatus) {
        this.partnershipStatus = partnershipStatus;
    }
    public List<Contract> getContractList() {
        return this.contractList;
    }

    public void setContractList(Contract contract) {
      this.contractList.add(contract);
    }


}

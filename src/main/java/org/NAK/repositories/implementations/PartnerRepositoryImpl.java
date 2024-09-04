package org.NAK.repositories.implementations;

import org.NAK.db.Database;
import org.NAK.entities.Contract;
import org.NAK.entities.Partner;
import org.NAK.enums.CONTRACTSTATUS;
import org.NAK.enums.PARTNERSHIPSTATUS;
import org.NAK.enums.TRANSPORTTYPE;
import org.NAK.repositories.interfaces.PartnerRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartnerRepositoryImpl implements PartnerRepositoryInterface {

    private Connection connection;

    public PartnerRepositoryImpl() {
        this.connection = Database.getInstance().establishConnection();
    }

    @Override
    public void addPartner(Partner partner) {
        String sql = "INSERT INTO Partner (partner_id, company_name, comercial_contact, transport_type, geographic_area, special_conditions, partnership_status) VALUES (?, ?, ?, ?::transport_type, ?, ?, ?::partnership_status)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, partner.getPartnerId());
            stmt.setString(2, partner.getCompanyName());
            stmt.setString(3, partner.getComercialContact());
            stmt.setString(4, partner.getTransportType().name());
            stmt.setString(5, partner.getGeographicArea());
            stmt.setString(6, partner.getSpecialCondition());
            stmt.setString(7, partner.getPartnershipStatus().name());

            stmt.executeUpdate();
            System.out.println("Partner added: " + partner);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePartner(Partner partner) {
        String sql = "UPDATE Partner SET company_name = ?, comercial_contact = ?, transport_type = ?::transport_type, geographic_area = ?, special_conditions = ?, partnership_status = ?::partnership_status WHERE partner_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, partner.getCompanyName());
            stmt.setString(2, partner.getComercialContact());
            stmt.setString(3, partner.getTransportType().name());
            stmt.setString(4, partner.getGeographicArea());
            stmt.setString(5, partner.getSpecialCondition());
            stmt.setString(6, partner.getPartnershipStatus().name());
            stmt.setObject(7, partner.getPartnerId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Partner updated: " + partner);
            } else {
                System.out.println("No partner found with ID: " + partner.getPartnerId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePartner(UUID partnerId) {
        String sql = "UPDATE Partner SET partnership_status = 'SUSPENDED' WHERE partner_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, partnerId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Partner deleted with ID: " + partnerId);
            } else {
                System.out.println("No partner found with ID: " + partnerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public List<Partner> getAllPartners() {
        String sql = "SELECT p.partner_id, p.company_name, p.comercial_contact, p.transport_type, p.geographic_area, p.special_conditions, p.partnership_status, " +
                "c.contract_id, c.start_date, c.end_date, c.special_price, c.agreement_conditions, c.renewable, c.contract_status " +
                "FROM Partner p " +
                "LEFT JOIN Contract c ON p.partner_id = c.id_partner";

        List<Partner> partners = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            Partner currentPartner = null;
            UUID lastPartnerId = null;

            while (rs.next()) {
                UUID partnerId = (UUID) rs.getObject("partner_id");

                // If we encounter a new partner, create a new Partner object
                if (lastPartnerId == null || !partnerId.equals(lastPartnerId)) {
                    if (currentPartner != null) {
                        partners.add(currentPartner);
                    }

                    currentPartner = new Partner();
                    currentPartner.setPartnerId(partnerId);
                    currentPartner.setCompanyName(rs.getString("company_name"));
                    currentPartner.setComercialContact(rs.getString("comercial_contact"));
                    currentPartner.setTransportType(TRANSPORTTYPE.valueOf(rs.getString("transport_type")));
                    currentPartner.setGeographicArea(rs.getString("geographic_area"));
                    currentPartner.setSpecialCondition(rs.getString("special_conditions"));
                    currentPartner.setPartnershipStatus(PARTNERSHIPSTATUS.valueOf(rs.getString("partnership_status")));

                    lastPartnerId = partnerId;
                }

                // Add contract information if available
                UUID contractId = (UUID) rs.getObject("contract_id");
                if (contractId != null) {
                    Contract contract = new Contract();
                    contract.setContractId(contractId);
                    contract.setStartDate(rs.getDate("start_date").toLocalDate());
                    contract.setEndDate(rs.getDate("end_date").toLocalDate());
                    contract.setSpecialPrice(rs.getFloat("special_price"));
                    contract.setAgreementConditions(rs.getString("agreement_conditions"));
                    contract.setRenewable(rs.getBoolean("renewable"));
                    contract.setContractStatus(CONTRACTSTATUS.valueOf(rs.getString("contract_status")));

                    currentPartner.addContract(contract);
                }
            }

            // Add the last partner to the list
            if (currentPartner != null) {
                partners.add(currentPartner);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partners;
    }


    public Partner getPartner(UUID partnerId) {
        return null;
    }
}

package org.NAK.repositories.implementations;

import org.NAK.db.Database;
import org.NAK.entities.Contract;
import org.NAK.entities.Partner;
import org.NAK.enums.CONTRACTSTATUS;
import org.NAK.enums.PARTNERSHIPSTATUS;
import org.NAK.enums.TRANSPORTTYPE;
import org.NAK.repositories.interfaces.ContractRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContractRepositoryImpl implements ContractRepositoryInterface {
    private static final String tableName = "Contract";
    private static final String partnerTable = "Partner";
    private Connection conn;

    public ContractRepositoryImpl() {
        this.conn = Database.getInstance().establishConnection();
    }

    @Override
    public void addContract(Contract contract) {
        String sql = "INSERT INTO " + tableName + " (contract_id, start_date, end_date, special_price, agreement_conditions, renewable, contract_status, id_partner) VALUES (?, ?, ?, ?, ?, ?, ?::contract_status, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, contract.getContractId());
            stmt.setDate(2, Date.valueOf(contract.getStartDate()));
            stmt.setDate(3, Date.valueOf(contract.getEndDate()));
            stmt.setFloat(4, contract.getSpecialPrice());
            stmt.setString(5, contract.getAgreementConditions());
            stmt.setBoolean(6, contract.isRenewable());
            stmt.setString(7, contract.getContractStatus().name());
            stmt.setObject(8, contract.getPartner().getPartnerId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Contract getContractById(UUID contractId) {
        String sql = "SELECT c.*, p.* FROM " + tableName + " c JOIN " + partnerTable + " p ON c.id_partner = p.partner_id WHERE c.contract_id = ?";
        Contract contract = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, contractId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    contract = new Contract();
                    contract.setContractId((UUID) rs.getObject("contract_id"));
                    contract.setStartDate(rs.getDate("start_date").toLocalDate());
                    contract.setEndDate(rs.getDate("end_date").toLocalDate());
                    contract.setSpecialPrice(rs.getFloat("special_price"));
                    contract.setAgreementConditions(rs.getString("agreement_conditions"));
                    contract.setRenewable(rs.getBoolean("renewable"));

                    String contractStatusStr = rs.getString("contract_status");
                    if (contractStatusStr != null) {
                        contract.setContractStatus(CONTRACTSTATUS.valueOf(contractStatusStr));
                    }

                    Partner partner = new Partner();
                    partner.setPartnerId((UUID) rs.getObject("partner_id"));
                    partner.setCompanyName(rs.getString("company_name"));
                    partner.setComercialContact(rs.getString("comercial_contact"));
                    partner.setTransportType(TRANSPORTTYPE.valueOf(rs.getString("transport_type")));
                    partner.setGeographicArea(rs.getString("geographic_area"));
                    partner.setSpecialCondition(rs.getString("special_conditions"));
                    partner.setPartnershipStatus(PARTNERSHIPSTATUS.valueOf(rs.getString("partnership_status")));

                    contract.setPartner(partner);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contract;
    }

    @Override
    public void updateContract(Contract contract) {
        String sql = "UPDATE " + tableName + " SET start_date = ?, end_date = ?, special_price = ?, agreement_conditions = ?, renewable = ?, contract_status = ?::contract_status, id_partner = ? WHERE contract_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(2, Date.valueOf(contract.getStartDate()));
            stmt.setDate(3, Date.valueOf(contract.getEndDate()));
            stmt.setFloat(3, contract.getSpecialPrice());
            stmt.setString(4, contract.getAgreementConditions());
            stmt.setBoolean(5, contract.isRenewable());
            stmt.setString(6, contract.getContractStatus().name());
            stmt.setObject(7, contract.getPartner().getPartnerId());
            stmt.setObject(8, contract.getContractId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContract(UUID contractId) {
        String sql = "UPDATE " + tableName + " SET contract_status = 'SUSPENDED' WHERE contract_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, contractId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contract> getAllContracts() {
        String sql = "SELECT c.*, p.* FROM " + tableName + " c JOIN " + partnerTable + " p ON c.id_partner = p.partner_id";
        List<Contract> contracts = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Contract contract = new Contract();
                contract.setContractId((UUID) rs.getObject("contract_id"));
                contract.setStartDate(rs.getDate("start_date").toLocalDate());
                contract.setEndDate(rs.getDate("end_date").toLocalDate());
                contract.setSpecialPrice(rs.getFloat("special_price"));
                contract.setAgreementConditions(rs.getString("agreement_conditions"));
                contract.setRenewable(rs.getBoolean("renewable"));

                String contractStatusStr = rs.getString("contract_status");
                if (contractStatusStr != null) {
                    contract.setContractStatus(CONTRACTSTATUS.valueOf(contractStatusStr));
                }

                Partner partner = new Partner();
                partner.setPartnerId((UUID) rs.getObject("partner_id"));
                partner.setCompanyName(rs.getString("company_name"));
                partner.setComercialContact(rs.getString("comercial_contact"));
                partner.setTransportType(TRANSPORTTYPE.valueOf(rs.getString("transport_type")));
                partner.setGeographicArea(rs.getString("geographic_area"));
                partner.setSpecialCondition(rs.getString("special_conditions"));
                partner.setPartnershipStatus(PARTNERSHIPSTATUS.valueOf(rs.getString("partnership_status")));

                contract.setPartner(partner);

                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }

    @Override
    public List<Contract> getContractsByPartnerId(UUID partnerId) {
        String sql = "SELECT c.*, p.* FROM " + tableName + " c JOIN " + partnerTable + " p ON c.id_partner = p.partner_id WHERE c.id_partner = ?";
        List<Contract> contracts = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, partnerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contract contract = new Contract();
                    contract.setContractId((UUID) rs.getObject("contract_id"));
                    contract.setStartDate(rs.getDate("start_date").toLocalDate());
                    contract.setEndDate(rs.getDate("end_date").toLocalDate());
                    contract.setSpecialPrice(rs.getFloat("special_price"));
                    contract.setAgreementConditions(rs.getString("agreement_conditions"));
                    contract.setRenewable(rs.getBoolean("renewable"));

                    String contractStatusStr = rs.getString("contract_status");
                    if (contractStatusStr != null) {
                        contract.setContractStatus(CONTRACTSTATUS.valueOf(contractStatusStr));
                    }

                    Partner partner = new Partner();
                    partner.setPartnerId((UUID) rs.getObject("partner_id"));
                    partner.setCompanyName(rs.getString("company_name"));
                    partner.setComercialContact(rs.getString("comercial_contact"));
                    partner.setTransportType(TRANSPORTTYPE.valueOf(rs.getString("transport_type")));
                    partner.setGeographicArea(rs.getString("geographic_area"));
                    partner.setSpecialCondition(rs.getString("special_conditions"));
                    partner.setPartnershipStatus(PARTNERSHIPSTATUS.valueOf(rs.getString("partnership_status"))); // Assuming this is correct mapping

                    contract.setPartner(partner);

                    contracts.add(contract);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }
}

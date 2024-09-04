package org.NAK.repositories.interfaces;

import org.NAK.entities.Contract;
import java.util.List;
import java.util.UUID;

public interface ContractRepositoryInterface {

    void addContract(Contract contract);

    Contract getContractById(UUID contractId);

    void updateContract(Contract contract);

    void deleteContract(UUID contractId);

    List<Contract> getAllContracts();

    List<Contract> getContractsByPartnerId(UUID partnerId);
}

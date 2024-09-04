package org.NAK.repositories.interfaces;

import org.NAK.entities.Partner;

import java.util.List;
import java.util.UUID;

public interface PartnerRepositoryInterface {

    void addPartner(Partner partner);
    void updatePartner(Partner partner);
    void deletePartner(UUID partnerId);
    List<Partner> getAllPartners();

}

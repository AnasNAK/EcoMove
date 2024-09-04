package org.NAK;

import org.NAK.entities.Partner;
import org.NAK.enums.CONTRACTSTATUS;
import org.NAK.enums.TRANSPORTTYPE;
import org.NAK.enums.PARTNERSHIPSTATUS;

//import org.NAK.repositories.interfaces.PartnerRepositoryInterface;
import org.NAK.repositories.implementations.PartnerRepositoryImpl;
import org.NAK.entities.Contract;
import org.NAK.repositories.implementations.ContractRepositoryImpl;
//import org.NAK.repositories.interfaces.ContractRepositoryInterface;

import java.time.format.DateTimeParseException;

import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
//import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            System.out.println();
            System.out.println(" ╔═════════════════════════════════╗");
            System.out.println(" ║           EcoMove Menu          ║");
            System.out.println(" ╠═════════════════════════════════╣");
            System.out.println(" ║  1 : Manage Partners            ║");
            System.out.println(" ║  2 : Manage Contracts           ║");
            System.out.println(" ║  3 : Manage Offers              ║");
            System.out.println(" ║  4 : Manage Tickets             ║");
            System.out.println(" ║  5 : Quit                       ║");
            System.out.println(" ╚═════════════════════════════════╝");
            System.out.println();
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    managePartners(scanner);
                    break;
                case 2:
                    manageContracts();
                    break;
                case 3:
                    manageOffers();
                    break;
                case 4:
                    manageTickets();
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }



    private static void manageContracts() {
        Scanner scanner = new Scanner(System.in);
        ContractRepositoryImpl contractRepository = new ContractRepositoryImpl();

        System.out.println();
        System.out.println(" ╔═════════════════════════════════╗");
        System.out.println(" ║       Manage Contracts          ║");
        System.out.println(" ╠═════════════════════════════════╣");
        System.out.println(" ║  1 : Add Contract               ║");
        System.out.println(" ║  2 : Update Contract            ║");
        System.out.println(" ║  3 : Delete Contract            ║");
        System.out.println(" ║  4 : View Contracts             ║");
        System.out.println(" ║  5 : View Contracts by Partner  ║");
        System.out.println(" ║  6 : Back to Main Menu          ║");
        System.out.println(" ╚═════════════════════════════════╝");
        System.out.println();
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addContract(scanner, contractRepository);
                break;
            case 2:
                updateContract(scanner, contractRepository);
                break;
            case 3:
                deleteContract(scanner, contractRepository);
                break;
            case 4:
                viewContracts(contractRepository);
                break;
            case 5:
                viewContractsByPartner(scanner, contractRepository);
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addContract(Scanner scanner, ContractRepositoryImpl contractRepository) {
        System.out.println("Enter Start Date (yyyy-mm-dd):");
        LocalDate startDateLocal = LocalDate.parse(scanner.nextLine());

        if (startDateLocal.isBefore(LocalDate.now())) {
            System.out.println("Start date cannot be before the current date.");
            return;
        }

        System.out.println("Enter End Date (yyyy-mm-dd):");
        LocalDate endDateLocal = LocalDate.parse(scanner.nextLine());

        if (startDateLocal.isAfter(endDateLocal)) {
            System.out.println("Start date cannot be after end date.");
            return;
        }

        System.out.println("Enter Special Price:");
        float specialPrice = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Enter Agreement Conditions:");
        String agreementConditions = scanner.nextLine();

        System.out.println("Is Renewable (true/false):");
        boolean renewable = scanner.nextBoolean();
        scanner.nextLine();

        System.out.println("Enter Contract Status (ACTIVE, INPROGRESS, COMPLETED, SUSPENDED):");
        CONTRACTSTATUS contractStatus = CONTRACTSTATUS.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Enter Partner ID:");
        UUID partnerId = UUID.fromString(scanner.nextLine());

        Partner partner = new Partner();
        partner.setPartnerId(partnerId);

        Contract contract = new Contract();
        contract.setStartDate(startDateLocal);
        contract.setEndDate(endDateLocal);
        contract.setSpecialPrice(specialPrice);
        contract.setAgreementConditions(agreementConditions);
        contract.setRenewable(renewable);
        contract.setContractStatus(contractStatus);
        contract.setPartner(partner);

        contractRepository.addContract(contract);
        System.out.println("Contract added successfully with ID: " + contract.getContractId());
    }




    private static void updateContract(Scanner scanner, ContractRepositoryImpl contractRepository) {
        System.out.println("Enter Contract ID to update:");
        UUID contractId = UUID.fromString(scanner.nextLine());

        Contract contract = contractRepository.getContractById(contractId);
        if (contract == null) {
            System.out.println("Contract not found.");
            return;
        }

        System.out.println("Enter new Start Date (yyyy-mm-dd) or press Enter to keep current:");
        String startDateStr = scanner.nextLine();
        if (!startDateStr.isEmpty()) {
            try {
                LocalDate startDate = LocalDate.parse(startDateStr);
                if (startDate.isAfter(contract.getEndDate())) {
                    System.out.println("Start date cannot be after end date.");
                    return;
                }
                contract.setStartDate(startDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
                return;
            }
        }

        System.out.println("Enter new End Date (yyyy-mm-dd) or press Enter to keep current:");
        String endDateStr = scanner.nextLine();
        if (!endDateStr.isEmpty()) {
            try {
                LocalDate endDate = LocalDate.parse(endDateStr);
                if (endDate.isBefore(contract.getStartDate())) {
                    System.out.println("End date cannot be before start date.");
                    return;
                }
                contract.setEndDate(endDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
                return;
            }
        }

        System.out.println("Enter new Special Price or press Enter to keep current:");
        String specialPriceStr = scanner.nextLine();
        if (!specialPriceStr.isEmpty()) {
            try {
                contract.setSpecialPrice(Float.parseFloat(specialPriceStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid special price format.");
                return;
            }
        }

        System.out.println("Enter new Agreement Conditions or press Enter to keep current:");
        String agreementConditions = scanner.nextLine();
        if (!agreementConditions.isEmpty()) {
            contract.setAgreementConditions(agreementConditions);
        }

        System.out.println("Is Renewable (true/false) or press Enter to keep current:");
        String renewableStr = scanner.nextLine();
        if (!renewableStr.isEmpty()) {
            contract.setRenewable(Boolean.parseBoolean(renewableStr));
        }

        System.out.println("Enter new Contract Status (ACTIVE, INPROGRESS, COMPLETED, SUSPENDED) or press Enter to keep current:");
        String contractStatusStr = scanner.nextLine();
        if (!contractStatusStr.isEmpty()) {
            try {
                CONTRACTSTATUS contractStatus = CONTRACTSTATUS.valueOf(contractStatusStr.toUpperCase());
                contract.setContractStatus(contractStatus);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid contract status. Keeping the current value.");
            }
        }

        contractRepository.updateContract(contract);
        System.out.println("Contract updated successfully.");
    }

    private static void deleteContract(Scanner scanner, ContractRepositoryImpl contractRepository) {
        System.out.println("Enter Contract ID to delete:");
        UUID contractId = UUID.fromString(scanner.nextLine());


        contractRepository.deleteContract(contractId);
        System.out.println("Contract deleted successfully.");
    }

    private static void viewContracts(ContractRepositoryImpl contractRepository) {
        List<Contract> contracts = contractRepository.getAllContracts();
        if (contracts.isEmpty()) {
            System.out.println("No contracts found.");
        } else {
            System.out.println("Contracts List:");
            for (Contract contract : contracts) {
                System.out.println("Contract ID: " + contract.getContractId());
                System.out.println("Start Date: " + contract.getStartDate());
                System.out.println("End Date: " + contract.getEndDate());
                System.out.println("Special Price: " + contract.getSpecialPrice());
                System.out.println("Agreement Conditions: " + contract.getAgreementConditions());
                System.out.println("Renewable: " + contract.isRenewable());
                System.out.println("Status: " + contract.getContractStatus());
                System.out.println("Partner ID: " + contract.getPartner().getPartnerId());
                System.out.println();
            }
        }
    }

    private static void viewContractsByPartner(Scanner scanner, ContractRepositoryImpl contractRepository) {
        System.out.println("Enter Partner ID to view their contracts:");
        UUID partnerId = UUID.fromString(scanner.nextLine());

        List<Contract> contracts = contractRepository.getContractsByPartnerId(partnerId);
        if (contracts.isEmpty()) {
            System.out.println("No contracts found for the given partner.");
        } else {
            System.out.println("Contracts for Partner ID " + partnerId + ":");
            for (Contract contract : contracts) {
                System.out.println("Contract ID: " + contract.getContractId());
                System.out.println("Start Date: " + contract.getStartDate());
                System.out.println("End Date: " + contract.getEndDate());
                System.out.println("Special Price: " + contract.getSpecialPrice());
                System.out.println("Agreement Conditions: " + contract.getAgreementConditions());
                System.out.println("Renewable: " + contract.isRenewable());
                System.out.println("Status: " + contract.getContractStatus());
                System.out.println();
            }
        }
    }

    private static void manageOffers() {
        System.out.println("Manage Offers selected.");
    }

    private static void manageTickets() {
        System.out.println("Manage Tickets selected.");
    }

    private static void managePartners(Scanner scanner) {
        PartnerRepositoryImpl partnerRepository = new PartnerRepositoryImpl();

        int choice;

        while (true) {
            System.out.println();
            System.out.println(" ╔═════════════════════════════════╗");
            System.out.println(" ║       Manage Partners Menu      ║");
            System.out.println(" ╠═════════════════════════════════╣");
            System.out.println(" ║  1 : Add Partner                ║");
            System.out.println(" ║  2 : Modify Partner             ║");
            System.out.println(" ║  3 : Delete Partner             ║");
            System.out.println(" ║  4 : View Partners              ║");
            System.out.println(" ║  5 : Back to Main Menu          ║");
            System.out.println(" ╚═════════════════════════════════╝");
            System.out.println();
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addPartner(scanner, partnerRepository);
                        break;
                    case 2:
                        modifyPartner(scanner, partnerRepository);
                        break;
                    case 3:
                        deletePartner(scanner, partnerRepository);
                        break;
                    case 4:
                        viewPartners(partnerRepository);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.next();
            }
        }
    }

    private static void addPartner(Scanner scanner, PartnerRepositoryImpl partnerRepository) {
        System.out.println("Enter Company Name:");
        String companyName = scanner.next();

        System.out.println("Enter Commercial Contact:");
        String comercialContact = scanner.next();

        System.out.println("Enter Geographic Area:");
        String geographicArea = scanner.next();

        System.out.println("Enter Special Conditions:");
        String specialConditions = scanner.next();

        System.out.println("Enter Transport Type (AIRPLANE, TRAIN, BUS):");
        String transportTypeStr = scanner.next();
        TRANSPORTTYPE transportType = TRANSPORTTYPE.valueOf(transportTypeStr.toUpperCase());

        System.out.println("Enter Partnership Status (ACTIVE, INACTIVE, SUSPENDED):");
        String partnershipStatusStr = scanner.next();
        PARTNERSHIPSTATUS partnershipStatus = PARTNERSHIPSTATUS.valueOf(partnershipStatusStr.toUpperCase());

        Partner partner = new Partner();
        partner.setCompanyName(companyName);
        partner.setComercialContact(comercialContact);
        partner.setGeographicArea(geographicArea);
        partner.setSpecialCondition(specialConditions);
        partner.setTransportType(transportType);
        partner.setPartnershipStatus(partnershipStatus);

        partnerRepository.addPartner(partner);
        System.out.println("Partner added successfully.");
    }

    private static void modifyPartner(Scanner scanner, PartnerRepositoryImpl partnerRepository) {
        System.out.println("Enter Partner ID to modify:");
        String partnerIdStr = scanner.next();
        UUID partnerId = UUID.fromString(partnerIdStr);
        scanner.nextLine();

        Partner partner = partnerRepository.getPartner(partnerId);
        if (partner == null) {
            System.out.println("Partner not found.");
            return;
        }

        System.out.println("Enter new Company Name (or press Enter to keep current):");
        String companyName = scanner.nextLine();
        if (!companyName.isEmpty()) {
            partner.setCompanyName(companyName);
        }

        System.out.println("Enter new Commercial Contact (or press Enter to keep current):");
        String comercialContact = scanner.nextLine();
        if (!comercialContact.isEmpty()) {
            partner.setComercialContact(comercialContact);
        }

        System.out.println("Enter new Geographic Area (or press Enter to keep current):");
        String geographicArea = scanner.nextLine();
        if (!geographicArea.isEmpty()) {
            partner.setGeographicArea(geographicArea);
        }

        System.out.println("Enter new Special Conditions (or press Enter to keep current):");
        String specialConditions = scanner.nextLine();
        if (!specialConditions.isEmpty()) {
            partner.setSpecialCondition(specialConditions);
        }

        System.out.println("Enter new Transport Type (AIRPLANE, TRAIN, BUS) (or press Enter to keep current):");
        String transportTypeStr = scanner.nextLine().toUpperCase();
        if (!transportTypeStr.isEmpty()) {
            try {
                TRANSPORTTYPE transportType = TRANSPORTTYPE.valueOf(transportTypeStr);
                partner.setTransportType(transportType);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid transport type. Keeping the current value.");
            }
        }

        System.out.println("Enter new Partnership Status (ACTIVE, INACTIVE, SUSPENDED) (or press Enter to keep current):");
        String partnershipStatusStr = scanner.nextLine().toUpperCase();
        if (!partnershipStatusStr.isEmpty()) {
            try {
                PARTNERSHIPSTATUS partnershipStatus = PARTNERSHIPSTATUS.valueOf(partnershipStatusStr);
                partner.setPartnershipStatus(partnershipStatus);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid partnership status. Keeping the current value.");
            }
        }

        partnerRepository.updatePartner(partner);
        System.out.println("Partner updated successfully.");
    }

    private static void deletePartner(Scanner scanner, PartnerRepositoryImpl partnerRepository) {
        System.out.println("Enter Partner ID to delete:");
        String partnerIdStr = scanner.next();
        UUID partnerId = UUID.fromString(partnerIdStr);

        partnerRepository.deletePartner(partnerId);
        System.out.println("Partner deleted successfully.");
    }

    private static void viewPartners(PartnerRepositoryImpl partnerRepository) {
        try {
            List<Partner> partners = partnerRepository.getAllPartners();

            if (partners.isEmpty()) {
                System.out.println("No partners found.");
            } else {
                System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                                                                                 Partners List                                                                                               ║");
                System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                System.out.printf("║ %-36s │ %-25s │ %-15s │ %-20s │ %-25s │ %-30s │ %-15s ║%n",
                        "Partner ID", "Company Name", "Transport Type", "Geographic Area", "Special Conditions", "Commercial Contact", "Partnership Status");
                System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");

                for (Partner partner : partners) {
                    System.out.printf("║ %-36s │ %-25s │ %-15s │ %-20s │ %-25s │ %-30s │ %-15s ║%n",
                            partner.getPartnerId(),
                            partner.getCompanyName(),
                            partner.getTransportType(),
                            partner.getGeographicArea(),
                            partner.getSpecialCondition(),
                            partner.getComercialContact(),
                            partner.getPartnershipStatus());

                    List<Contract> contracts = partner.getContracts();

                    if (contracts != null && !contracts.isEmpty()) {
                        System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.printf("║ %-36s │ %-10s │ %-10s │ %-10s │ %-15s │ %-15s ║%n",
                                "Contract ID", "Start Date", "End Date", "Special Price", "Renewable", "Contract Status");

                        for (Contract contract : contracts) {
                            System.out.printf("║ %-36s │ %-10s │ %-10s │ %-10s │ %-15s │ %-15s ║%n",
                                    contract.getContractId(),
                                    contract.getStartDate(),
                                    contract.getEndDate(),
                                    contract.getSpecialPrice(),
                                    contract.isRenewable() ? "Yes" : "No",
                                    contract.getContractStatus());
                        }
                    } else {
                        System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ No contracts found for this partner.                                                                                                                                                           ║");
                    }

                    System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
                }

                System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing partners. Please check the error details.");
        }
    }




}

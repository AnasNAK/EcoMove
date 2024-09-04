
CREATE TYPE partnership_status AS ENUM ('ACTIVE', 'INACTIVE', 'SUSPENDED');
CREATE TYPE transport_type AS ENUM ('AIRPLANE', 'TRAIN', 'BUS');
CREATE TYPE ticket_status AS ENUM ('SOLD', 'CANCELLED', 'PENDING');
CREATE TYPE contract_status AS ENUM ('INPROGRESS', 'COMPLETED', 'SUSPENDED');
CREATE TYPE offer_status AS ENUM ('ACTIVE', 'EXPIRED', 'SUSPENDED');
CREATE TYPE discount_type AS ENUM ('PERCENTAGE', 'FIXEDAMOUNT');

CREATE TABLE Partner (
    partner_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_name VARCHAR(50),
    comercial_contact VARCHAR(500),
    transport_type transport_type,
    geographic_area VARCHAR(255),
    special_conditions VARCHAR(255),
    partnership_status partnership_status
);

CREATE TABLE Contract (
    contract_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    start_date DATE,
    end_date DATE,
    special_price FLOAT,
    agreement_conditions VARCHAR(500),
    renewable BOOLEAN,
    contract_status contract_status,
    id_partner UUID,
    FOREIGN KEY (id_partner) REFERENCES Partner(partner_id)
);

CREATE TABLE Ticket (
    ticket_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    transport_type transport_type,
    purchase_price FLOAT,
    resale_price FLOAT,
    resale_date DATE,
    ticket_status ticket_status,
    id_contract UUID,
    FOREIGN KEY (id_contract) REFERENCES Contract(contract_id)
);

CREATE TABLE Discount (
    discount_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    offer_name VARCHAR(50),
    description VARCHAR(500),
    start_date DATE,
    end_date DATE,
    discount_type discount_type,
    discount_value FLOAT,
    conditions VARCHAR(500),
    id_contract UUID,
    FOREIGN KEY (id_contract) REFERENCES Contract(contract_id)
);
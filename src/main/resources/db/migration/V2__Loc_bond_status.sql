CREATE TABLE loc_bond_status(
    id serial PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_loc_contract INTEGER  NOT NULL,
    date_status DATE  NOT NULL,
    total_main DECIMAL(17,4) NOT NULL,
    cancel_main DECIMAL(17,4),
    CONSTRAINT l1 FOREIGN KEY(id_loc_contract) REFERENCES loc_contract,
    CONSTRAINT l2 CHECK FORMATDATETIME(date_status, 'dd') = 1
);
CREATE UNIQUE INDEX ak_loc_bond_status ON
loc_bond_status(id_loc_contract,date_status);


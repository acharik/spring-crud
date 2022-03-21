
CREATE TABLE Loc_contract (
                              ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                              date_begin DATE NOT NULL,
                              date_end DATE,
                              num_contract VARCHAR(100) not null,
                              sum DECIMAL(17,4),
                              comment VARCHAR(255),
                              id_loc_bond_status INTEGER,
                              CONSTRAINT num_uniq UNIQUE (num_contract),
                              CONSTRAINT date_check CHECK (date_end >= date_begin),
                              CONSTRAINT date_begin_check CHECK (date_begin between date '1950-01-01' and date '2100-12-31'),
                              CONSTRAINT date_end_check CHECK (date_end between date '1950-01-01' and date '2100-12-31')

);
CREATE INDEX pk_ind on Loc_contract(ID);
CREATE TABLE roles(
                      Id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY ,
                      name varchar(100) UNIQUE NOT NULL,
                      status varchar(25) not null default 'ACTIVE',
                      updated TIMESTAMP DEFAULT CURRENT_DATE,
                      created TIMESTAMP DEFAULT CURRENT_DATE
)
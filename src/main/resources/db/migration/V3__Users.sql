CREATE TABLE users(
                      Id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                      username varchar(100) UNIQUE NOT NULL,
                      email varchar(255) UNIQUE NOT NULL,
                      first_name varchar(100) not null,
                      last_name varchar(100) not null,
                      password varchar(255) not null,
                      updated TIMESTAMP DEFAULT CURRENT_DATE,
                      created TIMESTAMP DEFAULT CURRENT_DATE,
                      status varchar(25) not null default 'ACTIVE'
)
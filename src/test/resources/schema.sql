CREATE TABLE id_proof
(
    id        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_number VARCHAR(255),
    photo     BYTEA
);
CREATE TABLE onboarding
(
    id                     INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    copy_of_id_id          BIGINT,
    first_name             VARCHAR(255),
    last_name              VARCHAR(255),
    gender                 VARCHAR(50),
    birth                  DATE,
    phone                  VARCHAR(20),
    mobile_number          VARCHAR(20),
    mail_address           VARCHAR(255),
    nationality            VARCHAR(100),
    residential_address    VARCHAR(500),
    social_security_number VARCHAR(20),
    customer_id            VARCHAR(255) UNIQUE,
    password                varchar(10),


    CONSTRAINT fk_copy_of_id
        FOREIGN KEY (copy_of_id_id)
            REFERENCES id_proof (id)
            ON DELETE CASCADE
);


CREATE INDEX idx_onboarding_mail_address ON onboarding (mail_address);
CREATE INDEX idx_onboarding_customer_id ON onboarding (customer_id);
CREATE INDEX idx_onboarding_copy_of_id ON onboarding (copy_of_id_id);
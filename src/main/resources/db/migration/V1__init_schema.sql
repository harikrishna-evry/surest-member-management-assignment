CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";


CREATE TABLE IF NOT EXISTS role (
                      id UUID PRIMARY KEY,
                      name VARCHAR(50) UNIQUE NOT NULL,
                      version BIGINT DEFAULT 0 NOT NULL
);

CREATE TABLE IF NOT EXISTS app_user (
                          id UUID PRIMARY KEY,
                          username VARCHAR(50) NOT NULL UNIQUE,
                          password_hash VARCHAR(255) NOT NULL,
                          role_id UUID NOT NULL,
                          version BIGINT DEFAULT 0 NOT NULL
                          ,CONSTRAINT fk_role
                          FOREIGN KEY (role_id)
                          REFERENCES role(id)
);

CREATE TABLE IF NOT EXISTS member
(
    id UUID PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    version BIGINT DEFAULT 0 NOT NULL
    );
-- Create schema
CREATE SCHEMA IF NOT EXISTS jsonplaceholder;

-- Set search path
SET search_path TO jsonplaceholder;

DROP TABLE IF EXISTS jsonplaceholder.users CASCADE;
DROP TABLE IF EXISTS jsonplaceholder.addresses CASCADE;
DROP TABLE IF EXISTS jsonplaceholder.companies CASCADE;

CREATE TABLE jsonplaceholder.addresses (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    street VARCHAR(255),
    suite VARCHAR(255),
    city VARCHAR(255),
    zipcode VARCHAR(255),
    lat VARCHAR(255),
    lng VARCHAR(255)
);

CREATE TABLE jsonplaceholder.companies (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    company_name VARCHAR(255),
    company_catch_phrase VARCHAR(255),
    company_bs VARCHAR(255)
);

CREATE TABLE jsonplaceholder.users (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    website VARCHAR(255),
    address_id BIGINT,
    company_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES jsonplaceholder.addresses(id),
    FOREIGN KEY (company_id) REFERENCES jsonplaceholder.companies(id)
);

INSERT INTO addresses (street, suite, city, zipcode, lat, lng) VALUES
('Kulas Light', 'Apt. 556', 'Gwenborough', '92998-3874', '-37.3159', '81.1496'),
('Victor Plains', 'Suite 879', 'Wisokyburgh', '90566-7771', '-43.9509', '-34.4618'),
('Douglas Extension', 'Suite 847', 'McKenziehaven', '59590-4157', '-68.6102', '-47.0653');

INSERT INTO companies (company_name, company_catch_phrase, company_bs) VALUES
('Romaguera-Crona', 'Multi-layered client-server neural-net', 'harness real-time e-markets'),
('Deckow-Crist', 'Proactive didactic contingency', 'synergize scalable supply-chains'),
('Romaguera-Jacobson', 'Face to face neural-net', 'e-enable strategic applications');

INSERT INTO users (name, username, email, password, phone, website, address_id, company_id) VALUES
('Leanne Graham', 'Bret', 'Sincere@april.biz', '$2a$10$N/L8o2Vb/n6u6f5r8q3.k.l.l.l.l.l.l.l.l.l.l.l.', '1-770-736-8031 x56442', 'hildegard.org', 1, 1),
('Ervin Howell', 'Antonette', 'Shanna@melissa.tv', '$2a$10$N/L8o2Vb/n6u6f5r8q3.k.l.l.l.l.l.l.l.l.l.l.l.', '010-692-6593 x09125', 'anastasia.net', 2, 2),
('Clementine Bauch', 'Samantha', 'Nathan@yesenia.net', '$2a$10$N/L8o2Vb/n6u6f5r8q3.k.l.l.l.l.l.l.l.l.l.l.l.', '1-463-123-4447', 'ramiro.info', 3, 3);

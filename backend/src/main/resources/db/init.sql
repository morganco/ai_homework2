-- Create schema
CREATE SCHEMA IF NOT EXISTS jsonplaceholder;

-- Set search path
SET search_path TO jsonplaceholder;

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS companies CASCADE;

CREATE TABLE addresses (
    id BIGINT PRIMARY KEY,
    street VARCHAR(255),
    suite VARCHAR(255),
    city VARCHAR(255),
    zipcode VARCHAR(255),
    lat VARCHAR(255),
    lng VARCHAR(255)
);

CREATE TABLE companies (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    catch_phrase VARCHAR(255),
    bs VARCHAR(255)
);

CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    website VARCHAR(255),
    address_id BIGINT,
    company_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES addresses(id),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

INSERT INTO addresses (id, street, suite, city, zipcode, lat, lng) VALUES
(1, 'Kulas Light', 'Apt. 556', 'Gwenborough', '92998-3874', '-37.3159', '81.1496'),
(2, 'Victor Plains', 'Suite 879', 'Wisokyburgh', '90566-7771', '-43.9509', '-34.4618'),
(3, 'Douglas Extension', 'Suite 847', 'McKenziehaven', '59590-4157', '-68.6102', '-47.0653');

INSERT INTO companies (id, name, catch_phrase, bs) VALUES
(1, 'Romaguera-Crona', 'Multi-layered client-server neural-net', 'harness real-time e-markets'),
(2, 'Deckow-Crist', 'Proactive didactic contingency', 'synergize scalable supply-chains'),
(3, 'Romaguera-Jacobson', 'Face to face neural-net', 'e-enable strategic applications');

INSERT INTO users (id, name, username, email, password, phone, website, address_id, company_id) VALUES
(1, 'Leanne Graham', 'Bret', 'Sincere@april.biz', '$2a$10$N/L8o2Vb/n6u6f5r8q3.k.l.l.l.l.l.l.l.l.l.l.l.', '1-770-736-8031 x56442', 'hildegard.org', 1, 1),
(2, 'Ervin Howell', 'Antonette', 'Shanna@melissa.tv', '$2a$10$N/L8o2Vb/n6u6f5r8q3.k.l.l.l.l.l.l.l.l.l.l.l.', '010-692-6593 x09125', 'anastasia.net', 2, 2),
(3, 'Clementine Bauch', 'Samantha', 'Nathan@yesenia.net', '$2a$10$N/L8o2Vb/n6u6f5r8q3.k.l.l.l.l.l.l.l.l.l.l.l.', '1-463-123-4447', 'ramiro.info', 3, 3);

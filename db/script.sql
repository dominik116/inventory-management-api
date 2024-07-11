-- Create table employees
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    surname VARCHAR NOT NULL,
    nif VARCHAR(9) NOT NULL,
    email VARCHAR NOT NULL,
    enabled BOOLEAN DEFAULT FALSE,
    password VARCHAR NOT NULL
);

-- Create table article
CREATE TABLE article (
    id SERIAL PRIMARY KEY,
    EAN VARCHAR NOT NULL,
    Name VARCHAR NOT NULL,
    Description VARCHAR,
    Quantity INT,
    Price DECIMAL
);

-- Create table notifications
CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    message JSONB,
    status VARCHAR
);
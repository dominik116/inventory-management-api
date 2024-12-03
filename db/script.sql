-- Create table employees
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    surname VARCHAR NOT NULL,
    nif VARCHAR(9) NOT NULL,
    email VARCHAR NOT NULL,
    enabled BOOLEAN DEFAULT FALSE,
    password VARCHAR NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create table articles
CREATE TABLE articles (
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
    message VARCHAR NOT NULL,
    status VARCHAR,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
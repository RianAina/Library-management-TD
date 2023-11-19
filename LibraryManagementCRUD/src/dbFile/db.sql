DROP DATABASE IF EXISTS library_management;

CREATE DATABASE library_management;

\c library_management

CREATE TABLE authors (
    id VARCHAR(255) PRIMARY KEY,
    authorName VARCHAR(255),
    sex CHAR(1)
);


CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    author_name VARCHAR(255),
    book_name VARCHAR(255) NOT NULL,
    page_numbers INTEGER,
    topic VARCHAR(255),
    release_date DATE,
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE visitors (
    id SERIAL PRIMARY KEY,
    visitor_name VARCHAR(255) NOT NULL,
    reference VARCHAR(255) DEFAULT 'vst12345' UNIQUE NOT NULL
);

CREATE TABLE loan_history (
    loan_id SERIAL PRIMARY KEY,
    book_id INTEGER,
    visitor_id INTEGER,
    visitor_name VARCHAR(255),
    visitor_reference VARCHAR(255) DEFAULT 'vst12345' UNIQUE NOT NULL,
    borrowed_date DATE,
    returned_date DATE,
    is_available BOOLEAN,
    CONSTRAINT check_borrowed_status CHECK (
        (is_available = TRUE AND returned_date IS NOT NULL AND borrowed_date IS NULL)
        OR (is_available = FALSE AND returned_date IS NULL AND borrowed_date IS NOT NULL)
    )
);
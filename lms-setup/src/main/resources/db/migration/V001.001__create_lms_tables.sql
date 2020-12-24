CREATE TABLE lms.author
(
    id INT PRIMARY KEY IDENTITY (1, 1),
    first_name VARCHAR (50) NOT NULL,
    last_name VARCHAR (50) NOT NULL,
    version INT DEFAULT 0
);

CREATE TABLE lms.book_type
(
    id INT PRIMARY KEY IDENTITY (1, 1),
    type VARCHAR (50) NOT NULL,
    version INT DEFAULT 0
);

CREATE TABLE lms.book (
    id INT PRIMARY KEY IDENTITY (1, 1),
    name VARCHAR (50) NOT NULL,
    published_on DATETIME,
    number_of_copies INT NOT NULL,
    author_id INT NOT NULL,
    book_type_id INT NOT NULL,
    version INT DEFAULT 0,
    FOREIGN KEY (author_id) REFERENCES lms.author (id),
    FOREIGN KEY (book_type_id) REFERENCES lms.book_type (id)
);

CREATE TABLE lms.member_type
(
    id INT PRIMARY KEY IDENTITY (1, 1),
    type VARCHAR (50) NOT NULL,
    version INT DEFAULT 0
);

CREATE TABLE lms.member
(
    id INT PRIMARY KEY IDENTITY (1, 1),
    name VARCHAR (50) NOT NULL,
    member_type_id INT NOT NULL,
    version INT DEFAULT 0
    FOREIGN KEY (member_type_id) REFERENCES lms.member_type (id)
);

CREATE TABLE lms.issued_books
(
    id BIGINT PRIMARY KEY IDENTITY (1, 1),
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    issued_on DATETIME,
    returned_on DATETIME,
    version INT DEFAULT 0,
    FOREIGN KEY (book_id) REFERENCES lms.book (id),
    FOREIGN KEY (member_id) REFERENCES lms.member (id)
);


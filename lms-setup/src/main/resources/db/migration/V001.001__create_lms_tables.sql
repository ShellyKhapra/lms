CREATE TABLE dbo.author
(
    id INT PRIMARY KEY IDENTITY (1, 1),
    first_name VARCHAR (50) NOT NULL,
    last_name VARCHAR (50) NOT NULL,
    version INT DEFAULT 0
);

CREATE TABLE dbo.book_type
(
    id INT PRIMARY KEY IDENTITY (1, 1),
    type VARCHAR (50) NOT NULL,
    version INT DEFAULT 0
);

CREATE TABLE dbo.book (
    id INT PRIMARY KEY IDENTITY (1, 1),
    name VARCHAR (50) NOT NULL,
    published_on DATETIME,
    number_of_copies INT NOT NULL,
    author_id INT NOT NULL,
    book_type_id INT NOT NULL,
    version INT DEFAULT 0,
    FOREIGN KEY (author_id) REFERENCES dbo.author (id),
    FOREIGN KEY (book_type_id) REFERENCES dbo.book_type (id)
);

CREATE TABLE dbo.member_type
(
    id INT PRIMARY KEY IDENTITY (1, 1),
    type VARCHAR (50) NOT NULL,
    version INT DEFAULT 0
);

CREATE TABLE dbo.member
(
    id INT PRIMARY KEY IDENTITY (1, 1),
    name VARCHAR (50) NOT NULL,
    member_type_id INT NOT NULL,
    version INT DEFAULT 0
    FOREIGN KEY (member_type_id) REFERENCES dbo.member_type (id)
);

CREATE TABLE dbo.issued_books
(
    id BIGINT PRIMARY KEY IDENTITY (1, 1),
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    issued_on DATETIME,
    returned_on DATETIME,
    version INT DEFAULT 0,
    FOREIGN KEY (book_id) REFERENCES dbo.book (id),
    FOREIGN KEY (member_id) REFERENCES dbo.member (id)
);


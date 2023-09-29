USE tshirtbase;

CREATE TABLE tshirttable (
    OrderID INT,
    State CHAR(2),
    Zip MEDIUMINT,
    Address VARCHAR(50),
    ShirtColor VARCHAR(50),
    ShirtText VARCHAR(50),
    ShirtImage BOOL,
    Delivered BOOL,
    PRIMARY KEY(OrderID)
);

CREATE TABLE tshirtdeleted (
    OrderID INT,
    State CHAR(2),
    Zip MEDIUMINT,
    Address VARCHAR(50),
    ShirtColor VARCHAR(50),
    ShirtText VARCHAR(50),
    ShirtImage BOOL,
    Delivered BOOL,
    PRIMARY KEY(OrderID)
);

CREATE TABLE tshirtmarked (
    OrderID INT,
    State CHAR(2),
    Zip MEDIUMINT,
    Address VARCHAR(50),
    ShirtColor VARCHAR(50),
    ShirtText VARCHAR(50),
    ShirtImage BOOL,
    Delivered BOOL,
    PRIMARY KEY(OrderID)
);

SELECT * FROM tshirttable;
SELECT * FROM tshirtmarked;
SELECT * FROM tshirtdeleted;

INSERT INTO tshirttable (OrderID, State, Zip, Address, ShirtColor, ShirtText, ShirtImage, Delivered)
VALUES ('0', 'KS', '37593', '2757 Fake St.', 'Red', 'Burning Up', '0', '0');

UPDATE tshirttable SET Delivered = '1' WHERE OrderID = '0';
DELETE FROM tshirttable WHERE OrderID = '0';

INSERT INTO tshirtmarked SELECT * FROM tshirttable WHERE OrderID = '0';
INSERT INTO tshirtdeleted SELECT * FROM tshirttable WHERE OrderID = '0';


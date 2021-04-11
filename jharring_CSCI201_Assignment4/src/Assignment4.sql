DROP DATABASE IF EXISTS Assignment4;

CREATE DATABASE Assignment4;

USE Assignment4;


CREATE TABLE User(
UID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
Username VARCHAR(15) NOT NULL,
Password VARCHAR(15) NOT NULL,
Email VARCHAR(25) NOT NULL,
Balance DOUBLE NOT NULL,
AccountValue DOUBLE NOT NULL
);
CREATE TABLE Company(
CID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
Ticker VARCHAR(10) NOT NULL,
CompanyName VARCHAR(30),
ExchangeCode VARCHAR(10),
Price DOUBLE NOT NULL,
Change_ DOUBLE NOT NULL,
ChangePercentage DOUBLE NOT NULL
);

CREATE TABLE Stock(
SID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
UID INT NOT NULL,
    FOREIGN KEY fk1(UID) references User(UID),
CID INT NOT NULL,
	FOREIGN KEY fk2(CID) references Company(CID),
Quantity INT NOT NULL,
Cost DOUBLE NOT NULL,
Resell DOUBLE NOT NULL
);


CREATE TABLE Favorites(
FID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
UID INT NOT NULL,
    FOREIGN KEY fk1(UID) references User(UID),
CID INT NOT NULL,
	FOREIGN KEY fk2(CID) references Company(CID)
);

SELECT * FROM User;
SELECT * FROM Company;
SELECT * FROM Stock;
SELECT * FROM Favorites;

INSERT into User (Username, Password, Email, Balance, AccountValue)
VALUES ('test', '123', 'test@gmail.com', 50000.00, 50000.00);
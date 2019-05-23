CREATE table trains(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    trainnumber VARCHAR(50) NOT NULL,
    trainname varchar(50) NOT NULL,
    source VARCHAR(50) NOT NULL,
    dest VARCHAR(50) NOT NULL,
    sourcetime TIME NOT NULL,
    desttime TIME NOT NULL,
    seats int Not NULL,
    remseats int NOT NULL,  
    sun  CHAR(1),  
    mon  CHAR(1),
    tue  CHAR(1),
    wed  CHAR(1),
    thu  CHAR(1),
    fri  CHAR(1),
    sat  CHAR(1),
    route VARCHAR(100),
    date DATE 
);

CREATE TABLE users(
    int id PRIMARY KEY AUTO_INCREMENT;
    email varchar(20), 
    name VARCHAR(30),
    phno VARCHAR(15),
    gender VARCHAR(11),
    pass VARCHAR(20)
);

CREATE TABLE bookings(
    pnr INT PRIMARY KEY AUTO_INCREMENT,
    trainid VARCHAR(50) NOT NULL,
    trainname VARCHAR(50) NOT NULL,
    passengers VARCHAR(100) NOT NULL,
    source VARCHAR(50) NOT NULL,
    dest VARCHAR(50) NOT NULL,
    sourcetime TIME NOT NULL,
    desttime TIME NOT NULL,
    seatno VARCHAR(50) NOT NULL,
    mail VARCHAR(50) NOT NULL,
    payment VARCHAR(20) NOT NULL
);

SELECT * FROM users;

INSERT INTO USERS(email,name,phno,gender,pass) VALUES("1@1.com","Admin","9876543210","MALE","1");
INSERT INTO USERS(email,name,phno,gender,pass) VALUES("arun@123.com","Arun J","9876543210","MALE","12345");
INSERT INTO USERS(email,name,phno,gender,pass) VALUES("j@123.com","Dragon J" ,"8793671538","MALE","0000");

INSERT INTO TRAINS( trainnumber, trainname, source, dest, sourcetime, desttime, seats, remseats, sun, mon, tue, wed, thu, fri, sat, route, date)   Values("TN12345","FALCON","TRICHY","CHENNAI","08:10:00","18:10:00",100,100,'N','Y','Y','Y','Y','Y','N',"TRICHY,ARIYALUR,CHENGALPATTU,TAMBARAM,CHENNAI","2019-05-20");
INSERT INTO TRAINS( trainnumber, trainname, source, dest, sourcetime, desttime, seats, remseats, sun, mon, tue, wed, thu, fri, sat, route, date)   Values("TN90671","RHEGAR","TRICHY","CHENNAI","10:10:00","20:00:00",10,10,'Y','Y','Y','Y','Y','Y','N',"TRICHY,ARIYALUR,CHENGALPATTU,TAMBARAM,CHENNAI","2019-05-20");
INSERT INTO TRAINS( trainnumber, trainname, source, dest, sourcetime, desttime, seats, remseats, sun, mon, tue, wed, thu, fri, sat, route, date)   Values("TN70677","Valar","CHENNAI","TRICHY","12:10:00","20:00:00",200,200,'Y','Y','Y','Y','Y','Y','N',"CHENNAI,TAMBARAM,CHENGALPATTU,ARIYALUR,TRICHY","2019-05-20");
INSERT INTO TRAINS( trainnumber, trainname, source, dest, sourcetime, desttime, seats, remseats, sun, mon, tue, wed, thu, fri, sat, route, date)   Values("TN04601","TYRANT","CHENNAI","MADURAI","08:20:00","18:10:00",200,200,'Y','Y','Y','Y','Y','Y','Y',"CHENNAI,TRICHY,DINDIGUL,MADURAI","2019-05-20");
INSERT INTO TRAINS( trainnumber, trainname, source, dest, sourcetime, desttime, seats, remseats, sun, mon, tue, wed, thu, fri, sat, route, date)   Values("TN12945","BUCKY","TRICHY","CHENNAI","06:10:00","18:10:00",300,300,'N','Y','Y','Y','Y','Y','N',"TRICHY,ARIYALUR,CHENGALPATTU,TAMBARAM,CHENNAI","2019-05-20");
INSERT INTO TRAINS( trainnumber, trainname, source, dest, sourcetime, desttime, seats, remseats, sun, mon, tue, wed, thu, fri, sat, route, date)   Values("TN92371","DROGO","TRICHY","CHENNAI","08:10:00","20:30:00",400,400,'Y','Y','Y','Y','Y','Y','N',"TRICHY,ARIYALUR,CHENGALPATTU,TAMBARAM,CHENNAI","2019-05-20");
INSERT INTO TRAINS( trainnumber, trainname, source, dest, sourcetime, desttime, seats, remseats, sun, mon, tue, wed, thu, fri, sat, route, date)   Values("TN80677","GRANDO","CHENNAI","TRICHY","13:10:00","20:00:00",500,500,'Y','Y','Y','Y','Y','Y','N',"CHENNAI,TAMBARAM,CHENGALPATTU,ARIYALUR,TRICHY","2019-05-20");
INSERT INTO TRAINS( trainnumber, trainname, source, dest, sourcetime, desttime, seats, remseats, sun, mon, tue, wed, thu, fri, sat, route, date)   Values("TN00001","CIPHANO","CHENNAI","MADURAI","16:10:00","22:10:00",450,450,'Y','Y','Y','Y','Y','Y','Y',"CHENNAI,TAMBARAM,TRICHY,DINDIGUL,MADURAI","2019-05-20");

Insert INTO Bookings(trainid,trainname,uname,source,dest,sourcetime,desttime,seatno,mail) VALUES("TN00000","TEST","ME","STEST","DTEST","18:10:00","20:00:00","90","123@123.COM")

UPDATE TRAINS SET SOURCETIME="06:10:00" ,DESTTIME="20:00:00" WHERE ID="TN12345";
ALTER TABLE TRAINS modify COLUMN dest varchar(50) NOT NULL;



CREATE table trains(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    trainnumber VARCHAR(50) NOT NULL,
    remseats int NOT NULL,
    date DATE 
);


CREATE table traininfo(
    trainnumber VARCHAR(50) PRIMARY KEY,
    trainname varchar(50) NOT NULL,
    stationtime VARCHAR(200) NOT NULL,
    totalseats int NOT NULL,
    opdays VARCHAR(20) NOT NULL,
    route VARCHAR(200)
);

INSERT INTO TRAINS( trainnumber,remseats, date)    Values("TN12345",100,"2019-05-20");
INSERT INTO TRAINS( trainnumber,remseats, date)    Values("TN90671",150,"2019-05-20");
INSERT INTO TRAINS( trainnumber,remseats, date)    Values("TN70677",200,"2019-05-20");
INSERT INTO TRAINS( trainnumber,remseats, date)    Values("TN04601",250,"2019-05-20");
INSERT INTO TRAINS( trainnumber,remseats, date)    Values("TN12945",300,"2019-05-20");
INSERT INTO TRAINS( trainnumber,remseats, date)    Values("TN92371",350,"2019-05-20");
INSERT INTO TRAINS( trainnumber,remseats, date)    Values("TN80677",400,"2019-05-20");
INSERT INTO TRAINS( trainnumber,remseats, date)    Values("TN00001",450,"2019-05-20");

INSERT INTO TRAININFO( trainnumber, trainname, stationtime, totalseats, opdays, route)   Values("TN12345","FALCON","08:10:00,08:30:00,12:55:00,1:15:00,1:30:00", 100,"N,Y,Y,Y,Y,Y,N","TRICHY,ARIYALUR,CHENGALPATTU,TAMBARAM,CHENNAI");
INSERT INTO TRAININFO( trainnumber, trainname, stationtime, totalseats, opdays, route)   Values("TN90671","RHEGAR","08:10:00,08:30:00,12:55:00,1:15:00,1:30:00", 150,"Y,Y,Y,Y,Y,Y,N","TRICHY,ARIYALUR,CHENGALPATTU,TAMBARAM,CHENNAI");
INSERT INTO TRAININFO( trainnumber, trainname, stationtime, totalseats, opdays, route)   Values("TN70677","VALAR","08:10:00,08:30:00,12:55:00,1:15:00,1:30:00",  200,"Y,N,Y,N,Y,Y,N","CHENNAI,TAMBARAM,CHENGALPATTU,ARIYALUR,TRICHY");
INSERT INTO TRAININFO( trainnumber, trainname, stationtime, totalseats, opdays, route)   Values("TN04601","TYRANT","01:10:00","04:00:00","05:30:00",             250,"Y,Y,Y,Y,Y,Y,Y","CHENNAI,TRICHY,DINDIGUL,MADURAI");
INSERT INTO TRAININFO( trainnumber, trainname, stationtime, totalseats, opdays, route)   Values("TN12945","BUCKY","18:10:00,21:30:00",                           300,"Y,Y,Y,Y,Y,N,N","TRICHY,CHENNAI");
INSERT INTO TRAININFO( trainnumber, trainname, stationtime, totalseats, opdays, route)   Values("TN92371","DROGO","02:30:00,05:50:00,6:00:00,6:15:00",           350,"Y,Y,Y,Y,Y,Y,Y","TRICHY,CHENGALPATTU,TAMBARAM,CHENNAI");
INSERT INTO TRAININFO( trainnumber, trainname, stationtime, totalseats, opdays, route)   Values("TN80677","GRANDO","08:10:00,08:30:00,12:55:00,1:15:00,1:30:00", 400,"Y,N,Y,N,Y,Y,N","CHENNAI,TAMBARAM,CHENGALPATTU,ARIYALUR,TRICHY");
INSERT INTO TRAININFO( trainnumber, trainname, stationtime, totalseats, opdays, route)   Values("TN00001","CIPHANO","05:10:00,05:30:00,08:55:00,10:00:00,11:30:00",450,"Y,Y,Y,Y,Y,Y,Y","CHENNAI,TAMBARAM,TRICHY,DINDIGUL,MADURAI");


-- Updated SCHEMA

CREATE TABLE USERS(
    USERID INT PRIMARY KEY AUTO_INCREMENT,
    MAILID VARCHAR(20) NOT NULL,
    NAME VARCHAR(30) NOT NULL,
    PHONE VARCHAR(15) NOT NULL,
    GENDER VARCHAR(6),
    PASSWORD VARCHAR(30) NOT NULL,
    PRIVILEGE INT NOT NULL
);

INSERT INTO USERS(mailid,name,phone,gender,password, PRIVILEGE) VALUES("1@1.com","Admin","9876543210","MALE","1",10);
INSERT INTO USERS(mailid,name,phone,gender,password, PRIVILEGE) VALUES("arun@123.com","Arun J","9876543210","MALE","12345",1);
INSERT INTO USERS(mailid,name,phone,gender,password, PRIVILEGE) VALUES("j@123.com","Dragon J" ,"8793671538","MALE","0000",1);

CREATE TABLE STATIONNAMES(
    STATIONID VARCHAR(10) PRIMARY KEY NOT NULL,
    STATIONNAME VARCHAR(50) NOT NULL
);

INSERT INTO STATIONNAMES VALUES("TPJ","	TRICHY");
INSERT INTO STATIONNAMES VALUES("MAS","CHENNAI CENTRAL");
INSERT INTO STATIONNAMES VALUES("MS","CHENNAI EGMORE");
INSERT INTO STATIONNAMES VALUES("CBE","COIMBATORE JUNCTION");
INSERT INTO STATIONNAMES VALUES("ALU","ARIYALUR");
INSERT INTO STATIONNAMES VALUES("SRGM","SRIRANGAM");
INSERT INTO STATIONNAMES VALUES("CGL","CHENGALPATTU JUNCTION");
INSERT INTO STATIONNAMES VALUES("TBM","TAMBARAM");
INSERT INTO STATIONNAMES VALUES("MDU","MADURAI");
INSERT INTO STATIONNAMES VALUES("KQN","KODAIKKANAL ROAD");
	
CREATE TABLE TRAINNAMES(
    TRAINID INT PRIMARY KEY AUTO_INCREMENT,
    TRAINNUMBER INT NOT NULL,
    TRAINNAME VARCHAR(50) NOT NULL 
);

INSERT INTO TRAINNAMES(TRAINNUMBER,TRAINNAME) VALUES(22672,"TEJAS EXPRESS");
INSERT INTO TRAINNAMES(TRAINNUMBER,TRAINNAME) VALUES(16852,"CHENNAI EXPRESS");
INSERT INTO TRAINNAMES(TRAINNUMBER,TRAINNAME) VALUES(19603,"AII RMM HUMSAFAR");

CREATE TABLE STATIONS(
    TRAINID INT NOT NULL,
    STATIONID VARCHAR(10) NOT NULL,
    STOPNO INT NOT NULL,
    STATIONARRTIME TIME NOT NULL,
    STATIONDEPTTIME TIME NOT NULL,
    DAYNUMBER INT NOT NULL,
    FOREIGN KEY (TRAINID) REFERENCES TRAINNAMES(TRAINID),   
    FOREIGN KEY (STATIONID) REFERENCES STATIONNAMES(STATIONID)
);

INSERT INTO STATIONS(TRAINID, STATIONID, STOPNO, DAYNUMBER, STATIONARRTIME, STATIONDEPTTIME) VALUES(1,"MDU",1,1,"15:00","15:02");
INSERT INTO STATIONS(TRAINID, STATIONID, STOPNO, DAYNUMBER, STATIONARRTIME, STATIONDEPTTIME) VALUES(1,"TPJ",2,1,"16:50","16:52");
INSERT INTO STATIONS(TRAINID, STATIONID, STOPNO, DAYNUMBER, STATIONARRTIME, STATIONDEPTTIME) VALUES(1,"MS",3,1,"21:30","21:40");
INSERT INTO STATIONS(TRAINID, STATIONID, STOPNO, DAYNUMBER, STATIONARRTIME, STATIONDEPTTIME) VALUES(2,"TPJ",1,1,"22:25","22:30");
INSERT INTO STATIONS(TRAINID, STATIONID, STOPNO, DAYNUMBER, STATIONARRTIME, STATIONDEPTTIME) VALUES(2,"TBM",2,2,"04:55","05:00");
INSERT INTO STATIONS(TRAINID, STATIONID, STOPNO, DAYNUMBER, STATIONARRTIME, STATIONDEPTTIME) VALUES(2,"MS",3,2,"05:49","05:50");
INSERT INTO STATIONS(TRAINID, STATIONID, STOPNO, DAYNUMBER, STATIONARRTIME, STATIONDEPTTIME) VALUES(3,"MS",1,1,"14:00","14:15");
INSERT INTO STATIONS(TRAINID, STATIONID, STOPNO, DAYNUMBER, STATIONARRTIME, STATIONDEPTTIME) VALUES(3,"ALU",2,1,"18:56","18:58");
INSERT INTO STATIONS(TRAINID, STATIONID, STOPNO, DAYNUMBER, STATIONARRTIME, STATIONDEPTTIME) VALUES(3,"TPJ",3,1,"20:20","20:30");

CREATE TABLE SEATSINFO(
    TRAINID INT NOT NULL,
    SEATTYPE VARCHAR(20) NOT NULL,
    SEATCOUNT INT NOT NULL,
    FOREIGN KEY (TRAINID) REFERENCES TRAINNAMES(TRAINID)
);

INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(1, "FIRST CLASS AC", 10);
INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(1, "2 TIER AC", 100);
INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(1, "3 TIER AC", 150);
INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(1, "SLEEPER", 300);
INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(1, "GENERAL", 100);

INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(2, "3 TIER AC", 1500);

INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(3, "FIRST CLASS AC", 50);
INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(3, "2 TIER AC", 200);
INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(3, "3 TIER AC", 450);
INSERT INTO SEATSINFO(TRAINID, SEATTYPE, SEATCOUNT) VALUES(3, "SLEEPER", 800);

CREATE TABLE SEATSAVAILABLE (
    TRAINID INT NOT NULL,
    STATIONID VARCHAR(10) NOT NULL,
    SEATTYPE VARCHAR(20) NOT NULL,
    SEATSAVAILABLE INT NOT NULL,
    DAY DATE NOT NULL 
);

INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MDU", "FIRST CLASS AC", 10, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "TPJ", "FIRST CLASS AC", 10, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MS", "FIRST CLASS AC", 10, "2019-05-21");

INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MDU", "2 TIER AC", 100, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "TPJ", "2 TIER AC", 100, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MS", "2 TIER AC", 100, "2019-05-21");

INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MDU", "3 TIER AC", 150, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "TPJ", "3 TIER AC", 150, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MS", "3 TIER AC", 150, "2019-05-21");

INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MDU", "SLEEPER", 300, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "TPJ", "SLEEPER", 300, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MS", "SLEEPER", 300, "2019-05-21");

INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MDU", "GENERAL", 100, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "TPJ", "GENERAL", 100, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(1, "MS", "GENERAL", 100, "2019-05-21");


INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(2, "TPJ", "3 TIER AC", 1500, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(2, "TBM", "3 TIER AC", 1500, "2019-05-22");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(2, "MS", "3 TIER AC", 1500, "2019-05-22");


INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "MDU", "FIRST CLASS AC", 50, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "TPJ", "FIRST CLASS AC", 50, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "MS", "FIRST CLASS AC", 50, "2019-05-21");

INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "MS", "2 TIER AC", 200, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "ALU", "2 TIER AC", 100, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "TPJ", "2 TIER AC", 150, "2019-05-21");

INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "MS", "3 TIER AC", 450, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "ALU", "3 TIER AC", 450, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "TPJ", "3 TIER AC", 450, "2019-05-21");

INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "MS", "SLEEPER", 800, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "ALU", "SLEEPER", 800, "2019-05-21");
INSERT INTO SEATSAVAILABLE(TRAINID, STATIONID, SEATTYPE, SEATSAVAILABLE, DAY) VALUES(3, "TPJ", "SLEEPER", 800, "2019-05-21");

CREATE TABLE FARES (
    TRAINID INT NOT NULL,
    SEATTYPE VARCHAR(50) NOT NULL,
    FARETYPE VARCHAR(50) NOT NULL,
    FARE INT NOT NULL,
    FOREIGN KEY (TRAINID) REFERENCES TRAINNAMES(TRAINID) 
);

INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (1,"FIRST CLASS AC","NORMAL",500);
INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (1,"2 TIER AC","NORMAL",400);
INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (1,"3 TIER AC","NORMAL",300);
INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (1,"SLEEPER","NORMAL",200);
INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (1,"GENERAL","NORMAL",100);

INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (2,"3 TIER AC","NORMAL",420);

INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (3,"FIRST CLASS AC","NORMAL",550);
INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (3,"2 TIER AC","NORMAL",410);
INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (3,"3 TIER AC","NORMAL",330);
INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (3,"SLEEPER","NORMAL",210);
INSERT INTO FARES(TRAINID, SEATTYPE, FARETYPE, FARE) VALUES (3,"GENERAL","NORMAL",130);

CREATE TABLE BOOKINGS (
    PNR INT PRIMARY KEY AUTO_INCREMENT,
    USERID INT NOT NULL,
    TRAINID INT NOT NULL,
    MAILID VARCHAR(30),
    MODEOFPAYMENT VARCHAR(20) NOT NULL,
    PAYMENTSTATUS VARCHAR(20) NOT NULL,
    ACCOUNTNUMBER VARCHAR(20),
    IFSCCODE VARCHAR(20),
    CARDNUMBER VARCHAR(20),
    TICKETSTATUS VARCHAR(20),
    DATEOFTRAVEL DATE,
    SOURCE VARCHAR(50),
    DEST VARCHAR(50),
    FARE INT NOT NULL,
    FOREIGN KEY (USERID) REFERENCES USERS(USERID)
);

CREATE TABLE PASSENGERINFO(
    PNR INT NOT NULL,
    PASSENGERNAME VARCHAR(30),
    SEATNO INT,
    AGE INT NOT NULL,
    GENDER VARCHAR(20),
    FOREIGN KEY (PNR) REFERENCES BOOKINGS(PNR)
);

CREATE TABLE OPERATINGDAYS(
    TRAINID INT NOT NULL,
    DAYS VARCHAR(3) NOT NULL
);

-- YET TO USE OPERATING DAYS


-- LOGICAL PART

    SELECT * FROM STATIONS WHERE STATIONID = "TPJ"; 
    UPDATE SEATSAVAILABLE SET SEATSAVAILABLE = 8 WHERE TRAINID = "1" AND DAY = "2019-05-21" AND (STATIONID = "MDU" OR STATIONID = "MS") AND SEATTYPE = "FIRST CLASS AC";

SELECT  * FROM STATIONS WHERE TRAINID = 1 and STOPNO >= 3 AND STOPNO <= 2 ;

UPDATE TABLE BOOKINGS SET TICKETSTATUS = "WAITING LIST" WHERE PNR = 1;

SELECT BOOKINGS.PNR, BOOKINGS.USERID, BOOKINGS.TRAINID,
                    BOOKINGS.TICKETSTATUS, BOOKINGS.DATEOFTRAVEL, BOOKINGS.SOURCE, BOOKINGS.DEST, BOOKINGS.FARE, 
                    PASSENGERINFO.PASSENGERNAME, PASSENGERINFO.SEATNO, PASSENGERINFO.AGE, PASSENGERINFO.GENDER
                    FROM BOOKINGS,PASSENGERINFO  WHERE BOOKINGS.USERID = 2 AND PASSENGERINFO.PNR = BOOKINGS.PNR;

SELECT TRAINNAME, TRAINNUMBER, STATIONARRTIME FROM TRAINNAMES T,  STATIONS S WHERE T.TRAINID = S.TRAINID;  

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
INSERT INTO TRAININFO( trainnumber, trainname, stationtime, totalseats, opdays, route)  Values("TN00001","CIPHANO","05:10:00,05:30:00,08:55:00,10:00:00,11:30:00",450,"Y,Y,Y,Y,Y,Y,Y","CHENNAI,TAMBARAM,TRICHY,DINDIGUL,MADURAI");

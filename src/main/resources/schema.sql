create table Users
(
   userName varchar(255) not null,	
   latestLatitude double not null,
   latestLongitude double not null,
   latestUpdated timestamp
);
create table GPS
(
   id varchar(255) NOT NULL,
   PRIMARY KEY  (id),
   userName varchar(255) not null,
   fileName varchar(255) not null,
   name varchar(255),
   description LONGVARCHAR,
   author varchar(255),
   datetime timestamp
);
create table WayPoint
(
   gpsId varchar(255) NOT NULL,
   latitude double not null,
   longitude double not null,
   sym varchar(255),
   name varchar(255),
);
create table TrackPoint
(
   gpsId varchar(255) NOT NULL,
   latitude double not null,
   longitude double not null,
   ele varchar(255),
   datetime timestamp,
);
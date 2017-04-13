CREATE SEQUENCE ADDRESS_ID;

create table ADDRESS (
    id bigint default ADDRESS_ID.nextval primary key,
    firstname VARCHAR(30) NOT NULL,
    lastname VARCHAR(30) NOT NULL, 
    street VARCHAR(30) NOT NULL
);
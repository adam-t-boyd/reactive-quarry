DROP TABLE IF EXISTS quarry;

create table quarry (
id SERIAL,
establishedDate TIMESTAMP,
disused boolean,
CONSTRAINT quarry_pk PRIMARY KEY (id)
);
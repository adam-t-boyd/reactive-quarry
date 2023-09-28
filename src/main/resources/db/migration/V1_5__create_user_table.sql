create table user_details (
id SERIAL,
firstname VARCHAR(255),
lastname VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255),
CONSTRAINT user_details_pk PRIMARY KEY (id)
);
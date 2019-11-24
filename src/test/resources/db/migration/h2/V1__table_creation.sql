CREATE SEQUENCE user_authentication_seq;
--setval('user_authentication_seq', 10000000);

CREATE TABLE user_authentication(
id BIGINT default user_authentication_seq.nextval NOT NULL,
email varchar(100) NOT NULL,
password varchar(100) NOT NULL,
CONSTRAINT email_unique UNIQUE (email),
CONSTRAINT user_pk PRIMARY KEY(id)
);
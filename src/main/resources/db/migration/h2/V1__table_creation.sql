CREATE SEQUENCE user_authentication_seq;
--setval('user_authentication_seq', 10000000);

CREATE TABLE user_account(
id BIGINT default user_authentication_seq.nextval NOT NULL,
email varchar(100) NOT NULL,
password varchar(100) NOT NULL,
CONSTRAINT email_unique UNIQUE (email),
CONSTRAINT user_authentication_pk PRIMARY KEY(id)
);

CREATE SEQUENCE user_profile_seq;
CREATE TABLE user_profile(
id BIGINT default user_profile_seq.nextval NOT NULL,
username varchar(100) NOT NULL,
mobile BIGINT,
city varchar(100),
gender CHAR,
CONSTRAINT username_unique UNIQUE (username),
CONSTRAINT user_profile_pk PRIMARY KEY(id)
);
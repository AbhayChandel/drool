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

CREATE SEQUENCE discussion_topic_seq;
CREATE TABLE discussion_topic(
id BIGINT default discussion_topic_seq.nextval NOT NULL,
topic varchar(250) NOT NULL,
user_id BIGINT NOT NULL,
date_posted TIMESTAMP,
last_active_date TIMESTAMP,
views INT default 0,
likes INT default 0,
active BOOLEAN default true NOT NULL,
CONSTRAINT discussion_topic_pk PRIMARY KEY(id)
);

CREATE TABLE discussion_topic_user_likes(
discussion_topic_id BIGINT,
user_id BIGINT
);

CREATE SEQUENCE discussion_topic_replies_seq;
CREATE TABLE discussion_topic_replies(
id BIGINT default discussion_topic_replies_seq.nextval NOT NULL,
topic_id BIGINT NOT NULL,
reply varchar(500) NOT NULL,
user_id BIGINT NOT NULL,
date_posted TIMESTAMP,
likes INT default 0,
active BOOLEAN default true NOT NULL,
CONSTRAINT discussion_topic_replies_pk PRIMARY KEY(id)
);

CREATE TABLE discussion_topic_reply_user_likes(
discussion_topic_reply_id BIGINT,
user_id BIGINT
);
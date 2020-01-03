CREATE TABLE user_account
(
    id       SERIAL,
    email    varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT user_authentication_pk PRIMARY KEY (id)
);

ALTER SEQUENCE user_account_id_seq RESTART WITH 10001004;

CREATE TABLE user_profile
(
    user_account_id BIGINT NOT NULL REFERENCES user_account (id),
    username varchar(100) NOT NULL,
    mobile   BIGINT,
    city     varchar(100),
    gender   CHAR,
    CONSTRAINT username_unique UNIQUE (username),
    CONSTRAINT user_profile_pk PRIMARY KEY (user_account_id)
);

CREATE TABLE discussion_topic
(
    id      SERIAL,
    topic            varchar(250)                                    NOT NULL,
    user_id          BIGINT                                          NOT NULL,
    date_posted      TIMESTAMP,
    date_last_active TIMESTAMP,
    views            INT     default 0,
    likes            INT     default 0,
    replies          INT     default 0,
    active           BOOLEAN default true                            NOT NULL,
    CONSTRAINT discussion_topic_pk PRIMARY KEY (id)
);

ALTER SEQUENCE discussion_topic_id_seq RESTART WITH 10001001;

CREATE TABLE discussion_topic_user_like
(
    user_id  BIGINT NOT NULL,
    topic_id BIGINT NOT NULL
);

CREATE TABLE discussion_reply
(
    id                  SERIAL,
    discussion_topic_id BIGINT,
    reply               varchar(500)         NOT NULL,
    user_id             BIGINT               NOT NULL,
    active              BOOLEAN default true NOT NULL,
    CONSTRAINT discussion_reply_pk PRIMARY KEY (id)
);

CREATE TABLE discussion_reply_activity
(
    discussion_reply_id BIGINT NOT NULL REFERENCES discussion_reply (id),
    date_posted         TIMESTAMP,
    likes               INT default 0
);

CREATE TABLE discussion_reply_user_like
(
    user_id  BIGINT NOT NULL,
    reply_id BIGINT NOT NULL
);

CREATE TABLE POST_TYPE
(
    post_type_id INT,
    post_type    VARCHAR(50)
);


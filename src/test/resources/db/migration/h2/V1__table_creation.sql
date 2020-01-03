--SET TIMEZONE TO 'Asia/Kolkata';

CREATE SEQUENCE user_account_id_seq;
--setval('user_authentication_seq', 10000000);

CREATE TABLE user_account
(
    id       BIGINT default user_account_id_seq.nextval NOT NULL,
    email    varchar(100)                               NOT NULL,
    password varchar(100)                               NOT NULL,
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT user_authentication_pk PRIMARY KEY (id)
);

CREATE TABLE user_profile
(
    user_account_id BIGINT       NOT NULL,
    username        varchar(100) NOT NULL,
    mobile          BIGINT,
    city            varchar(100),
    gender          CHAR,
    CONSTRAINT username_unique UNIQUE (username),
    CONSTRAINT user_profile_pk PRIMARY KEY (user_account_id),
    CONSTRAINT user_profile_fk FOREIGN KEY (user_account_id) REFERENCES user_account (id)
);

CREATE SEQUENCE discussion_topic_id_seq;
CREATE TABLE discussion_topic
(
    id               BIGINT  default discussion_topic_id_seq.nextval NOT NULL,
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

CREATE TABLE discussion_topic_user_like
(
    user_id  BIGINT NOT NULL,
    topic_id BIGINT NOT NULL
);

CREATE SEQUENCE discussion_reply_id_seq;
CREATE TABLE discussion_reply
(
    id                  BIGINT  default discussion_reply_id_seq.nextval NOT NULL,
    discussion_topic_id BIGINT,
    reply               varchar(500)                                    NOT NULL,
    user_id             BIGINT                                          NOT NULL,
    active              BOOLEAN default true                            NOT NULL,
    CONSTRAINT discussion_reply_pk PRIMARY KEY (id)
);

CREATE TABLE discussion_reply_activity
(
    discussion_reply_id BIGINT NOT NULL,
    date_posted         TIMESTAMP,
    likes               INT default 0,
    CONSTRAINT discussion_reply_activity FOREIGN KEY (discussion_reply_id) REFERENCES discussion_reply (id)
--CONSTRAINT discussion_topic_stats_fk FOREIGN KEY (id) REFERENCES discussion_topic(id)
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


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
    date_posted         TIMESTAMP,
    likes               INT     default 0,
    CONSTRAINT discussion_reply_pk PRIMARY KEY (id)
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

CREATE OR REPLACE VIEW user_profile_card_view AS
SELECT up.user_account_id AS userId,
       up.username        AS username
FROM user_profile up;

CREATE OR REPLACE VIEW discussion_topic_card_view AS
SELECT topic.id               AS topicId,
       topic.topic            AS topic,
       topic.user_id          AS userId,
       topic.date_posted      AS datePosted,
       topic.date_last_active AS dateLastActive,
       topic.views            AS views,
       topic.likes            AS likes,
       topic.replies          AS replies,
       upcard.username        AS username
FROM discussion_topic topic
         INNER JOIN user_profile_card_view upcard ON topic.user_id = upcard.userId
where topic.active = true;

CREATE OR REPLACE VIEW discussion_reply_card_view AS
SELECT reply.id                  AS replyId,
       reply.discussion_topic_id AS discussionTopicId,
       reply.reply               AS reply,
       reply.user_id             AS userId,
       reply.date_posted         AS datePosted,
       reply.likes               AS likes,
       upcard.username           AS username
FROM discussion_reply reply
         INNER JOIN user_profile_card_view upcard ON reply.user_id = upcard.userId
where reply.active = true;







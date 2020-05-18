CREATE SEQUENCE user_account_id_seq;

CREATE TABLE user_account
(
    id       BIGINT                default user_account_id_seq.nextval NOT NULL,
    email    varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    username varchar(100) NOT NULL,
    mobile   varchar(10),
    active   boolean      NOT NULL DEFAULT TRUE,
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT username_unique UNIQUE (username),
    CONSTRAINT mobile_unique UNIQUE (mobile),
    CONSTRAINT user_account_pk PRIMARY KEY (id)
);

CREATE TABLE user_profile
(
    id        BIGINT NOT NULL,
    name      varchar(100),
    city      varchar(75),
    gender    varchar(5),
    join_date timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT user_profile_pk PRIMARY KEY (id),
    CONSTRAINT user_profile_fk FOREIGN KEY (id) REFERENCES user_account (id)
);

CREATE TABLE verification_type
(
    id   INT         NOT NULL,
    name VARCHAR(25) NOT NULL,
    CONSTRAINT verification_type_pk PRIMARY KEY (id)
);

CREATE TABLE user_account_verification
(
    user_id  BIGINT NOT NULL,
    type     INT    NOT NULL,
    verified boolean DEFAULT FALSE,
    CONSTRAINT user_account_verification_pk PRIMARY KEY (user_id, type),
    CONSTRAINT user_account_verification_fk FOREIGN KEY (type) REFERENCES verification_type (id)
);

CREATE TABLE account_status
(
    code        INT         NOT NULL,
    description VARCHAR(50) NOT NULL,
    CONSTRAINT account_status_pk PRIMARY KEY (code)
);

CREATE TABLE user_account_status
(
    user_id BIGINT NOT NULL,
    status  int    NOT NULL,
    remarks varchar(100),
    CONSTRAINT user_account_status_pk PRIMARY KEY (user_id),
    CONSTRAINT user_account_status_fk FOREIGN KEY (status) REFERENCES account_status (code)
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
    active           boolean DEFAULT TRUE                            NOT NULL,
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
    post_type    VARCHAR(10),
    descritpion  VARCHAR(50)
);

CREATE OR REPLACE VIEW user_profile_card_view AS
SELECT ua.id       AS userId,
       ua.username AS username
FROM user_account ua;

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







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
    name      varchar,
    city      varchar,
    gender    varchar,
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


CREATE SEQUENCE post_id_seq;
CREATE TABLE post
(
    id              BIGINT  default post_id_seq.nextval NOT NULL,
    active          BOOLEAN default true                NOT NULL,
    post_type       INT                                 NOT NULL,
    title           VARCHAR                             NOT NULL,
    date_posted     TIMESTAMP,
    likes           INT     default 0,
    views           INT     default 0,
    owner           BIGINT                              NOT NULL,
    d_type          VARCHAR                             NOT NULL,
    source_video_id varchar,
    text            varchar,
    cover_picture   varchar,
    CONSTRAINT post_pk PRIMARY KEY (id)
);

CREATE TABLE POST_TYPE
(
    id          INT,
    type        VARCHAR(10),
    description VARCHAR(50)
);

CREATE TABLE post_format
(
    id     INT         NOT NULL,
    format VARCHAR(50) NOT NULL,
    CONSTRAINT post_format_pk PRIMARY KEY (id)
);

CREATE SEQUENCE article_comment_id_seq;
CREATE TABLE article_comment
(
    id          BIGINT  default article_comment_id_seq.nextval NOT NULL,
    comment     varchar                                        NOT NULL,
    article_id  INT                                            NOT NULL,
    date_posted TIMESTAMP,
    likes       INT     default 0,
    user_id     BIGINT                                         NOT NULL,
    active      BOOLEAN default true                           NOT NULL,
    CONSTRAINT article_comment_pk PRIMARY KEY (id),
    CONSTRAINT article_comment_article_fk FOREIGN KEY (article_id) REFERENCES post (id)
);

CREATE SEQUENCE collection_id_seq;
CREATE TABLE collection
(
    id         BIGINT default collection_id_seq.nextval NOT NULL,
    name       VARCHAR                                  NOT NULL,
    about      VARCHAR                                  NOT NULL,
    visibility INT                                      NOT NULL,
    owner      BIGINT                                   NOT NULL,
    CONSTRAINT collection_pk PRIMARY KEY (id)
);

CREATE TABLE visibility
(
    id         INT         NOT NULL,
    visibility VARCHAR(50) NOT NULL,
    CONSTRAINT visibility_pk PRIMARY KEY (id)
);

CREATE TABLE POST_COLLECTION
(
    collection_id BIGINT,
    post_id       BIGINT,
    CONSTRAINT collection_post_pk PRIMARY KEY (collection_id, post_id),
    CONSTRAINT collection_fk FOREIGN KEY (collection_id) references collection (id),
    CONSTRAINT post_fk FOREIGN KEY (post_id) references post (id)
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
    user_id             INT                                             NOT NULL,
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



CREATE OR REPLACE VIEW user_profile_card_view AS
SELECT ua.id       AS userId,
       ua.username AS username
FROM user_account ua;

/*CREATE OR REPLACE VIEW discussion_topic_card_view AS
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
where reply.active = true;*/







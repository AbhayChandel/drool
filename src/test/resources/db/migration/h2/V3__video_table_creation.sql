CREATE SEQUENCE video_id_seq;

CREATE TABLE video
(
    id              BIGINT  default video_id_seq.nextval NOT NULL,
    title           varchar(250)                         NOT NULL,
    user_id         BIGINT                               NOT NULL,
    source_video_id varchar                              NOT NULL,
    description     varchar,
    likes           INT     default 0,
    date_posted     TIMESTAMP,
    views           INT     default 0,
    comments        INT     default 0,
    post_type       INT                                  NOT NULL,
    active          BOOLEAN default true                 NOT NULL,
    CONSTRAINT video_pk PRIMARY KEY (id)
);

CREATE TABLE video_user_like
(
    user_id  BIGINT NOT NULL,
    video_id BIGINT NOT NULL
);

CREATE SEQUENCE video_comment_id_seq;
CREATE TABLE video_comment
(
    id          BIGINT  default video_comment_id_seq.nextval NOT NULL,
    video_id    BIGINT,
    user_id     BIGINT                                       NOT NULL,
    date_posted TIMESTAMP,
    likes       INT     default 0,
    comment     varchar                                      NOT NULL,
    active      BOOLEAN default true                         NOT NULL,
    CONSTRAINT video_comment_pk PRIMARY KEY (id)
);

CREATE TABLE video_comment_user_like
(
    user_id          BIGINT NOT NULL,
    video_comment_id BIGINT NOT NULL
);

CREATE OR REPLACE VIEW user_profile_card_view AS
SELECT up.user_account_id AS userId,
       up.username        AS username
FROM user_profile up;

CREATE OR REPLACE VIEW video_card_view AS
SELECT video.id                                  AS videoId,
       video.title                               AS title,
       video.user_id                             AS userId,
       video.source_video_id                     AS sourceVideoId,
       to_char(video.date_posted, 'DD-Mon-YYYY') AS datePosted,
       video.views                               AS views,
       video.likes                               AS likes,
       video.description                         AS description,
       upcard.username                           AS username
FROM video video
         INNER JOIN user_profile_card_view upcard ON video.user_id = upcard.userId
where video.active;

CREATE OR REPLACE VIEW video_comment_card_view AS
SELECT comment.id                                             AS commentId,
       comment.video_id                                       AS videoId,
       comment.comment                                        AS comment,
       comment.user_id                                        AS userId,
       to_char(comment.date_posted, 'DD-Mon-YYYY HH12:MI AM') AS datePosted,
       comment.likes                                          AS likes,
       upcard.username                                        AS username
FROM video_comment comment
         INNER JOIN user_profile_card_view upcard ON comment.user_id = upcard.userId
where comment.active;
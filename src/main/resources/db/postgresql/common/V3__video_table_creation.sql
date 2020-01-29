CREATE TABLE video
(
    id              SERIAL,
    title           varchar(250)         NOT NULL,
    user_id         BIGINT               NOT NULL,
    source_video_id varchar              NOT NULL,
    description     varchar,
    likes           INT     default 0,
    date_posted     TIMESTAMP,
    views           INT     default 0,
    post_type       INT                  NOT NULL,
    active          BOOLEAN default true NOT NULL,
    CONSTRAINT video_pk PRIMARY KEY (id)
);

ALTER SEQUENCE video_id_seq RESTART WITH 30000001;

CREATE TABLE video_user_like
(
    user_id  BIGINT NOT NULL,
    video_id BIGINT NOT NULL
);

CREATE TABLE video_comment
(
    id          SERIAL,
    video_id    BIGINT,
    user_id     BIGINT               NOT NULL,
    date_posted TIMESTAMP,
    likes       INT     default 0,
    comment     varchar              NOT NULL,
    active      BOOLEAN default true NOT NULL,
    CONSTRAINT video_comment_pk PRIMARY KEY (id)
);

ALTER SEQUENCE video_comment_id_seq RESTART WITH 31000001;

CREATE TABLE video_comment_user_like
(
    user_id          BIGINT NOT NULL,
    video_comment_id BIGINT NOT NULL
);

CREATE OR REPLACE VIEW video_card_view AS
SELECT video.id                                  AS videoId,
       pt.post_type                              As postType,
       video.title                               AS title,
       video.user_id                             AS userId,
       video.source_video_id                     AS sourceVideoId,
       to_char(video.date_posted, 'DD-Mon-YYYY') AS datePosted,
       video.views                               AS views,
       video.likes                               AS likes,
       video.description                         AS description,
       count(comment.id)                         AS commentCount,
       upcard.username                           AS username
FROM video video
         INNER JOIN user_profile_card_view upcard ON video.user_id = upcard.userId
         INNER JOIN POST_TYPE pt ON video.post_type = pt.post_type_id
         LEFT JOIN video_comment comment on video.id = comment.video_id
where video.active
  and comment.active
group by video.id, upcard.username, pt.post_type;

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



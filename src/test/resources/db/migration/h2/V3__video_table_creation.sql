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

CREATE OR REPLACE VIEW video_card_view AS
SELECT video.id              AS videoId,
       pt.post_type          As postType,
       video.title           AS title,
       video.user_id         AS userId,
       video.source_video_id AS sourceVideoId,
       video.date_posted     AS datePosted,
       video.views           AS views,
       video.likes           AS likes,
       video.description     AS description,
       count(comment.id)     AS commentCount,
       upcard.username       AS username
FROM video video
         INNER JOIN user_profile_card_view upcard ON video.user_id = upcard.userId
         INNER JOIN POST_TYPE pt ON video.post_type = pt.post_type_id
         LEFT JOIN video_comment comment on video.id = comment.video_id
where video.active
  and comment.active;

CREATE OR REPLACE VIEW video_comment_card_view AS
SELECT comment.id          AS commentId,
       comment.video_id    AS videoId,
       comment.comment     AS comment,
       comment.user_id     AS userId,
       comment.date_posted AS datePosted,
       comment.likes       AS likes,
       upcard.username     AS username
FROM video_comment comment
         INNER JOIN user_profile_card_view upcard ON comment.user_id = upcard.userId
where comment.active;

insert into video(id, title, user_id, source_video_id, description, likes, date_posted, views, post_type, active)
values (1, 'Reviewed Lakme 9to5 lipcolor', 1, 'M7lc1UVf-VE', 'I have tried to swatch all the shades of 9to5 lipcolor',
        1456, now(), 245654, 7, true);

insert into video_comment(id, video_id, user_id, date_posted, likes, comment, active)
values (1, 1, 2, now(), 55675, 'Great job. I really like all your videos', true);

insert into video_comment(id, video_id, user_id, date_posted, likes, comment, active)
values (2, 1, 3, now(), 55675, 'Oh what a great review', false);

insert into video_comment(id, video_id, user_id, date_posted, likes, comment, active)
values (3, 1, 3, now(), 12, 'Oh what a useless review. Hated it', true);

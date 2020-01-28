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
         LEFT JOIN video_comment comment on video.id = comment.video_id
where video.active
  and comment.active
group by video.id, upcard.username;

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

insert into video(id, title, user_id, source_video_id, description, likes, date_posted, views, post_type, active)
values (1, 'Reviewed Lakme 9to5 lipcolor', 1, 'M7lc1UVf-VE', 'I have tried to swatch all the shades of 9to5 lipcolor',
        10, now(), 200, 7, true);

insert into video_comment(id, video_id, user_id, date_posted, likes, comment, active)
values (1, 1, 2, now(), 2, 'Great job. I really like all your videos', true);

insert into video_comment(id, video_id, user_id, date_posted, likes, comment, active)
values (2, 1, 3, now(), 5, 'Oh what a great review', false);

insert into video_comment(id, video_id, user_id, date_posted, likes, comment, active)
values (3, 1, 3, now(), 12, 'Oh what a useless review. Hated it', true);

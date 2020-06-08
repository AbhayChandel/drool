--passwords were encoded with bCryptEncoders https://bcrypt-generator.com/
insert into user_account(email, password, username, mobile)
values ('shabana@gmail.com', '$2y$12$RTmDCrxkhq.XQkFk6Aw85uC5pBCMIjORk8qutEtwtACl7a6R2cB4G', 'shabanastyle',
        '9876543210');
insert into user_account(email, password, username)
values ('priya.singh@gmail.com', '$2y$12$nfSQJzgpJ3gTu.CczB6BiuceNqA6niFi7EX03p4Ep3205kL5I2pDy', 'priya21');
insert into user_account(email, password, username, active)
values ('sonam99@gmail.com', '$2y$12$nkEeE1P.hWfg1iqhp8JWOea9F7lEEzBi07ZdGs1ujrVJM5YVYnQqi', 'sonam99', false);

insert into user_profile(id, name, city, gender)
values (1, 'Shabana Sheikh', 'Lucknow', 'F');
insert into user_profile(id, name, city, gender)
values (2, 'Priya Sharma', 'Pune', 'F');
insert into user_profile(id, name, city, gender)
values (3, 'Sonam Kapoor', 'Jaipur', 'F');

insert into verification_type
values (1, 'email');
insert into verification_type
values (2, 'mobile');

insert into user_account_verification(user_id, type)
values (1, 1);
insert into user_account_verification(user_id, type)
values (1, 2);
insert into user_account_verification(user_id, type)
values (2, 1);
insert into user_account_verification(user_id, type)
values (2, 2);
insert into user_account_verification(user_id, type)
values (3, 1);
insert into user_account_verification(user_id, type)
values (3, 2);

insert into account_status
values (101, 'Active');
insert into account_status
values (102, 'Suspended');
insert into account_status
values (103, 'Deactivated');

insert into user_account_status
values (3, 102, 'Spamming');

insert into video(id, title, description, source_video_id, date_posted, owner)
values (1000001, 'How to pick the right shade for your skin tone', 'This is a guide to pick the best lipstick shades',
        'zdtx83s', '2020-06-07 19:10:25', 1);
insert into video(id, title, description, source_video_id, date_posted, owner)
values (1000002, 'Top ten lipstick picks for this summer', 'Here is the list of all the shades', 'anu56sd',
        '2020-06-06 19:10:25', 2);
insert into video(id, title, description, source_video_id, date_posted, owner)
values (1000003, 'Reviewed Forest Essential Face Scrub', 'You can but it at this link.', 'ajgf567kx',
        '2020-06-01 19:10:25', 3);

insert into video_like(video_id, user_id)
values (1000001, 1);
insert into video_like(video_id, user_id)
values (1000001, 2);
insert into video_like(video_id, user_id)
values (1000001, 3);

insert into video_comment(id, comment, video_id, date_posted, likes, user_id, active)
values (301, 'Great tips. thank you so much.', 1000001, now(), 0, 3, true);
insert into video_comment(id, comment, video_id, date_posted, likes, user_id, active)
values (302, 'Wow !! I really like all those colors', 1000001, now(), 0, 3, true);
insert into video_comment(id, comment, video_id, date_posted, likes, user_id, active)
values (303, 'I have 3 of those shades', 1000001, now(), 0, 2, true);

insert into article(id, title, body, cover_picture, date_posted, owner)
values (2000001, 'My favorite lipsticks for th fall', 'He is the detailed guide', 'd6dj5j.jps',
        '2020-06-05 19:10:25', 2);
insert into article(id, title, body, cover_picture, date_posted, owner)
values (2000002, 'My favorite lipsticks for th winter', 'He is the detailed guide', 'a56js.jps',
        '2020-06-04 19:10:25', 3);
insert into article(id, title, body, cover_picture, date_posted, owner)
values (2000003, 'Best places to shop for handbags', '', 'abgh.jps',
        '2020-05-31 14:10:25', 3);

insert into article_like(article_id, user_id)
values (2000001, 1);
insert into article_like(article_id, user_id)
values (2000001, 2);

insert into article_comment(id, comment, article_id, date_posted, likes, user_id, active)
values (301, 'This is a very nice article', 2000001, now(), 0, 3, true);
insert into article_comment
values (302, 'I think you should also mention the skin color', 2000001, now(), 0, 3, true);
insert into article_comment
values (303, 'Yes, thanks for the suggestion', 2000001, now(), 0, 2, true);
insert into article_comment
values (304, 'Really enjoyed the article', 2000001, now(), 0, 1, true);

insert into discussion(id, title, details, cover_picture, date_posted, owner, active)
values (40001, 'Where to buy genuine chanel products in Delhi', 'I have found Chanel at following locations',
        'zdtx83s.jpg', '2020-06-03 19:10:25', 1, true);
insert into discussion(id, title, details, cover_picture, date_posted, owner, active)
values (40002, 'How can I get fairer skin', '',
        'sg675j.jpg', '2020-06-02 19:10:25', 2, true);
insert into discussion(id, title, details, cover_picture, date_posted, owner, active)
values (40003, 'Which is a good alternate to Lakme', '',
        'sg675j.jpg', '2020-05-30 19:10:25', 2, true);

insert into discussion_like(discussion_id, user_id)
values (40002, 3);
insert into discussion_like(discussion_id, user_id)
values (40002, 2);

insert into discussion_reply(id, reply, discussion_id, date_posted, likes, user_id, active)
values (301, 'Look for some fairness products', 40002, now(), 0, 3, true);
insert into discussion_reply(id, reply, discussion_id, date_posted, likes, user_id, active)
values (302, 'I think you should try natural remedies', 40002, now(), 0, 3, true);
insert into discussion_reply(id, reply, discussion_id, date_posted, likes, user_id, active)
values (303, 'Yes, thanks for the suggestion', 40002, now(), 0, 2, true);


insert into post(id, type, title, owner, d_type, source_video_id, text)
values (101, 1, 'Lakme 9to5 Lip Color', 2, 'video', 'xsztiz', 'This is aideo review for Lakme 9to5');

insert into post(id, type, title, owner, d_type, cover_picture, text, views, likes)
values (102, 2, 'How to choose the right shade', 3, 'article', 'xsztiz.jpg',
        'This is an article about picking the right lip color shade', 456765, 3456);

insert into post(id, type, title, owner, d_type, cover_picture, text, views, likes)
values (103, 3, 'Which is the best body lotion for dry skin?', 1, 'discussion', 'discuss.jpg',
        'I have shortlisted the following lotions', 12345, 126);


insert into collection(id, name, about, visibility, owner)
values (1001, 'Party Dresses', 'It is about party dresses', 2, 2);

insert into collection_video(collection_id, video_id)
values (1001, 1000001);
insert into collection_video(collection_id, video_id)
values (1001, 1000002);

insert into collection_article(collection_id, article_id)
values (1001, 2000001);
insert into collection_article(collection_id, article_id)
values (1001, 2000002);

insert into collection_discussion(collection_id, discussion_id)
values (1001, 40001);
insert into collection_discussion(collection_id, discussion_id)
values (1001, 40002);

insert into feed(post_id, post_type, date_posted)
values (1000001, 1, '2016-05-22 19:10:25');
insert into feed(post_id, post_type, date_posted)
values (1000002, 1, '2016-05-23 19:10:25');
insert into feed(post_id, post_type, date_posted)
values (2000001, 2, '2016-05-24 19:10:25');
insert into feed(post_id, post_type, date_posted)
values (2000002, 2, '2016-05-25 19:10:25');
insert into feed(post_id, post_type, date_posted)
values (40001, 3, '2016-05-24 19:10:25');
insert into feed(post_id, post_type, date_posted)
values (40002, 3, '2016-05-25 19:10:25');


insert into POST_TYPE(id, type)
values (1, 'video');
insert into POST_TYPE(id, type)
values (2, 'article');
insert into POST_TYPE(id, type)
values (3, 'discussion');

insert into visibility
values (1, 'public');
insert into visibility
values (2, 'private');

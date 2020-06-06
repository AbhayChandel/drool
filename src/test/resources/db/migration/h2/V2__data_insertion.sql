-- passwords were encoded with bCryptEncoders https://bcrypt-generator.com/
insert into user_account(email, password, username, mobile)
values ('talk_to_priyanka@gmail.com', '$2y$12$bDHT1s2E8pH7if.XQr/14urBafxek/62kxe9R.omoloGhh9XnwdbW', 'Priyankalove',
        '9876543210');
insert into user_account(email, password, username)
values ('priya.singh@gmail.com', '$2y$12$nfSQJzgpJ3gTu.CczB6BiuceNqA6niFi7EX03p4Ep3205kL5I2pDy', 'priyankasingh');
insert into user_account(email, password, username, active)
values ('sonam99@gmail.com', '$2y$12$nkEeE1P.hWfg1iqhp8JWOea9F7lEEzBi07ZdGs1ujrVJM5YVYnQqi', 'sonam31', false);

insert into user_profile(id, name, city, gender)
values (1, 'Priyanka Singh', 'Indore', 'F');
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

insert into video(id, title, description, source_video_id, owner)
values (1000001, 'How to pick the right shade for your skin tone', 'This is a guide to pick the best lipstick shades',
        'zdtx83s', 1);
insert into video(id, title, description, source_video_id, owner)
values (1000002, 'Top ten lipstick picks for this summer', 'Here is the list of all the shades', 'anu56sd', 2);

insert into article(id, title, body, cover_picture, owner)
values (2000001, 'My favorite lipsticks for th fall', 'He is the detailed guide', 'd6dj5j.jps', 2);
insert into article(id, title, body, cover_picture, owner)
values (2000002, 'My favorite lipsticks for th winter', 'He is the detailed guide', 'a56js.jps', 3);

insert into post(id, type, title, owner, d_type, source_video_id, text)
values (101, 1, 'Lakme 9to5 Lip Color', 2, 'video', 'xsztiz', 'This is aideo review for Lakme 9to5');

insert into post(id, type, title, owner, d_type, cover_picture, text, views, likes)
values (102, 2, 'How to choose the right shade', 3, 'article', 'xsztiz.jpg',
        'This is an article about picking the right lip color shade', 456765, 3456);

insert into post(id, type, title, owner, d_type, cover_picture, text, views, likes)
values (103, 3, 'Which is the best body lotion for dry skin?', 1, 'discussion', 'discuss.jpg',
        'I have shortlisted the following lotions', 12345, 126);

insert into video_comment(id, comment, post_id, date_posted, user_id, active)
values (11001, 'I liked all the shades in this series', 101, now(), 3, true);
insert into video_comment(id, comment, post_id, date_posted, user_id, active)
values (11002, 'Can you also do the video review for LOreal as well', 101, now(), 1, true);
insert into video_comment(id, comment, post_id, date_posted, user_id, active)
values (11003, 'Enjoyed this review a lot great job', 101, now(), 3, true);
insert into video_comment(id, comment, post_id, date_posted, user_id, active)
values (11004, 'What a great honest review. really liked it.', 101, now(), 1, true);


insert into article_comment
values (201, 'This is a very nice article', 102, now(), 0, 3, true);
insert into article_comment
values (202, 'I think you should also mention the skin color', 102, now(), 0, 3, true);
insert into article_comment
values (203, 'Yes, thanks for the suggestion', 102, now(), 0, 2, true);
insert into article_comment
values (204, 'Really enjoyed the article', 102, now(), 0, 1, true);

insert into collection(id, name, about, visibility, owner)
values (1001, 'Lipsticks', 'all about lipsticks', 2, 3);
insert into collection(id, name, about, visibility, owner)
values (1002, 'Party Dresses', 'It is about party dresses', 2, 2);


insert into collection_video(collection_id, video_id)
values (1001, 1000001);
insert into collection_video(collection_id, video_id)
values (1001, 1000002);

insert into collection_article(collection_id, article_id)
values (1001, 2000001);
insert into collection_article(collection_id, article_id)
values (1001, 2000002);

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
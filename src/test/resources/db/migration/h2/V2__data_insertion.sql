-- passwords were encoded with bCryptEncoders https://bcrypt-generator.com/
insert into user_account(email, password) values ('talk_to_priyanka@gmail.com', '$2y$12$bDHT1s2E8pH7if.XQr/14urBafxek/62kxe9R.omoloGhh9XnwdbW');
insert into user_account(email, password) values ('priya.singh@gmail.com', '$2y$12$nfSQJzgpJ3gTu.CczB6BiuceNqA6niFi7EX03p4Ep3205kL5I2pDy');
insert into user_account(email, password) values ('sonam99@gmail.com', '$2y$12$nkEeE1P.hWfg1iqhp8JWOea9F7lEEzBi07ZdGs1ujrVJM5YVYnQqi');

insert into user_profile(username, mobile, city, gender) values ('priyanka11', 9876543210, 'Indore', 'F');
insert into user_profile(username, mobile, city, gender) values ('priya21', 8765432109, 'Pune', 'F');
insert into user_profile(username, mobile, city, gender) values ('sonam31', 7654321098, 'Jaipur', 'F');

insert into discussion_topic(topic, user_id) values ('Are Loreal lip colors better than Lakme or is it the other way around', 5);
insert into discussion_topic_activity(discussion_topic_id, date_posted, date_last_active, views, likes, replies) values(1, now(), now(), 15, 12, 1);

insert into discussion_reply(discussion_topic_id, reply, user_id) values (1, 'Yes, Loreal is better than Lakme', 4);
insert into discussion_reply_activity(discussion_reply_id, date_posted, likes) values(1, now(), 2);

insert into discussion_reply(discussion_topic_id, reply, user_id, active) values (1, 'No, Loreal is better than Lakme', 2, false);
insert into discussion_reply_activity(discussion_reply_id, date_posted, likes) values(2, now(), 2);

insert into POST_TYPE(post_type_id, post_type) values (1, 'Topic');
insert into POST_TYPE(post_type_id, post_type) values (2, 'Reply');
insert into POST_TYPE(post_type_id, post_type) values (3, 'Comment');
insert into POST_TYPE(post_type_id, post_type) values (4, 'Text Review');
insert into POST_TYPE(post_type_id, post_type) values (5, 'Video Review');
insert into POST_TYPE(post_type_id, post_type) values (6, 'Text Guide');
insert into POST_TYPE(post_type_id, post_type) values (7, 'Video Guide');

insert into discussion_topic_user_like(user_id, topic_id) values(5, 5);
insert into discussion_topic_user_like(user_id, topic_id) values(5, 1);
insert into discussion_topic_user_like(user_id, topic_id) values(6, 6);

insert into discussion_reply_user_like(user_id, reply_id) values(7, 7);
insert into discussion_reply_user_like(user_id, reply_id) values(5, 1);
insert into discussion_reply_user_like(user_id, reply_id) values(8, 8);
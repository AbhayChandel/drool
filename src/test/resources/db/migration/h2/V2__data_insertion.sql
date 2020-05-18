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


insert into discussion_topic(topic, user_id, date_posted, date_last_active, views, likes, replies)
values ('Are Loreal lip colors better than Lakme or is it the other way around', 1, now(), now(), 154564, 12754, 234);

insert into discussion_reply(discussion_topic_id, reply, user_id, date_posted, likes)
values (1, 'Yes, Loreal is better than Lakme', 3, now(), 2457);

insert into discussion_reply(discussion_topic_id, reply, user_id, active, date_posted, likes)
values (1, 'No, Loreal is better than Lakme', 2, false, now(), 2);

insert into discussion_reply(discussion_topic_id, reply, user_id, active, date_posted, likes)
values (1, 'It is not a straight answer', 1, true, now(), 2);



insert into discussion_topic(topic, user_id, date_posted, date_last_active, views, likes, replies)
values ('How to get fairer skin', 1, now(), now(), 154564, 4999, 234);

insert into discussion_reply(discussion_topic_id, reply, user_id, active, date_posted, likes)
values (2, 'Try natural remedies. Do not try cosmetics.', 1, true, now(), 99);

insert into discussion_reply(discussion_topic_id, reply, user_id, active, date_posted, likes)
values (2, 'Use some natual herbal creams', 1, true, now(), 2000);



insert into discussion_topic(topic, user_id, date_posted, date_last_active, views, likes, replies)
values ('How to apply conditioner', 1, now(), now(), 154564, 13000, 234);

insert into POST_TYPE(post_type_id, post_type, descritpion)
values (1, 'dit', 'Discussion topic');
insert into POST_TYPE(post_type_id, post_type, descritpion)
values (2, 'dir', 'Discussion reply');
insert into POST_TYPE(post_type_id, post_type, descritpion)
values (3, 'vdc', 'Video comment');
insert into POST_TYPE(post_type_id, post_type, descritpion)
values (4, 'txr', 'Text Review');
insert into POST_TYPE(post_type_id, post_type, descritpion)
values (5, 'vdr', 'Video Review');
insert into POST_TYPE(post_type_id, post_type, descritpion)
values (6, 'txg', 'Text Guide');
insert into POST_TYPE(post_type_id, post_type, descritpion)
values (7, 'vdg', 'Video Guide');

insert into discussion_topic_user_like(user_id, topic_id)
values (5, 5);
insert into discussion_topic_user_like(user_id, topic_id)
values (5, 1);
insert into discussion_topic_user_like(user_id, topic_id)
values (6, 6);
insert into discussion_topic_user_like(user_id, topic_id)
values (2, 3);

insert into discussion_reply_user_like(user_id, reply_id)
values (7, 7);
insert into discussion_reply_user_like(user_id, reply_id)
values (5, 1);
insert into discussion_reply_user_like(user_id, reply_id)
values (8, 8);
insert into discussion_reply_user_like(user_id, reply_id)
values (5, 5);
--passwords were encoded with bCryptEncoders https://bcrypt-generator.com/
insert into user_account(email, password)
values ('shabana@gmail.com', '$2y$12$RTmDCrxkhq.XQkFk6Aw85uC5pBCMIjORk8qutEtwtACl7a6R2cB4G');
insert into user_account(email, password)
values ('priya.singh@gmail.com', '$2y$12$nfSQJzgpJ3gTu.CczB6BiuceNqA6niFi7EX03p4Ep3205kL5I2pDy');
insert into user_account(email, password)
values ('sonam99@gmail.com', '$2y$12$nkEeE1P.hWfg1iqhp8JWOea9F7lEEzBi07ZdGs1ujrVJM5YVYnQqi');

insert into user_profile(user_account_id, username, mobile, city, gender)
values (1, 'shabanastyle', 9876543210, 'Lucknow', 'F');
insert into user_profile(user_account_id, username, mobile, city, gender)
values (2, 'priya21', 8765432109, 'Pune', 'F');
insert into user_profile(user_account_id, username, mobile, city, gender)
values (3, 'sonam31', 7654321098, 'Jaipur', 'F');

insert into discussion_topic(topic, user_id, date_posted, date_last_active, views, likes, replies)
values ('Are Loreal lip colors better than Lakme or is it the other way around', 1, now(), now(), 15, 12, 2);

insert into discussion_reply(discussion_topic_id, reply, user_id, date_posted, likes)
values (1, 'Yes, Loreal is better than Lakme', 3, now(), 2);

insert into discussion_reply(discussion_topic_id, reply, user_id, active, date_posted, likes)
values (1, 'No, I dont think Loreal products are any better than Lakme. It is just the hype', 2, false, now(), 4);

insert into discussion_reply(discussion_topic_id, reply, user_id, date_posted, likes)
values (1,
        'It depends on the products you are looking at. For lip colors loreal is better. But then for other things Lakme is better',
        1, now(), 3);

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

insert into discussion_reply_user_like(user_id, reply_id)
values (7, 7);
insert into discussion_reply_user_like(user_id, reply_id)
values (5, 1);
insert into discussion_reply_user_like(user_id, reply_id)
values (8, 8);
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


insert into post(id, type, title, owner, d_type, source_video_id, text)
values (101, 1, 'Lakme 9to5 Lip Color', 2, 'video', 'xsztiz', 'This is aideo review for Lakme 9to5');

insert into post(id, type, title, owner, d_type, cover_picture, text, views, likes)
values (102, 2, 'How to choose the right shade', 3, 'article', 'xsztiz.jpg',
        'This is an article about picking the right lip color shade', 456765, 3456);

insert into article_comment
values (201, 'This is a very nice article', 102, now(), 0, 3, true);
insert into article_comment
values (202, 'I think you should also mention the skin color', 102, now(), 0, 3, true);
insert into article_comment
values (203, 'Yes, thanks for the suggestion', 102, now(), 0, 2, true);
insert into article_comment
values (204, 'Really enjoyed the article', 102, now(), 0, 1, true);

insert into collection(name, about, visibility, owner)
values ('Party Dresses', 'It is about party dresses', 2, 2);


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

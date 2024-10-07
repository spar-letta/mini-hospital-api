-- users
insert into public.users (id, public_id, user_name, deleted, contact_email, first_name) values (1,'7a40fd66-36a6-4487-9509-e9b12a61bff9','testuser',false,'admin@gmail.com','peterKason');
insert into public.users (id, public_id, user_name, deleted, contact_email, first_name) values (2,'2d7c44c0-3e3c-415d-b4e3-feb799f41e04','Cao',false,'xueqin@gmail.com','Xueqin');
insert into public.users (id, public_id, user_name, deleted, contact_email, first_name) values (3,'054e1a02-9cd4-476b-bb4b-22fda2ef02df','Charles',false,'dickens@gmail.com','Dickens');
insert into public.users (id, public_id, user_name, deleted, contact_email, first_name) values (4,'7ba79c3b-f16b-44da-9696-0174e36d7021','Agatha',false,'christie@gmail.com','Christie');

insert into hospital_v1.departments(id, public_id, deleted, name, description) values (100, '4616953c-cb4e-4536-b3ff-0132aea01995', false, 'departName', 'description of the department');
insert into hospital_v1.departments(id, public_id, deleted, name, description) values (101, 'ad1c9025-136f-44c0-8233-942b2a1c79e2', false, 'departName1', 'description of the department1');

insert into hospital_v1.patient(id, public_id, deleted, national_id, first_name, last_name) values (120, '78196df3-67de-497d-8165-6d20fac4a76a', false, '1299900', 'kevin', 'patient1');


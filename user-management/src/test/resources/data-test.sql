
insert into public.users (change_password, public_id, date_created, date_modified, created_by, modified_by, deleted, first_name, last_name, other_name, user_name, password, contact_email, contact_phonenumber, verified_email, verified_phonenumber, status,id) values (false, 'e76345b6-8c1e-4aa2-9512-39467e6ec336', '2023-01-01 12:00:00', '2023-01-01 12:00:00', 'admin', 'admin', false, 'John', 'Doe', 'Jr.', 'john.doe', 'password123', 'john.doe@example.com', '1234567890', true, false, 'Active', 51);
insert into public.users (change_password,contact_email,contact_phonenumber,created_by,date_created,date_modified,deleted,first_name,last_name,modified_by,other_name,password,public_id,status,user_name,verified_email,verified_phonenumber,id) values (false,'admin@gmail.com','0708461561',null,now(),null,false,'admin','admin1',null,null,'$2a$10$JH61hCfSSk2jhXTGvjehtuZ8LmzJ53xE2hdgX5fkt/WPP3wYIkOJm','bb874ce2-dc46-4f11-8915-c1d644f236df','Active','123456',false,false,100);


insert into public.roles (created_by,date_created,date_modified,deleted,description,modified_by,name,public_id,id) values ('Admin',now(),null,false,'role_admin',null,'ROLE_ADMIN','fb874ce2-dc46-4f11-8915-c1d644f236dd',100);
insert into public.roles (created_by,date_created,date_modified,deleted,description,modified_by,name,public_id,id) values ('Admin', now(),null,false,'role_secretary',null,'ROLE_SECRETARY','ab874ce2-dc46-4f11-8915-c1d644f236da',101);

insert into public.user_roles(user_id_fk, role_id_fk) values (100, 100);

insert into public.privileges(id, public_id, deleted, name, role_id) values (12, 'bb874ce2-dc46-4f11-8915-c1d644f236df', false, 'READ_USER', 100);
insert into public.privileges(id, public_id, deleted, name, role_id) values (13, 'ab874ce2-dc46-4f11-8915-c1d644f236d4', false, 'READ_ROLES', 100);
insert into public.privileges(id, public_id, deleted, name, role_id) values (14, 'db874ce2-dc46-4f11-8915-c1d644f236d1', false, 'USER_UPDATE_PRIVILEGE', null);
insert into public.privileges(id, public_id, deleted, name, role_id) values (15, 'ec06cecc-9979-4d1f-ba7d-640506135227', false, 'ASSIGN_PRIVILEGE_TO_ROLE', 100);
insert into public.privileges(id, public_id, deleted, name, role_id) values (16, 'ac3976ff-4d9e-4ad8-a19c-1c55cdf4f8be', false, 'CREATE_ROLE', 100);
insert into public.privileges(id, public_id, deleted, name, role_id) values (17, 'a7c6a7f9-3d9e-4280-b02a-8c2ce912b33f', false, 'CREATE_PRIVILEGE', 100);
insert into public.privileges(id, public_id, deleted, name, role_id) values (18, '40ce4097-c5bd-4343-a8d7-88490622d035', false, 'READ_PRIVILEGES', 100);


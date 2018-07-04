BEGIN TRANSACTION;

INSERT INTO user_
	(login, first_name, last_name, email, type)
VALUES (
	'specified login',
	'sample first name',
	'sample last name',
	'sample email',
	'specified type');


INSERT INTO codecooler
	(user_login, class_id, level_id)
VALUES (
	'same specified login',
	'sample class id',
	'sample level id');

COMMIT;




select * from user_
join codecooler
ON user_.login = codecooler.user_login;

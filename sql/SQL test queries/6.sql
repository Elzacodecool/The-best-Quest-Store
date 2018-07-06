INSERT INTO appuser
	(login, first_name, last_name, email, appuser_type)
VALUES (
	'specified login',
	'sample first name',
	'sample last name',
	'sample email',
	'specified type');


INSERT INTO codecooler
	(appuser_login, classroom_id, degree_id)
VALUES (
	'same specified login',
	'sample class id',
	'sample level id');


select * from appuser
join codecooler
ON appuser.login = codecooler.appuser_login;

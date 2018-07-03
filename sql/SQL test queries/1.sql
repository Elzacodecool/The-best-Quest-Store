BEGIN TRANSACTION;

INSERT INTO user 
(login, first_name, last_name, email, type)
VALUES ('sample login',
	'sample name', 
	'sample surname',
	'sample email',
	'mentor');

INSERT INTO mentor 
(user_login, class_id)
VALUES ('same sample login',
	'class_id');

COMMIT;

INSERT INTO appuser
(login, first_name, last_name, email, appuser_type)
VALUES ('sample login',
	'sample name', 
	'sample surname',
	'sample email',
	'mentor');

INSERT INTO mentor 
(appuser_login)
VALUES ('same sample login');

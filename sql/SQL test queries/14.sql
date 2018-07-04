SELECT first_name, last_name, wallet FROM user_
INNER JOIN codecooler
ON codecooler.user_login = user_.login;




SELECT first_name, last_name, wallet, name, purchase_date, use_date FROM codecooler_artifact
INNER JOIN codecooler
	ON codecooler.id = codecooler_artifact.codecooler_id
INNER JOIN artifact
	ON codecooler_artifact.artifact_id = artifact.id
INNER JOIN user_ 
	ON user_.login = codecooler.user_login;


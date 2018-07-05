SELECT first_name, last_name, wallet FROM appuser
INNER JOIN codecooler
ON codecooler.appuser_login = user_.login;




SELECT first_name, last_name, wallet, artifact_name, purchase_date, use_date FROM codecooler_artifact
INNER JOIN codecooler
	ON codecooler.id = codecooler_artifact.codecooler_id
INNER JOIN artifact
	ON codecooler_artifact.artifact_id = artifact.id
INNER JOIN appuser 
	ON appuser.login = codecooler.appuser_login;


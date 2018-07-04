INSERT INTO codecooler_artifact
(codecooler_id, artifact_id, purchase_date, use_date)
VALUES (selected_codecooler_id,
	selected_artifact_id,
	selected_purchase_date,
	selected_use_date);


SELECT * FROM codecooler_artifact INNER JOIN artifact
ON artifact.id = codecooler_artifact.artifact_id
WHERE codecooler_id = 'specified codecooler id';


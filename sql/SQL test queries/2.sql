BEGIN TRANSACTION;

INSERT INTO class 
	(name)
VALUES ('sample class name');

UPDATE mentor
SET class_id = 'created class id'
WHERE first_name = 'mentor name'
AND last_name = 'mentor surname';

COMMIT;

SELECT * FROM mentor
WHERE id = sample_id;



SELECT first_name, last_name, class_id FROM codecooler
JOIN user
ON user.login = codecooler.user_login
WHERE class_id IN
(SELECT class_id FROM mentor 
JOIN mentor_class
ON mentor.id = mentor_class.mentor_id
WHERE user_login = sample_login;)

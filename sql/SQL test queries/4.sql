SELECT * FROM mentor
WHERE id = sample_id;




SELECT first_name, last_name, class_id FROM codecooler
JOIN user
ON user.login = codecooler.user_login

WHERE class_id IN
(SELECT class_id FROM mentor_class 
WHERE mentor_id = Specified_mentor_ID)

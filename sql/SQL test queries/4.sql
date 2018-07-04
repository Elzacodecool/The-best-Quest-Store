SELECT * FROM mentor
JOIN user_
ON user_.login = mentor.user_login
WHERE id = selected mentor_id;



SELECT first_name, last_name, class_id FROM codecooler
JOIN user_
ON user_.login = codecooler.user_login
WHERE class_id IN
(SELECT class_id FROM mentor_class 
WHERE mentor_id = selected_mentor_id)

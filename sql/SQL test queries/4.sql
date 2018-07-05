SELECT * FROM mentor
JOIN appuser
ON appuser.login = mentor.appuser_login
WHERE id = selected_mentor_id;



SELECT first_name, last_name, classroom_id FROM codecooler
JOIN appuser
ON appuser.login = codecooler.appuser_login
WHERE class_id IN
(SELECT classroom_id FROM mentor_classroom 
WHERE mentor_id = selected_mentor_id)

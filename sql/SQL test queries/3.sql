UPDATE user_
SET    email = 'new_email'
FROM   mentor
WHERE  user_.login = mentor.user_login
       AND first_name = 'Dominik'
       AND last_name = 'Starzyk';


UPDATE mentor_class
SET mentor_id = selected_mentor_id
WHERE class_id = selected_class_id;



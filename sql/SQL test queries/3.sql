UPDATE appuser
SET    email = 'new_email'
FROM   mentor
WHERE  appuser.login = mentor.appuser_login
       AND first_name = 'Dominik'
       AND last_name = 'Starzyk';


UPDATE mentor_classroom
SET mentor_id = selected_mentor_id
WHERE classroom_id = selected_classroom_id;



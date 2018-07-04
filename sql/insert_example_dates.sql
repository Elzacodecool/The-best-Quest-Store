BEGIN TRANSACTION;
 INSERT INTO Class_
(name)
VALUES
('python'),
('java');

INSERT INTO Level_
(name, min_coolcoins)
VALUES
('python_level', 200),
('java_level', 400);

INSERT INTO User_
(login, first_name, last_name, email, type)
VALUES
('adamszmidt', 'adam', 'szmidt', 'aszmidt92@gmail.com', 'codecooler'),
('marekgrzybek', 'marek', 'grzybek', 'marekg@gmail.com', 'mentor'),
('przemyslawpolczak', 'przemyslaw', 'polczak', 'przemyslaw.polczak@gmail.com', 'codecooler');
COMMIT;
END TRANSACTION;
BEGIN TRANSACTION;
INSERT INTO Codecooler
(user_login, class_id, level_id )
VALUES
('adamszmidt', 1, 1),
('przemyslawpolczak', 1,1);


INSERT INTO Mentor
(user_login)
VALUES
('marekgrzybek');

INSERT INTO Mentor_class
(mentor_id, class_id)
VALUES
(1, 1);





INSERT INTO Artifact
(name, description, price, category)
VALUES
('Combat training', 'Private mentoring', 50, 'I'),
('Sanctuary', 'You can spend a day in home office', 300, 'I'),
('Time Travel', 'Extend SI week assignment deadline by one day', 500, 'I'),
('Tome of knowledge', 'Extra material for the current topic',  500, 'T'),
('Summon Code Elemental','Mentor joins a students'' team for a one hour', 1000, 'T'),
('Circle of Sorcery', '60 min workshop by a mentor(s) of the chosen topic', 1000, 'T');

COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
INSERT INTO Codecooler_artifact
(codecooler_id, artifact_id, purchase_date, use_date)
VALUES
(1, 1, '2018-07-03 08:44:44', '2018-07-04 11:44:44' );

INSERT INTO Quest
(name, description, value, category)
VALUES
('Exploring a dungeon', 'Finishing a Teamwork week', 100, 'Basic'),
('Solving the magic puzzle', 'Finishing an SI assignment', 100, 'Basic'),
('Spot trap', 'Spot a major mistake in the assignment', 50, 'Extra');
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
INSERT INTO Codecooler_quest
(codecooler_id, quest_id)
VALUES
(1, 1);

INSERT INTO Team
(artefact_id, leader_id)
VALUES
(1, 1);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;

INSERT INTO Codecooler_team
(codecooler_id, team_id, coolcoins)
VALUES
(1,1, 100);


COMMIT;
END TRANSACTION;












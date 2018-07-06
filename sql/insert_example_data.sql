BEGIN;
INSERT INTO Classroom
(classroom_name)
VALUES
('python'),
('java');

INSERT INTO Degree
(degree_name, min_coolcoins)
VALUES
('beginner', 200),
('master', 400),
('expert', 800),
('jez', 2000);

INSERT INTO Appuser
(login, first_name, last_name, email, appuser_type)
VALUES
('adamszmidt', 'adam', 'szmidt', 'aszmidt92@gmail.com', 'codecooler'),
('marekgrzybek', 'marek', 'grzybek', 'marekg@gmail.com', 'mentor'),
('przemyslawpolczak', 'przemyslaw', 'polczak', 'przemyslaw.polczak@gmail.com', 'codecooler'),
('jerzyjerzowski', 'jerzy', 'jerzowski', 'jez123@gmail.com', 'admin');

COMMIT;


BEGIN;
INSERT INTO Codecooler
(appuser_login, classroom_id, earnedCoolcoins )
VALUES
('adamszmidt', 1, 1),
('przemyslawpolczak', 1, 33);

INSERT INTO Mentor
(Appuser_login)
VALUES
('marekgrzybek');

INSERT INTO Mentor_classroom
(mentor_id, classroom_id)
VALUES
(1, 1);

INSERT INTO Artifact
(artifact_name, artifact_description, cost, category)
VALUES
('Combat training', 'Private mentoring', 50, 'I'),
('Sanctuary', 'You can spend a day in home office', 300, 'I'),
('Time Travel', 'Extend SI week assignment deadline by one day', 500, 'I'),
('Tome of knowledge', 'Extra material for the current topic',  500, 'T'),
('Summon Code Elemental','Mentor joins a students'' team for a one hour', 1000, 'T'),
('Circle of Sorcery', '60 min workshop by a mentor(s) of the chosen topic', 1000, 'T');

COMMIT;

BEGIN;
INSERT INTO Codecooler_artifact
(codecooler_id, artifact_id, purchase_date, use_date)
VALUES
(1, 1, '2018-07-03 08:44:44', '2018-07-04 11:44:44' );

INSERT INTO Quest
(quest_name, quest_description, prize, category)
VALUES
('Exploring a dungeon', 'Finishing a Teamwork week', 100, 'Basic'),
('Solving the magic puzzle', 'Finishing an SI assignment', 100, 'Basic'),
('Spot trap', 'Spot a major mistake in the assignment', 50, 'Extra');

COMMIT;


BEGIN;
INSERT INTO Codecooler_quest
(codecooler_id, quest_id)
VALUES
(1, 1);

INSERT INTO Team
(artefact_id, leader_id)
VALUES
(1, 1);
COMMIT;


BEGIN;
INSERT INTO Codecooler_team
(codecooler_id, team_id, coolcoins)
VALUES
(1,1, 100);

COMMIT;

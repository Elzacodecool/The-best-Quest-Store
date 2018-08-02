BEGIN;
INSERT INTO classroom
(classroom_name)
VALUES
('2017.2 Krk'),
('2017.3 Krk'),
('2018.1 Krk');

INSERT INTO degree
(degree_name,  min_earned_coolcoins)
VALUES
('Newbie', 0),
('Intermediate', 50),
('Experienced', 150),
('Master', 300);

INSERT INTO appuser
(login, password, first_name, last_name, email, appuser_type)
VALUES
('codecooler', '123', 'Jerzy', 'Jerzowski', 'jez123@gmail.com', 'admin'),
('jerzyjerzowski', 'a', 'Jerzy', 'Jerzowski', 'jez123@gmail.com', 'admin'),
('marekgrzybek', 'm',  'Marek', 'Grzybek', 'marekg@gmail.com', 'mentor'),
('ds', 'm',  'Dominik', 'Starzyk', 'dominik@gmail.com', 'mentor'),
('ak', 'm',  'Agnieszka', 'Koszany', 'agnieszka@gmail.com', 'mentor'),
('kg', 'm',  'Konrad', 'Gadzina', 'konrad@gmail.com', 'mentor'),
('przemyslawpolczak', 'c', 'Przemyslaw', 'polczak', 'przemyslaw.polczak@gmail.com', 'codecooler'),
('angel', 'c', 'Angelika', 'Nieduziak', 'a.n@gmail.com', 'codecooler'),
('eliza', 'c', 'Eliza', 'Golec', 'eliza@gmail.com', 'codecooler'),
('adamszmidt', 'c', 'Adam', 'Szmidt', 'aszmidt92@gmail.com', 'codecooler'),
('olka', 'c', 'Ola', 'Kowalska', 'kowalska_ola.@gmail.com', 'codecooler');

COMMIT;


BEGIN;
INSERT INTO codecooler
(appuser_login, classroom_id, earned_coolcoins, wallet)
VALUES
('angel', 1, 44, 1),
('eliza', 1, 160, 40),
('adamszmidt', 1, 66, 5),
('olka', 3, 2, 1),
('przemyslawpolczak', 1, 301, 22);

INSERT INTO mentor
(Appuser_login)
VALUES
('marekgrzybek'),
('ds'),
('ak'),
('kg');

INSERT INTO mentor_classroom
(mentor_id, classroom_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 3),
(3, 2),
(4, 2),
(4, 3);

INSERT INTO artifact
(artifact_name, artifact_description, cost, category)
VALUES
('Day off', 'Free day of codecool', 20, 'solo'),
('Private mentoring', 'Private consultation with mentor', 2, 'solo'),
('Additional day', 'Extend SI week assignment deadline by one day', 15, 'solo'),
('Extra knowledge', 'Extra material for the current topic',  30, 'team'),
('Support in TW','Mentor joins a codecooler team for one hour', 24, 'team'),
('Mentor transform', 'Mentor have to dress up', 30, 'team');

COMMIT;

BEGIN;
INSERT INTO codecooler_artifact
(codecooler_id, artifact_id, purchase_date, use_date)
VALUES
(1, 1, 100, 100),
(1, 3, 100, 100);

INSERT INTO codecooler_artifact
(codecooler_id, artifact_id, purchase_date)
VALUES
(1, 2, 100),
(1, 3, 100);

INSERT INTO quest
(quest_name, quest_description, prize, category)
VALUES
('Complete TW', 'Complete your TW in time', 1, 'Basic'),
('Complete SI', 'Complete your SI in time', 1, 'Basic'),
('Workshop', 'Help to ornanize workshop for fellow students', 10, 'Extra'),
('Demo', 'Presentation on demo day', 10, 'Extra'),
('Company interview', 'Going to a company interview', 5, 'Extra');

COMMIT;


BEGIN;

INSERT INTO codecooler_quest
(codecooler_id, quest_id, mark_date)
VALUES
(1, 2, 100),
(3, 1, 100),
(3, 2, 100);

INSERT INTO codecooler_quest
(codecooler_id, quest_id)
VALUES
(1, 1),
(1, 3),
(2, 2);

INSERT INTO team
(team_name, artifact_id, leader_id)
VALUES
('coolersi', 3, 2),
('new_ones', 3, 4),
('coolersi', 4, 1),
('advanced', 5, 2);
COMMIT;


BEGIN;
INSERT INTO codecooler_team
(codecooler_id, team_id, coolcoins)
VALUES
(2, 1, 2),
(4, 2, 3),
(1, 3, 5),
(2, 4, 1),
(4, 1, 4),
(5, 1, 2);

COMMIT;

UPDATE codecooler
SET wallet = wallet + 
(SELECT value FROM codecooler_quest
JOIN quest
ON codecooler_quest.quest_id = quest.id
WHERE codecooler_quest.id = selected_done_quest_id)
WHERE id = 
(SELECT codecooler_id FROM codecooler_quest
WHERE id = selected_done_quest_id);
UPDATE codecooler_quest
SET mark_date = current_date
WHERE id = selected_done_quest_id;



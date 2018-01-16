SELECT * FROM user_t;

UPDATE user_t SET first_name = 'test' WHERE id = 9;

SELECT id, condition, description, date_add, dead_line, user_id FROM task;
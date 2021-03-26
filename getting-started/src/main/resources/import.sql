INSERT INTO gift(id, name) VALUES (1, 'Cherry');
INSERT INTO gift(id, name) VALUES (2, 'Apple');
INSERT INTO gift(id, name) VALUES (3, 'Banana');

drop sequence gift_id_seq;
CREATE SEQUENCE gift_id_seq START 101;

-- enables debezium event with 'before' part for update/delete
ALTER TABLE gift REPLICA IDENTITY FULL;
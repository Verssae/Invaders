CREATE TABLE IF NOT EXISTS Score (
                                     id VARCHAR(255),
                                     score INT NOT NULL,
                                     difficulty INT NOT NULL,
                                     create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     PRIMARY KEY (id, create_at),
                                     FOREIGN KEY (id) REFERENCES Client(id) on delete cascade
);

CREATE OR REPLACE FUNCTION update_high_score()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.score > (SELECT COALESCE(high_score[NEW.difficulty], 0) FROM Client WHERE id = NEW.id) THEN
        UPDATE Client SET high_score[NEW.difficulty] = NEW.score, update_at = CURRENT_TIMESTAMP
        WHERE id = NEW.id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_high_score AFTER INSERT ON score
    FOR EACH ROW EXECUTE FUNCTION update_high_score();
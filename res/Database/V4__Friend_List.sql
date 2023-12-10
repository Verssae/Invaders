CREATE TABLE IF NOT EXISTS Friend_List (
                                           user_id VARCHAR(255) NOT NULL,
                                           friend_id VARCHAR(255) NOT NULL,
                                           create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           PRIMARY KEY (user_id, friend_id),
                                           FOREIGN KEY (user_id) REFERENCES Client(id) ON DELETE CASCADE,
                                           FOREIGN KEY (friend_id) REFERENCES Client(id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION friend_bidirectional_check()
    RETURNS TRIGGER AS $$
BEGIN
    -- Friend_List bidirectional check
    IF NOT EXISTS (
        SELECT 1 FROM Friend_List
        WHERE user_id = NEW.friend_id AND friend_id = NEW.user_id
    )
        -- If not bidirectional, add reciprocal  value
    THEN
        INSERT INTO Friend_List (user_id, friend_id)
        VALUES (NEW.friend_id, NEW.user_id);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER friend_bidirectional_check
    AFTER INSERT ON Friend_List
    FOR EACH ROW EXECUTE FUNCTION friend_bidirectional_check();
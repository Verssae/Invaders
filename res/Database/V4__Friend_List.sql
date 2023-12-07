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
    -- Friend_List 양방향성 확인
    IF NOT EXISTS (
        SELECT 1 FROM Friend_List
        WHERE user_id = NEW.friend_id AND friend_id = NEW.user_id
    )
        -- 반대 방향의 Friend_List가 없을 시 추가
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
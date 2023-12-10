--if is_online value become false, update last_online_time
CREATE OR REPLACE FUNCTION update_last_online_time()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.is_online = FALSE THEN
        NEW.last_online_time = CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER last_online_time_trigger
    BEFORE UPDATE OF is_online ON Client
    FOR EACH ROW
EXECUTE FUNCTION update_last_online_time();
CREATE TABLE IF NOT EXISTS Client(
                                     id VARCHAR(255) PRIMARY KEY,
                                     password VARCHAR(255) NOT NULL,
                                     client_name VARCHAR(255) NOT NULL,
                                     country VARCHAR(255) NOT NULL,
                                     high_score INT[],
                                     create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
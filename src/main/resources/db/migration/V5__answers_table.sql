CREATE TABLE answers(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    question_id BIGINT UNSIGNED NOT NULL,
    answer_index VARCHAR(1),
    description VARCHAR(250),
    PRIMARY KEY (id)
);
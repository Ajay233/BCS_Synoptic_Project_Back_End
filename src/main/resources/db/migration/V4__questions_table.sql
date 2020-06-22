CREATE TABLE questions(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    quiz_id BIGINT UNSIGNED NOT NULL,
    question_number INT UNSIGNED NOT NULL,
    description VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
);
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age INT NOT NULL CHECK(age >= 0),
    address VARCHAR(100) NOT NULL
);
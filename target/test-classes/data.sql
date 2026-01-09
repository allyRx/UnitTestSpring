
-- Create person table
CREATE TABLE persons
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(50),
    city            VARCHAR(50),
    phone_number    VARCHAR(50)
);


-- insert data
INSERT INTO persons(name, city, phone_number)
VALUES('John DOe', 'Madagascar','375008952'),
      ('Doe Chris','Fianarantsoa', '457896221');


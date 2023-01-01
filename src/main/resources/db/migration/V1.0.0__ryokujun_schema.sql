CREATE DATABASE IF NOT EXISTS ryokujun_db;
USE ryokujun_db;
CREATE TABLE IF NOT EXISTS users
(
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR (255),
   email VARCHAR (255),
   pass VARCHAR (255),
   description VARCHAR (255),
   status INT,
   created_at DATETIME,
   updated_at DATETIME,
   PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS albums
(
   id INT NOT NULL AUTO_INCREMENT,
   user_id INT,
   title VARCHAR(255),
   alive boolean,
   start_date DATETIME,
   end_date DATETIME,
   created_at DATETIME,
   updated_at DATETIME,
   PRIMARY KEY (id),
   CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS follows
(
   id INT NOT NULL AUTO_INCREMENT,
   followee_id INT,
   follwer_id INT,
   start_date DATETIME,
   end_date DATETIME,
   created_at DATETIME,
   updated_at DATETIME,
   PRIMARY KEY (id),
   CONSTRAINT FOREIGN KEY (followee_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (follwer_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS categories
(
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR (255),
   PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS plants
(
   id INT NOT NULL AUTO_INCREMENT,
   category_id INT,
   user_id INT,
   album_id INT,
   title VARCHAR (255),
   description VARCHAR (255),
   image_url VARCHAR (255),
   alive boolean,
   status INT,
   created_at DATETIME,
   updated_at DATETIME,
   PRIMARY KEY (id),
   CONSTRAINT FOREIGN KEY (album_id) REFERENCES albums (id) ON DELETE RESTRICT ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS comments
(
   id INT NOT NULL AUTO_INCREMENT,
   plant_id INT,
   user_id INT,
   content VARCHAR (255),
   created_at DATETIME,
   updated_at DATETIME,
   PRIMARY KEY (id),
   CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (plant_id) REFERENCES plants (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS likes
(
   id INT NOT NULL AUTO_INCREMENT,
   plant_id INT,
   comment_id INT,
   created_at DATETIME,
   updated_at DATETIME,
   PRIMARY KEY (id),
   CONSTRAINT FOREIGN KEY (plant_id) REFERENCES plants (id) ON DELETE RESTRICT ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (comment_id) REFERENCES comments (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS plant_category
(
   plant_id INT,
   category_id INT,
   created_at DATETIME,
   updated_at DATETIME,
   CONSTRAINT FOREIGN KEY (plant_id) REFERENCES plants (id) ON DELETE RESTRICT ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS activities
(
   id INT NOT NULL AUTO_INCREMENT,
   user_id INT,
   comments_id INT,
   plant_id INT,
   activity_type INT,
   created_at DATETIME,
   updated_at DATETIME,
   PRIMARY KEY (id),
   CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (plant_id) REFERENCES plants (id) ON DELETE RESTRICT ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (comments_id) REFERENCES comments (id) ON DELETE RESTRICT ON UPDATE CASCADE
);
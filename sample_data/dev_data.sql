USE botapeer_db;

INSERT INTO users(name, email, password, description, status) values("okuda", "sts868dti@dti.ne.jp", "$2a$12$GdY.dZn7wjfLnlqURlaacObGfp9.i0UJ7EQaYbNr4AtAuxVi5nY12", "初めまして！園芸初心者ですがこれから頑張って投稿しようと思います。コメントもお気軽にどうぞ！", 1);
INSERT INTO users(name, email, password, description, status) values("oosaki", "masaru1972@example.com", "$2a$12$GdY.dZn7wjfLnlqURlaacObGfp9.i0UJ7EQaYbNr4AtAuxVi5nY12", "自宅で野菜を栽培して10年になります。最近畑を購入しました。", 1);
INSERT INTO users(name, email, password, description, status) values("okumura", "rmko20050413@web.ad.jp", "$2a$12$GdY.dZn7wjfLnlqURlaacObGfp9.i0UJ7EQaYbNr4AtAuxVi5nY12",  "農学部3年生。ゼミでは細菌の研究をしています。植物病理学に関心があります。", 1);
INSERT INTO users(name, email, password, description, status) values("takeuti", "yumi3119@dti.ad.jp", "$2a$12$GdY.dZn7wjfLnlqURlaacObGfp9.i0UJ7EQaYbNr4AtAuxVi5nY12", "自宅で野菜を栽培して10年になります。最近畑を購入しました。", 1);
INSERT INTO users(name, email, password, description, status) values("narumi", "utiumi81@example.jp", "$2a$12$GdY.dZn7wjfLnlqURlaacObGfp9.i0UJ7EQaYbNr4AtAuxVi5nY12", "自宅で野菜を栽培して10年になります。最近畑を購入しました。", 1);

INSERT INTO plant_records(user_id, title, alive, place_id, created_at, updated_at) values(1, "バラの生育記録", 1, 1, "2023-02-17 15:46:35", "2023-02-17 15:46:35");

INSERT INTO posts(plant_record_id, title, article, created_at, updated_at) values(1, "初めての投稿", "初めての投稿です。よろしくお願いします。", "2023-02-17 15:46:35", "2023-02-17 15:46:35");
INSERT INTO posts(plant_record_id, title, article, created_at, updated_at) values(1, "芽が出ました", "徐々に芽がでてきましたー", "2023-02-17 15:46:35", "2023-02-17 15:46:35");
INSERT INTO posts(plant_record_id, title, article, created_at, updated_at) values(1, "徐々に育ってきています", "いい感じです〜", "2023-02-17 15:46:35", "2023-02-17 15:46:35");
INSERT INTO posts(plant_record_id, title, article, created_at, updated_at) values(1, "蕾が咲きました", "もうそろそろ咲きそうかな？", "2023-02-17 15:46:35", "2023-02-17 15:46:35");
INSERT INTO posts(plant_record_id, title, article, created_at, updated_at) values(1, "花が咲きました", "綺麗に咲きました！", "2023-02-17 15:46:35", "2023-02-17 15:46:35");
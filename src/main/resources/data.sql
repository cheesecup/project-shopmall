/* Member 데이터 입력 */
INSERT INTO member(name, email, password, address, role)
VALUES ('Park', 'park@naver.com', 'park1234', '서울특별시 강남구 테헤란로', 'USER');

INSERT INTO member(name, email, password, address, role)
VALUES ('Kim', 'kim@gmail.com', 'kim1234', '전라남도 여수시', 'USER');

/* Item 데이터 입력 */
INSERT INTO item(item_name, price, stock_number, item_detail, item_sell_status, created_by, updated_by, reg_time, update_time)
VALUES('나무 연필', 500, 200, '고급 나무 연필', 'SELL', 'chiz', 'chiz', '2021-05-30 23:53:46', '2021-05-30 23:53:46');

INSERT INTO item(item_name, price, stock_number, item_detail, item_sell_status, created_by, updated_by, reg_time, update_time)
VALUES('고무 연필', 1200, 150, '부드러운 고무 연필', 'SELL', 'chiz', 'chiz', '2021-05-31 23:53:46', '2021-06-01 23:53:46');

INSERT INTO item(item_name, price, stock_number, item_detail, item_sell_status, created_by, updated_by, reg_time, update_time)
VALUES('지우개', 1000, 100, '가루가 없는 지우개', 'SELL', 'cheesecup', 'cheesecup', '2021-06-20 13:53:46', '2021-06-23 11:23:55');

INSERT INTO item(item_name, price, stock_number, item_detail, item_sell_status, created_by, updated_by, reg_time, update_time)
VALUES('볼펜', 2500, 250, '오래쓰는 볼펜', 'SELL', 'baekwon', 'baekwon', '2021-07-11 17:13:11', '2021-07-15 20:22:31');

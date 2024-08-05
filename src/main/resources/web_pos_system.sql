DROP DATABASE IF EXISTS pos_system;

CREATE DATABASE IF NOT EXISTS  pos_system;

SHOW DATABASES;

USE pos_system;

create table customer(
                         id VARCHAR(15) PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         address VARCHAR(255) NOT NULL,
                         salary DOUBLE NOT NULL
);

create table item(
                     code VARCHAR(15) PRIMARY KEY,
                     name VARCHAR(255) NOT NULL,
                     price DECIMAL(10,2) NOT NULL,
                     qty INT NOT NULL
);

create table orders(
                       order_id VARCHAR(15) PRIMARY KEY,
                       date DATE NOT NULL,
                       cust_id VARCHAR(15) NOT NULL,
                       discount DECIMAL(4,1),
                       total DECIMAL(10,2) NOT NULL,
                       CONSTRAINT FOREIGN KEY (cust_id) REFERENCES customer(id) ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TABLE order_details (
                               order_id VARCHAR(15) NOT NULL,
                               item_code VARCHAR(15) NOT NULL,
                               unit_price DECIMAL(10, 2) NOT NULL,
                               qty INT,
                               PRIMARY KEY (order_id, item_code),
                               CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE,
                               CONSTRAINT fk_item FOREIGN KEY (item_code) REFERENCES item(code) ON DELETE CASCADE ON UPDATE CASCADE
);

# create table order_details(
#                               order_id VARCHAR(15) PRIMARY KEY,
#                               item_code VARCHAR(15) NOT NULL,
#                               unit_price DECIMAL(10,2) NOT NULL,
#                               qty INT,
#                               CONSTRAINT FOREIGN KEY (item_code) REFERENCES item(code) ON DELETE CASCADE ON UPDATE CASCADE
# );
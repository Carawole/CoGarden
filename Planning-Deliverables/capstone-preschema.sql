drop database if exists capstone_preschema;
create database capstone_preschema;
use capstone_preschema;

create table `user` (
	user_id int primary key auto_increment,
    email varchar(100) not null unique,
    password_hash varchar(250) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    phone varchar(20) null,
    address text null,
    is_admin boolean default false
);

create table category (
	category_id int primary key auto_increment,
    category_name varchar(50) not null
);

create table product (
	product_id int primary key auto_increment,
    product_name varchar(100) not null,
    category_id int null,
    `description` text null,
    cycle varchar(30) null,
    watering varchar(30) null,
    sunlight varchar(30) null,
    hardiness_zone int null,
    price decimal(10,2) null,
    constraint fk_product_category_id
		foreign key (category_id)
        references category(category_id)
);

create table `session` (
	session_id int primary key auto_increment,
    user_id int not null,
    total decimal(10,2),
    created_at timestamp DEFAULT current_timestamp,
    constraint fk_session_user_id
		foreign key (user_id)
        references `user`(user_id)
);

create table session_item (
	session_item_id int primary key auto_increment,
	session_id int not null,
    product_id int not null,
    quantity int not null,
    constraint fk_session_item_session_id
		foreign key (session_id)
        references `session`(session_id),
    constraint fk_session_item_product_id
		foreign key (product_id)
        references product(product_id)
);

create table `order` (
	order_id int primary key auto_increment,
    user_id int not null,
    order_status varchar(50) not null,
    total decimal(10,2),
    created_at timestamp DEFAULT current_timestamp,
    constraint fk_order_user_id
		foreign key (user_id)
        references `user`(user_id)
);

create table order_item (
	order_item_id int primary key auto_increment,
	order_id int not null,
    product_id int not null,
    quantity int not null,
    constraint fk_order_item_order_id
		foreign key (order_id)
        references `order`(order_id),
    constraint fk_order_item_product_id
		foreign key (product_id)
        references product(product_id)
);
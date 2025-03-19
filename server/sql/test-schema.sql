drop database if exists cogarden_test;
create database cogarden_test;
use cogarden_test;

create table `user` (
	user_id int primary key auto_increment,
    email varchar(100) not null unique,
    password_hash varchar(250) not null,
    is_admin boolean default false
);

create table product (
	product_id int primary key auto_increment,
    product_name varchar(100) not null,
    category varchar(50) not null,
    `description` text null,
    cycle varchar(30) null,
    watering varchar(30) null,
    sunlight varchar(30) null,
    hardiness_zone int null,
    price decimal(10,2) not null
);

create table cart (
	cart_id int primary key auto_increment,
    user_id int not null,
    total decimal(10,2),
    created_at timestamp DEFAULT current_timestamp,
    constraint fk_cart_user_id
		foreign key (user_id)
        references `user`(user_id)
);

create table cart_item (
	cart_item_id int primary key auto_increment,
	cart_id int not null,
    product_id int not null,
    quantity int not null,
    constraint fk_cart_item_cart_id
		foreign key (cart_id)
        references cart(cart_id),
    constraint fk_cart_item_product_id
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

delimiter //
create procedure set_known_good_state()
begin
	delete from order_item;
    delete from `order`;
    alter table `order` auto_increment = 1;
    delete from cart_item;
    delete from cart;
    alter table cart auto_increment = 1;
    delete from product;
    alter table product auto_increment = 1;
    delete from `user`;
    alter table `user` auto_increment = 1;

    insert into `user` (email, password_hash, is_admin)
        values
        ('test@email.com', 'testPasswordHash', false),
        ('test2@email.com', 'testPasswordHash2', true);
end//
delimiter ;
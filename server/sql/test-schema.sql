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
    price decimal(10,2) not null,
    constraint uq_product_name_category_price
        unique(product_name, category, price)
);

create table cart (
	cart_id int primary key auto_increment,
    user_id int not null unique,
    total decimal(10,2) DEFAULT 0.00 not null,
    created_at timestamp DEFAULT current_timestamp not null,
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
        references cart(cart_id)
        on delete cascade,
    constraint fk_cart_item_product_id
		foreign key (product_id)
        references product(product_id),
    constraint uq_cart_id_product_id
		unique(cart_id, product_id)
);

create table `order` (
	order_id int primary key auto_increment,
    user_id int not null,
    order_status varchar(50) DEFAULT 'PENDING' not null,
    total decimal(10,2) DEFAULT 0.00 not null,
    created_at timestamp DEFAULT current_timestamp not null,
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
    alter table order_item auto_increment = 1;
    delete from `order`;
    alter table `order` auto_increment = 1;
    delete from cart_item;
    alter table cart_item auto_increment = 1;
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
        
	insert into product (product_name, category, `description`, cycle, watering, sunlight, hardiness_zone, price)
	    values
        ('Test Product 1', 'FLOWERS', 'Test Description 1', 'Test Cycle 1', 'Test Watering 1', 'Test Sunlight 1', 1, 1.00),
        ('Test Product 2', 'TREES', 'Test Description 2', 'Test Cycle 2', 'Test Watering 2', 'Test Sunlight 2', 2, 2.00),
        ('Test Product 3', 'FLOWERS', 'Test Description 3', 'Test Cycle 3', 'Test Watering 3', 'Test Sunlight 3', 3, 3.00);

    insert into cart (user_id, total)
        values
        (2, 1.00);

    insert into cart_item (cart_id, product_id, quantity)
        values
        (1, 2, 4),
        (1, 3, 2);
end//
delimiter ;
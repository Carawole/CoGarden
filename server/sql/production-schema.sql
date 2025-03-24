drop database if exists cogarden;
create database cogarden;
use cogarden;

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
        unique (product_name, category, price)
);

create table cart (
	cart_id int primary key auto_increment,
    user_id int not null unique,
    total decimal(10,2) DEFAULT 0.00 not null,
    created_at timestamp DEFAULT current_timestamp not null,
    constraint fk_cart_user_id
		foreign key (user_id)
        references `user`(user_id),
    constraint uq_user_id
		unique (user_id)
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
		unique (cart_id, product_id)
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

 insert into `user` (email, password_hash, is_admin)
     values
     ('tt@kellogg.com', '$2a$12$0E8Ln6D7dkSw9HTrDNujZ.cDWbLJUIuE2X24oGZ1y3dtUnw755B8q', true);

insert into product (product_name, category, `description`, cycle, watering, sunlight, hardiness_zone, price)
    values
    ('Aloe Vera', 'Succulents', 'Aloe Vera is a succulent plant that is native to the Arabian Peninsula. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 5.99),
    ('Cactus', 'Succulents', 'Cactus is a succulent plant that is native to the Americas. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 3.99),
    ('Echeveria', 'Succulents', 'Echeveria is a succulent plant that is native to Mexico. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 4.99),
    ('Haworthia', 'Succulents', 'Haworthia is a succulent plant that is native to South Africa. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 6.99),
    ('Kalanchoe', 'Succulents', 'Kalanchoe is a succulent plant that is native to Madagascar. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 7.99),
    ('Lithops', 'Succulents', 'Lithops is a succulent plant that is native to South Africa. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 8.99),
    ('Mammillaria', 'Succulents', 'Mammillaria is a succulent plant that is native to Mexico. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 9.99),
    ('Notocactus', 'Succulents', 'Notocactus is a succulent plant that is native to Mexico. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 10.99),
    ('Opuntia', 'Succulents', 'Opuntia is a succulent plant that is native to Mexico. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 11.99),
    ('Pachypodium', 'Succulents', 'Pachypodium is a succulent plant that is native to Madagascar. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 12.99);
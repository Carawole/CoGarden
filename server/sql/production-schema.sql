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
     ('tt@kellogg.com', '$2a$12$0E8Ln6D7dkSw9HTrDNujZ.cDWbLJUIuE2X24oGZ1y3dtUnw755B8q', true),
     ('mj@neverland.com', '$2a$12$.zFBDfuROCrYI6WOb6NgpeHzihO8DCYbh0XJARgf.DTTyOUE2..WS', false);

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
    ('Pachypodium', 'Succulents', 'Pachypodium is a succulent plant that is native to Madagascar. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Low', 'Full Sun', 9, 12.99),
    ('Rose', 'Flowers', 'Rose is a flowering plant that is native to Asia. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 13.99),
    ('Daisy', 'Flowers', 'Daisy is a flowering plant that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 14.99),
    ('Tulip', 'Flowers', 'Tulip is a flowering plant that is native to Asia. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 15.99),
    ('Sunflower', 'Flowers', 'Sunflower is a flowering plant that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 16.99),
    ('Orchid', 'Flowers', 'Orchid is a flowering plant that is native to Asia. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 17.99),
    ('Lily', 'Flowers', 'Lily is a flowering plant that is native to Asia. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 18.99),
    ('Daffodil', 'Flowers', 'Daffodil is a flowering plant that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 19.99),
    ('Hyacinth', 'Flowers', 'Hyacinth is a flowering plant that is native to Asia. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 20.99),
    ('Iris', 'Flowers', 'Iris is a flowering plant that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 21.99),
    ('Oak', 'Trees', 'Oak is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 22.99),
    ('Maple', 'Trees', 'Maple is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 23.99),
    ('Pine', 'Trees', 'Pine is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 24.99),
    ('Birch', 'Trees', 'Birch is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 25.99),
    ('Cedar', 'Trees', 'Cedar is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 26.99),
    ('Fir', 'Trees', 'Fir is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 27.99),
    ('Spruce', 'Trees', 'Spruce is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 28.99),
    ('Redwood', 'Trees', 'Redwood is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 29.99),
    ('Sequoia', 'Trees', 'Sequoia is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 30.99),
    ('Yew', 'Trees', 'Yew is a tree that is native to North America. It is a popular houseplant and is often used in gardens.', 'Perennial', 'High', 'Full Sun', 3, 31.99),
    ('Boxwood', 'Shrubs', 'Boxwood is a shrub that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 32.99),
    ('Holly', 'Shrubs', 'Holly is a shrub that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 33.99),
    ('Juniper', 'Shrubs', 'Juniper is a shrub that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 34.99),
    ('Lilac', 'Shrubs', 'Lilac is a shrub that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 35.99),
    ('Rhododendron', 'Shrubs', 'Rhododendron is a shrub that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 36.99),
    ('Azalea', 'Shrubs', 'Azalea is a shrub that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 37.99),
    ('Camellia', 'Shrubs', 'Camellia is a shrub that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 38.99),
    ('Forsythia', 'Shrubs', 'Forsythia is a shrub that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 39.99),
    ('Hydrangea', 'Shrubs', 'Hydrangea is a shrub that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 25.99),
    ('Lavender', 'Edibles', 'Lavender is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 26.99),
    ('Mint', 'Edibles', 'Mint is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 27.99),
    ('Oregano', 'Edibles', 'Oregano is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 28.99),
    ('Parsley', 'Edibles', 'Parsley is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 29.99),
    ('Rosemary', 'Edibles', 'Rosemary is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 30.99),
    ('Sage', 'Edibles', 'Sage is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 31.99),
    ('Thyme', 'Edibles', 'Thyme is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 32.99),
    ('Basil', 'Edibles', 'Basil is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 33.99),
    ('Chives', 'Edibles', 'Chives is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 34.99),
    ('Cilantro', 'Edibles', 'Cilantro is an herb that is native to Europe. It is a popular houseplant and is often used in gardens.', 'Perennial', 'Medium', 'Full Sun', 5, 35.99),
    ('Soil', 'Other', 'Soil is a mixture of organic matter, minerals, gases, liquids, and organisms that together support life.', 'N/A', 'N/A', 'N/A', 0, 5.99),
    ('Fertilizer', 'Other', 'Fertilizer is a substance added to soil to improve plant growth and health.', 'N/A', 'N/A', 'N/A', 0, 6.99),
    ('Pesticide', 'Other', 'Pesticide is a substance used for destroying insects or other organisms harmful to cultivated plants or to animals.', 'N/A', 'N/A', 'N/A', 0, 7.99),
    ('Mulch', 'Other', 'Mulch is a layer of material applied to the surface of soil. Reasons for applying mulch include conservation of soil moisture, improving fertility and health of the soil, reducing weed growth and enhancing the visual appeal of the area.', 'N/A', 'N/A', 'N/A', 0, 8.99),
    ('Gloves', 'Other', 'Gloves are a garment covering the whole hand.', 'N/A', 'N/A', 'N/A', 0, 9.99),
    ('Pruners', 'Other', 'Pruners are a type of scissors used for cutting plants.', 'N/A', 'N/A', 'N/A', 0, 10.99),
    ('Trowel', 'Other', 'Trowel is a small hand tool used for digging, applying, smoothing, or moving small amounts of viscous or particulate material.', 'N/A', 'N/A', 'N/A', 0, 11.99),
    ('Watering Can', 'Other', 'Watering Can is a container with a handle and a spout used for watering plants.', 'N/A', 'N/A', 'N/A', 0, 12.99),
    ('Hose', 'Other', 'Hose is a flexible tube conveying water.', 'N/A', 'N/A', 'N/A', 0, 13.99),
    ('Sprinkler', 'Other', 'Sprinkler is a device used to spray water.', 'N/A', 'N/A', 'N/A', 0, 14.99);

insert into `order` (user_id, order_status, total, created_at)
    values
    (2, 'COMPLETED', 55.96, '2025-03-24 20:19:31'),
    (2, 'PENDING', 94.90, '2025-03-26 01:17:47'),
    (2, 'PROCESSING', 16.99, '2025-03-26 01:20:55');
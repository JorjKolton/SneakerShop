create table buckets
(
    id      varchar(255) not null,
    user_id varchar(255),
    constraint buckets_pkey
        primary key (id)
);

create table products
(
    id          varchar(255) not null,
    back_image  varchar(255),
    gender      varchar(255),
    height      varchar(255),
    left_image  varchar(255),
    model       varchar(15),
    price       integer      not null,
    right_image varchar(255),
    sole_image  varchar(255),
    title       varchar(255),
    title_image varchar(255),
    top_image   varchar(255),
    constraint products_pkey
        primary key (id),
    constraint products_price_check
        check (price >= 2000)
);

create table buckets_products
(
    bucket_id  varchar(255) not null,
    product_id varchar(255) not null,
    constraint fk_products
        foreign key (product_id) references products,
    constraint fk_buckets
        foreign key (bucket_id) references buckets
);

create table sizes
(
    id              varchar(255) not null,
    euro_size       varchar(4),
    number_in_stock integer      not null,
    product_id      varchar(255),
    constraint sizes_pkey
        primary key (id),
    constraint fk_products
        foreign key (product_id) references products
);

create table users
(
    id        varchar(255) not null,
    email     varchar(255),
    name      varchar(30),
    password  varchar(20),
    role      varchar(6),
    bucket_id varchar(255),
    constraint users_pkey
        primary key (id),
    constraint fk_buckets
        foreign key (bucket_id) references buckets
);

alter table buckets
    add constraint fk_users
        foreign key (user_id) references users;

create table orders
(
    id           varchar(255) not null,
    address      varchar(255),
    created      timestamp,
    order_status varchar(255),
    sum          integer      not null,
    updated      timestamp,
    user_id      varchar(255),
    constraint orders_pkey
        primary key (id),
    constraint fk_users
        foreign key (user_id) references users
);

create table orders_details
(
    id         varchar(255) not null,
    amount     integer      not null,
    price      integer      not null,
    order_id   varchar(255),
    product_id varchar(255),
    constraint orders_details_pkey
        primary key (id),
    constraint fk_orders
        foreign key (order_id) references orders,
    constraint fk_products
        foreign key (product_id) references products
);
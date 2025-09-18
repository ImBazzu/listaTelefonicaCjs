CREATE TABLE usuarios(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    login VARCHAR(20) NOT NULL ,
    senha varchar(60) NOT NULL ,
    role varchar(10) not null
);


INSERT INTO usuarios (login, senha, role)
VALUES ('admin', '$2a$12$dHjwgt9L9G02ExrpnvMz3Oh099J2HiY0d7lN7zVgl1JGk.XvIuvH6', 'ADMIN');

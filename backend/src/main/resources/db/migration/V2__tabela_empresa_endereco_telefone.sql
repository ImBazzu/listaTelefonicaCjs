CREATE TABLE empresas(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome varchar(100) not null,
    descricao text,
    logradouro varchar(100),
    numero varchar(10),
    bairro varchar(30),
    cidade varchar(100),
    uf varchar(2),
    cep varchar(10)
);

create table categorias(
    id bigint primary key not null auto_increment,
    nome varchar(100) not null
);

create table empresa_categoria(
    empresa_id bigint not null,
    categoria_id bigint not null,
    primary key (empresa_id,categoria_id),
    constraint empresa_categoria_fk_empresa foreign key (empresa_id) references empresas(id),
    constraint empresa_categoria_fk_categoria foreign key (categoria_id) references categorias(id)
);

create table telefones(
    id bigint primary key not null auto_increment,
    numero varchar(15) not null ,
    principal boolean default false,
    empresa_id bigint,
    constraint telefones_fk_empresa foreign key (empresa_id) references empresas(id)
)
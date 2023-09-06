-- Criando tabela tb_user
drop table tb_user;
create table tb_user(
  id_user number(4) constraint us_id_pk primary key
                    constraint us_id_nn not null,
  nm_user varchar(50) constraint us_nm_nn not null,
  em_user varchar(50) constraint us_em_nn not null,
  role_user varchar(10) constraint us_rl_nn not null
);

-- Criando tabela tb_customer
DROP TABLE tb_customer;
CREATE TABLE tb_customer(
  cpf_customer CHAR(11) CONSTRAINT cu_cpf_pk PRIMARY KEY
                        CONSTRAINT cu_cpf_nn NOT NULL,
  id_user NUMBER(4) CONSTRAINT cu_id_fk REFERENCES tb_user(id_user)
);

-- Criando table tb_analyst
DROP TABLE tb_analyst;
CREATE TABLE tb_analyst(
  rm_analyst char(12) CONSTRAINT an_rm_pk PRIMARY KEY
                      CONSTRAINT an_rm_nn NOT NULL,
  id_user number(4) CONSTRAINT an_id_fk REFERENCES tb_user(id_user) 
);

-- Criando table tb_bicycle
DROP TABLE tb_bicycle;
CREATE TABLE tb_bicycle (
    id_bicycle NUMBER(4) CONSTRAINT bi_id_pk PRIMARY KEY 
                         CONSTRAINT bi_id_nn NOT NULL,
    num_serie_bicycle VARCHAR2(12) CONSTRAINT bi_ns_nn NOT NULL,
    nm_bicycle VARCHAR2(50) CONSTRAINT bi_nm_nn NOT NULL,
    brand_bicycle VARCHAR2(50) CONSTRAINT bi_br_nn NOT NULL,
    price_bicycle NUMBER(10, 2) CONSTRAINT bi_pr_nn NOT NULL,
    year_bicycle CHAR(4) CONSTRAINT bi_ye_nn NOT NULL,
    ds_bicycle VARCHAR2(100) CONSTRAINT bi_ds_nn NOT NULL,
    cpf_customer CHAR(11) CONSTRAINT bi_cpf_pk REFERENCES tb_customer(cpf_customer)
);

-- Crie a tabela Addon
DROP TABLE tb_addon;
CREATE TABLE tb_addon (
    id_addon NUMBER(4) CONSTRAINT ad_id_pk PRIMARY KEY,
    nm_addon VARCHAR2(50) CONSTRAINT ad_nm_nn NOT NULL,
    brand_addon VARCHAR2(50) CONSTRAINT ad_br_nn NOT NULL,
    price_addon NUMBER(10, 2) CONSTRAINT ad_pr_nn NOT NULL,
    ds_addon VARCHAR2(100) CONSTRAINT ad_ds_nn NOT NULL,
    id_bicycle NUMBER(4) CONSTRAINT ad_id_bicycle_fk REFERENCES tb_bicycle(id_bicycle)
);

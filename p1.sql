CREATE DATABASE P1_Sistema_Compra_Venda;
USE P1_Sistema_Compra_Venda;

CREATE TABLE cliente (
cli_id  INT auto_increment KEY,
cli_nome VARCHAR(60),
cli_tipo TINYINT DEFAULT 1,
cli_email VARCHAR(60),
cli_telefone VARCHAR(11),
cli_rua VARCHAR(100),
cli_numero VARCHAR(10),
cli_bairro VARCHAR(50),
cli_cidade VARCHAR(50),
cli_estado CHAR(2),
cli_CEP CHAR(9),
cli_pais VARCHAR(30)
);

ALTER TABLE cliente
ADD cli_cpf VARCHAR(11),
ADD cli_cnpj VARCHAR(14);

ALTER TABLE cliente 
MODIFY cli_estado VARCHAR(9);

CREATE TABLE fornecedor(
fnc_id INT auto_increment primary KEY,
fnc_nome VARCHAR(50),
fnc_nome_fantasia VARCHAR(50),
fnc_cnpj VARCHAR(14),
fnc_email VARCHAR(60),
fnc_telefone VARCHAR(11),
fnc_rua VARCHAR(100),
fnc_numero VARCHAR(10),
fnc_bairro VARCHAR(50),
fnc_cidade VARCHAR(50),
fnc_estado CHAR(2),
fnc_CEP CHAR(9),
fnc_pais VARCHAR(30)
);

ALTER TABLE fornecedor
MODIFY fnc_estado VARCHAR(9);

CREATE TABLE produto(
prd_id INT auto_increment primary KEY,
prd_nome VARCHAR(60),
prd_descricao TEXT,
prd_preco_venda DECIMAL(10,2),
prd_qtd_estoque INT,
fnc_id INT,
FOREIGN KEY (fnc_id) REFERENCES fornecedor(fnc_id)
);

CREATE TABLE notaFiscal(
ntf_id INT auto_increment primary KEY,
ntf_data_venda DATETIME,
ntf_tipo  TINYINT DEFAULT 1,
ntf_quant_vend INT, 
cli_id INT,
fnc_id INT,
FOREIGN KEY (cli_id) REFERENCES cliente(cli_id),
FOREIGN KEY (fnc_id) REFERENCES fornecedor(fnc_id)
);

CREATE TABLE itemNotaFiscal(
inf_id INT auto_increment KEY,
ntf_id INT,
prd_id INT,
inf_quantidade INT,
inf_preco_unidade DECIMAL (10,2),
inf_subtotal DECIMAL(10,2),
FOREIGN KEY (ntf_id) REFERENCES notaFiscal(ntf_id),
FOREIGN KEY (prd_id) REFERENCES produto(prd_id)
);
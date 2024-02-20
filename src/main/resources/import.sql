INSERT INTO taxa(tipo, valor) VALUES ( 'MENSALIDADE_PADRAO', 31.25 );
INSERT INTO taxa(tipo, valor) VALUES ( 'RENDIMENTO_PADRAO', 11.25 );

INSERT INTO cliente(nome, cpf, endereco, data_nascimento, tipo) VALUES ( 'Rodrigo Perez', '12345678915', 'Rua A', '1968-05-11', 'C' );

INSERT INTO conta(saldo, tipo, taxa_id) VALUES ( 20000.00, 'COR', 1);
INSERT INTO conta(saldo, tipo, taxa_id) VALUES ( 55000.00, 'POU', 2);
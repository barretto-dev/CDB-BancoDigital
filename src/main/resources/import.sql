INSERT INTO taxa_conta(tipo, valor) VALUES ( 'MENSALIDADE_PADRAO', 31.25 );
INSERT INTO taxa_conta(tipo, valor) VALUES ( 'RENDIMENTO_PADRAO', 11.25 );

INSERT INTO taxa_cartao(tipo, valor) VALUES ( 'DEBITO', 1.5 );
INSERT INTO taxa_cartao(tipo, valor) VALUES ( 'CREDITO', 4.0 );

INSERT INTO cliente(nome, cpf, endereco, data_nascimento, tipo) VALUES ( 'Rodrigo Perez', '12345678915', 'Rua A', '1968-05-11', 'C' );

INSERT INTO conta(agencia, saldo, tipo, cliente_id, taxa_id) VALUES ( '000-1', 20000.00, 'COR', 1, 1);
INSERT INTO conta(agencia, saldo, tipo, cliente_id, taxa_id) VALUES ( '000-2', 55000.00, 'POU', 1, 2);
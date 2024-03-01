INSERT INTO taxa_conta(tipo, valor) VALUES ( 'MENSALIDADE_PADRAO', 31.25 );
INSERT INTO taxa_conta(tipo, valor) VALUES ( 'RENDIMENTO_PADRAO', 11.25 );

INSERT INTO taxa_cartao(tipo, valor) VALUES ( 'DEBITO', 2.0 );
INSERT INTO taxa_cartao(tipo, valor) VALUES ( 'CREDITO', 4.0 );

INSERT INTO cliente(nome, cpf, endereco, data_nascimento, tipo) VALUES( 'Rodrigo Perez Almeida Santos', '12345678915', 'Rua A', '1968-05-11', 'C' );

INSERT INTO conta(agencia, saldo, tipo, cliente_id, taxa_id) VALUES ( '000-1', 20204.00, 'COR', 1, 1);
INSERT INTO conta(agencia, saldo, tipo, cliente_id, taxa_id) VALUES ( '000-2', 55000.00, 'POU', 1, 2);


INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, ativo, conta_id, taxa_id) VALUES ( '0000000000000001', 'RODRIGO P A SANTOS', 185, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', true, 1, 1);
INSERT INTO cartao_debito(limite_diario, cartao_id) VALUES ( 500.00, 1);

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, ativo, conta_id, taxa_id) VALUES ( '0000000000000002', 'RODRIGO PEREZ A S', 857, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', true, 1, 2);
INSERT INTO cartao_credito(limite_mensal, cartao_id) VALUES ( 2000.00, 2);

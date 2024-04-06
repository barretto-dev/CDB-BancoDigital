INSERT INTO tipo_cliente(nome) VALUES ( 'COMUM' );
INSERT INTO tipo_cliente(nome) VALUES ( 'SUPER' );
INSERT INTO tipo_cliente(nome) VALUES ( 'PREMIUM' );

INSERT INTO taxa_conta(tipo, valor) VALUES ( 'MENSALIDADE_PADRAO', 12.00 );
INSERT INTO taxa_conta(tipo, valor) VALUES ( 'RENDIMENTO_PADRAO', 0.50 );

INSERT INTO taxa_cartao(tipo, valor) VALUES ( 'DEBITO', 2.0 );
INSERT INTO taxa_cartao(tipo, valor) VALUES ( 'CREDITO', 4.0 );

INSERT INTO endereco(cep, uf, cidade, bairro, logradouro, numero, complemento) VALUES ( '49010-180', 'SE', 'Aracaju', 'Centro', 'Rua Estância', '77', '' );

INSERT INTO cliente(nome, cpf, data_nascimento, tipo_cliente_id, endereco_id) VALUES( 'Rodrigo Perez Almeida Santos', '123.456.789-15', '1968-05-11', 1, 1 );

INSERT INTO conta(numero,agencia, saldo, tipo, cliente_id, taxa_id) VALUES ( '000000001', '0001', 20204.00, 'COR', 1, 1);
INSERT INTO conta(numero,agencia, saldo, tipo, cliente_id, taxa_id) VALUES ( '000000002', '0001', 55000.00, 'POU', 1, 2);
INSERT INTO conta(numero,agencia, saldo, tipo, cliente_id, taxa_id) VALUES ( '000000003', '0001', 1500.00, 'COR', 1, 1);


--A SENHA DE TODOS OS CARTÕES SÃO 123456

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id, taxa_id) VALUES ( '0000000000000001', 'RODRIGO P A SANTOS', 185, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2024-03-12', '2029-03-12', true, 1, 1);
INSERT INTO cartao_debito(limite_diario, cartao_id) VALUES ( 500.00, 1);

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id, taxa_id) VALUES ( '0000000000000002', 'RODRIGO PEREZ A S', 857, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2024-02-12', '2029-02-12', true, 1, 2);
INSERT INTO cartao_credito(limite_mensal, cartao_id) VALUES ( 2000.00, 2);

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id, taxa_id) VALUES ( '0000000000000003', 'RODRIGO P A S', 857, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2017-02-12', '2023-02-12', true, 1, 2);
INSERT INTO cartao_debito(limite_diario, cartao_id) VALUES ( 500.00, 3);

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id, taxa_id) VALUES ( '0000000000000004', 'RODRIGO PEREZ A S', 857, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2024-02-12', '2029-02-12', true, 3, 2);
INSERT INTO cartao_credito(limite_mensal, cartao_id) VALUES ( 2000.00, 4);

INSERT INTO pagamento(destinatario, valor, parcela_atual, parcela_total, data_pagamento, cartao_id) VALUES ('Empresa A', 50.00, null, null, '2024-03-12 19:34:43.309845', 1);
INSERT INTO pagamento(destinatario, valor, parcela_atual, parcela_total, data_pagamento, cartao_id) VALUES ('Empresa B', 200.00, null, null, NOW(), 1);

INSERT INTO seguro(nome_produto, descricao) VALUES ('automóvel', 'Regras para o seguro de automóvel são...')
INSERT INTO seguro(nome_produto, descricao) VALUES ('imóvel', 'Regras para o seguro de imovél são...')

INSERT INTO apolice(cartao_credito_id, seguro_id, numero, data, valor ) VALUES ( 2, 1, '000000000001', NOW(), 270.00)



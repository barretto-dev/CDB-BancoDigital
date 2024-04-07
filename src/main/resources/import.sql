INSERT INTO tipo_cliente(nome) VALUES ( 'COMUM' );
INSERT INTO tipo_cliente(nome) VALUES ( 'SUPER' );
INSERT INTO tipo_cliente(nome) VALUES ( 'PREMIUM' );

INSERT INTO mensalidade_conta(nome,valor,tipo_cliente_id) VALUES ( 'COMUM', 12.00, 1 );
INSERT INTO mensalidade_conta(nome,valor,tipo_cliente_id) VALUES ( 'SUPER', 8.00, 2 );
INSERT INTO mensalidade_conta(nome,valor,tipo_cliente_id) VALUES ( 'PREMIUM', 0.00, 3 );

INSERT INTO rendimento_conta(nome,valor,tipo_cliente_id) VALUES ( 'COMUM', 0.50, 1 );
INSERT INTO rendimento_conta(nome,valor,tipo_cliente_id) VALUES ( 'SUPER', 0.70, 2 );
INSERT INTO rendimento_conta(nome,valor,tipo_cliente_id) VALUES ( 'PREMIUM', 0.90, 3 );

INSERT INTO limite_mensal_cartao(nome,valor,tipo_cliente_id) VALUES ( 'COMUM', 1000.00, 1 );
INSERT INTO limite_mensal_cartao(nome,valor,tipo_cliente_id) VALUES ( 'SUPER', 5000.00, 2 );
INSERT INTO limite_mensal_cartao(nome,valor,tipo_cliente_id) VALUES ( 'PREMIUM', 10000.00, 3 );

INSERT INTO endereco(cep, uf, cidade, bairro, logradouro, numero, complemento) VALUES ( '49010-180', 'SE', 'Aracaju', 'Centro', 'Rua Estância', '77', '' );

INSERT INTO cliente(nome, cpf, data_nascimento, tipo_cliente_id, endereco_id) VALUES( 'Rodrigo Perez Almeida Santos', '123.456.789-15', '1968-05-11', 1, 1 );

INSERT INTO conta(numero,agencia, saldo, tipo, cliente_id) VALUES ( '000000001', '0001', 20204.00, 'CORRENTE', 1);
INSERT INTO conta(numero,agencia, saldo, tipo, cliente_id) VALUES ( '000000002', '0001', 55000.00, 'POUPANCA', 1);
INSERT INTO conta(numero,agencia, saldo, tipo, cliente_id) VALUES ( '000000003', '0001', 1500.00, 'CORRENTE', 1);


--A SENHA DE TODOS OS CARTÕES SÃO 123456

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id) VALUES ( '0000000000000001', 'RODRIGO P A SANTOS', 185, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2024-03-12', '2029-03-12', true, 1);
INSERT INTO cartao_debito(limite_diario, cartao_id) VALUES ( 600.00, 1);

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id) VALUES ( '0000000000000002', 'RODRIGO PEREZ A S', 857, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2024-02-12', '2029-02-12', true, 1);
INSERT INTO cartao_credito(cartao_id) VALUES (2);

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id) VALUES ( '0000000000000003', 'RODRIGO P A S', 857, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2017-02-12', '2023-02-12', true, 1);
INSERT INTO cartao_debito(limite_diario, cartao_id) VALUES ( 600.00, 3);

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id) VALUES ( '0000000000000004', 'RODRIGO PEREZ A S', 857, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2024-02-12', '2029-02-12', true, 3);
INSERT INTO cartao_credito(cartao_id) VALUES (4);

INSERT INTO pagamento(destinatario, valor, parcela_atual, parcela_total, data_pagamento, cartao_id) VALUES ('Empresa A', 50.00, null, null, '2024-03-12 19:34:43.309845', 1);
INSERT INTO pagamento(destinatario, valor, parcela_atual, parcela_total, data_pagamento, cartao_id) VALUES ('Empresa B', 200.00, null, null, NOW(), 1);

INSERT INTO seguro(nome_produto, descricao) VALUES ('Viagem', 'Regras para o seguro de viagem são...')

INSERT INTO seguro_valor_tipo_cliente(tipo_cliente_id, seguro_id, valor, tipo_valor) VALUES (1, 1, 50.00, 'MENSAL')
INSERT INTO seguro_valor_tipo_cliente(tipo_cliente_id, seguro_id, valor, tipo_valor) VALUES (2, 1, 50.00, 'MENSAL')
INSERT INTO seguro_valor_tipo_cliente(tipo_cliente_id, seguro_id, valor, tipo_valor) VALUES (3, 1, 0.00, 'MENSAL')

INSERT INTO seguro(nome_produto, descricao) VALUES ('Fraude', 'Regras para o seguro de fraude são...')

INSERT INTO seguro_valor_tipo_cliente(tipo_cliente_id, seguro_id, valor, tipo_valor) VALUES (1, 2, 5000.00, 'ENTRADA')
INSERT INTO seguro_valor_tipo_cliente(tipo_cliente_id, seguro_id, valor, tipo_valor) VALUES (2, 2, 5000.00, 'ENTRADA')
INSERT INTO seguro_valor_tipo_cliente(tipo_cliente_id, seguro_id, valor, tipo_valor) VALUES (3, 2, 5000.00, 'ENTRADA')

--INSERT INTO apolice(cartao_credito_id, seguro_id, numero, data, valor ) VALUES ( 2, 1, '000000000001', NOW(), 270.00)



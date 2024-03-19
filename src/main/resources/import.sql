INSERT INTO taxa_conta(tipo, valor) VALUES ( 'MENSALIDADE_PADRAO', 31.25 );
INSERT INTO taxa_conta(tipo, valor) VALUES ( 'RENDIMENTO_PADRAO', 0.50 );

INSERT INTO taxa_cartao(tipo, valor) VALUES ( 'DEBITO', 2.0 );
INSERT INTO taxa_cartao(tipo, valor) VALUES ( 'CREDITO', 4.0 );

INSERT INTO cliente(nome, cpf, endereco, data_nascimento, tipo) VALUES( 'Rodrigo Perez Almeida Santos', '12345678915', 'Rua A', '1968-05-11', 'C' );

INSERT INTO conta(numero,agencia, saldo, tipo, cliente_id, taxa_id) VALUES ( '000000001', '0001', 20204.00, 'COR', 1, 1);
INSERT INTO conta(numero,agencia, saldo, tipo, cliente_id, taxa_id) VALUES ( '000000002', '0001', 55000.00, 'POU', 1, 2);


INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id, taxa_id) VALUES ( '0000000000000001', 'RODRIGO P A SANTOS', 185, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2024-02-12', '2029-02-12', true, 1, 1);
INSERT INTO cartao_debito(limite_diario, cartao_id) VALUES ( 500.00, 1);

INSERT INTO cartao(numero, nome_dono, codigo_seg, senha, data_criacao, validade, ativo, conta_id, taxa_id) VALUES ( '0000000000000002', 'RODRIGO PEREZ A S', 857, '$2a$10$ugeRo3Oig3OEx5pdnv4s1ufXnsMoQUWJYMQstDGuHBJzoBi4iUVQK', '2024-02-12', '2029-02-12', true, 1, 2);
INSERT INTO cartao_credito(limite_mensal, cartao_id) VALUES ( 2000.00, 2);

INSERT INTO pagamento(destinatario, valor, parcela_atual, parcela_total, data_pag, cartao_id) VALUES ('Empresa A', 50.00, null, null, '2024-03-11 19:34:43.309845', 2);
INSERT INTO pagamento(destinatario, valor, parcela_atual, parcela_total, data_pag, cartao_id) VALUES ('Empresa B', 900.00, null, null, NOW(), 2);

INSERT INTO seguro(nome_produto, descricao) VALUES ('automóvel', 'Regras para o seguro de automóvel são...')
INSERT INTO seguro(nome_produto, descricao) VALUES ('imóvel', 'Regras para o seguro de imovél são...')

INSERT INTO apolice(cartao_credito_id, seguro_id, numero, data, valor ) VALUES ( 2, 1, '000000000001', NOW(), 270.00)



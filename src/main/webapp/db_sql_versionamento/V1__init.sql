set session_replication_role = replica;
delete from plano;
delete from empresa;
delete from pessoa;

INSERT INTO public.plano(
	id, ativo, descricao, limite_cliente, limite_usuario, nome, tipo_plano, valor_mensal)
	VALUES (1, true, 'Plano Gratuito teste', 2, 4, 'Plano Gratuito', 'FREE', 0);

select nextval('seq_plano');

INSERT INTO public.plano(
	id, ativo, descricao, limite_cliente, limite_usuario, nome, tipo_plano, valor_mensal)
	VALUES (2, true, 'Plano Starter teste', 2, 4, 'Plano Starter', 'STARTER', 50);

select nextval('seq_plano');


INSERT INTO public.pessoa(
    id, ativo, bairro, cep, cidade, cnpj, complemento, cpf, data_cadastro, email, estado, inscricao_estadual, logradouro, nome, nome_fantasia, observacao, pais, razao_social, telefone, tipo_pessoa, empresa_id)
    VALUES (
        1,
        true,
        'Boa Viagem',
        '51021-360',
        'Recife',
        '48.655.687/0001-20',
        'Apto 1101 Edf Ilha de Itamaraca',
        '124.973.650-16',
        '2022-11-18',
        'contato@ajsolucoes.com.br',
        'PE',
        '123.456.789.000',
        'Rua Desembargador Joao Paes, 796',
        'Aj Solucoes e Sistemas LTDA',
        'Aj Solucoes e Sistemas',
        'Micro Empresa - Sociedade Empresária Limitada - Capital Social R$ 80.000,00 - Simples Nacional desde 18/11/2022',
        'Brasil',
        'Aj Solucoes e Sistemas LTDA',
        '(81) 99999-8888',
        'PJ',
        1
    );
SELECT nextval('seq_pessoa');

INSERT INTO public.empresa(
    id, bloqueio, logo_marca, plano_ativo, total_clientes, total_usuarios, vigencia_plano, pessoa_id, plano_id)
    VALUES (
        1,
        false,
        'nao tem logo',
        true,
        2,
        4,
        '2030-10-10',
        1,
        1
    );

SELECT nextval('seq_empresa');

set session_replication_role = origin;
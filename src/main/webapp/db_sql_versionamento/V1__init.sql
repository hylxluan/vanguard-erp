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

INSERT INTO public.categoria (id, nome, empresa_id) VALUES
(nextval('seq_categoria'), 'Eletrônicos', 1),
(nextval('seq_categoria'), 'Informática', 1),
(nextval('seq_categoria'), 'Celulares e Smartphones', 1),
(nextval('seq_categoria'), 'Acessórios para Celular', 1),
(nextval('seq_categoria'), 'Áudio e Som', 1),
(nextval('seq_categoria'), 'Televisores', 1),
(nextval('seq_categoria'), 'Câmeras e Filmadoras', 1),
(nextval('seq_categoria'), 'Games e Consoles', 1),
(nextval('seq_categoria'), 'Informática - Periféricos', 1),
(nextval('seq_categoria'), 'Redes e Conectividade', 1),
(nextval('seq_categoria'), 'Móveis', 1),
(nextval('seq_categoria'), 'Móveis para Escritório', 1),
(nextval('seq_categoria'), 'Decoração', 1),
(nextval('seq_categoria'), 'Iluminação', 1),
(nextval('seq_categoria'), 'Utilidades Domésticas', 1),
(nextval('seq_categoria'), 'Cama, Mesa e Banho', 1),
(nextval('seq_categoria'), 'Cozinha', 1),
(nextval('seq_categoria'), 'Eletrodomésticos', 1),
(nextval('seq_categoria'), 'Eletroportáteis', 1),
(nextval('seq_categoria'), 'Climatização', 1),
(nextval('seq_categoria'), 'Ferramentas', 1),
(nextval('seq_categoria'), 'Ferramentas Elétricas', 1),
(nextval('seq_categoria'), 'Material de Construção', 1),
(nextval('seq_categoria'), 'Jardim', 1),
(nextval('seq_categoria'), 'Automotivo', 1),
(nextval('seq_categoria'), 'Peças e Acessórios Automotivos', 1),
(nextval('seq_categoria'), 'Pneus e Rodas', 1),
(nextval('seq_categoria'), 'Moda Masculina', 1),
(nextval('seq_categoria'), 'Moda Feminina', 1),
(nextval('seq_categoria'), 'Moda Infantil', 1),
(nextval('seq_categoria'), 'Calçados', 1),
(nextval('seq_categoria'), 'Bolsas e Mochilas', 1),
(nextval('seq_categoria'), 'Relógios', 1),
(nextval('seq_categoria'), 'Joias e Bijuterias', 1),
(nextval('seq_categoria'), 'Beleza e Cuidados Pessoais', 1),
(nextval('seq_categoria'), 'Perfumaria', 1),
(nextval('seq_categoria'), 'Saúde e Bem-Estar', 1),
(nextval('seq_categoria'), 'Suplementos Alimentares', 1),
(nextval('seq_categoria'), 'Esporte e Lazer', 1),
(nextval('seq_categoria'), 'Fitness e Musculação', 1),
(nextval('seq_categoria'), 'Camping e Aventura', 1),
(nextval('seq_categoria'), 'Bicicletas', 1),
(nextval('seq_categoria'), 'Brinquedos', 1),
(nextval('seq_categoria'), 'Papelaria', 1),
(nextval('seq_categoria'), 'Livros', 1),
(nextval('seq_categoria'), 'Instrumentos Musicais', 1),
(nextval('seq_categoria'), 'Pet Shop', 1),
(nextval('seq_categoria'), 'Bebidas', 1),
(nextval('seq_categoria'), 'Alimentos e Mercearia', 1),
(nextval('seq_categoria'), 'Limpeza', 1),
(nextval('seq_categoria'), 'Segurança e Vigilância', 1);
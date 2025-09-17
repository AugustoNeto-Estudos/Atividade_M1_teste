# ViaCEP-Integration-Atividade-M1
Autores:
Augusto Neto RGM: 11221101575 |
Nicollas Cavalcante RGM: 11221104304 |
Gleydson Akiriro  RGM: 11221103498 |
Enrico Fuccia RGM:  11221104635 
 
1: Banco
Criar um script sql que será interpretado pelo flyway, criando um campo na tabela product chamado "distribution_center", 
E sete manualmente o valor deste campo para os produtos existentes de maneira aleatoria com 3 possiveis valores:
Mogi das Cruzes, Recife e Porto Alegre.
ex:
SET distribution_center = 'Mogi das Cruzes'
WHERE id IN ('p1', 'p2', 'p3');
2: Entidade
Refatorar as entidades necessarias para acomodar o campo "distribution_center", ter certeza que o campo esta devidamente criado na classe de modelo.
3: Criar uma classe de serviço (@Service) que vai fazer a integração com a viacep atraves da URL: https://viacep.com.br/ws/08773380/json/
4: Criar o novo endpoint (Metodo na controller) que receba CEP e ID (do produto) como Parametros
5: Fazer o endpoint chamar a classe Service.
6: Criar a logica dentro da service, que retorna uma resposta Boolean, baseado na CIDADE do CEP enviado x Distribution_Center do Produto, 
Caso sejam iguais: Retorna True.
7: Criar a request via postman.

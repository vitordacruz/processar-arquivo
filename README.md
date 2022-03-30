# Desafio Processar Arquivo

Cenário de Negócio:
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda da Instituição Financeira recebe e organiza as informações de 
contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje a Instituição Financeira 
já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, 
antes as 10:00 da manhã na abertura das agências.  

Requisito:  
Usar o "serviço da receita" (fake) para processamento automático do arquivo.

Funcionalidade:

0. Criar uma aplicação SprintBoot standalone.  
1. Processa um arquivo CSV de entrada com o formato abaixo.
2. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
3. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma 
nova coluna.

Formato CSV:  
agencia;conta;saldo;status  
0101;12225-6;100,00;A  
0101;12226-8;3200,50;A  
3202;40011-1;-35,12;I  
3202;54001-2;0,00;P  
3202;00321-2;34500,00;B  
...
  
# A solução  
  Para executar o código é necessário:  
  - Possuir Java 8 instalado  
  - Possuir o Maven instalado  
  - Possuir o Arquivo CSV no padrão acima  

# Passo a passo para execução  
  1. Baixar o código  
  2. Executar na raiz do código o comando "mvn clean package" no prompt de comando do Windows, ou no console se for Linux  
  3. Acessar a pasta target do código e copiar o arquivo jar gerado (o arquivo "processar-arquivo-0.0.1-SNAPSHOT.jar")  
  4. Colar o arquivo jar na mesma pasta do arquivo CSV que vai ser processado  
  5. Abrir o prompt de comando ou console e executar o comando "java -jar processar-arquivo-0.0.1-SNAPSHOT.jar arquivo=<endereco_do_arquivo>" onde <endereco_do_arquivo> deve ser o endereço do arquivo CSV que vai ser processado  

# Observações
  - Se o nome do arquivo CSV que vai ser processado for "arquivo.csv" o nome do arquivo processado será "arquivo.csv.processado.csv" 
  - Se o processamento for executado uma segunda vez o arquivo antigo que foi procesado será deletado e um novo será gerado  
  - No arquivo processado será criado uma nova coluna chamada de "result" e será preenchida com o resultado do envio da atualização  
  - Na coluna "result" os possíveis valores são:  
    - "A" = Atualização
    - "N" = Não Atualizado
    - "E" = Erro
  
  
  

# Petshop GUI Java

Aplicação de gerenciamento de petshop desenvolvida em Java com interface gráfica Swing. Permite cadastro de clientes, pets, serviços, pacotes e contratos de serviços/pacotes.

## Funcionalidades
- Cadastro, edição e listagem de clientes.
- Cadastro, edição e listagem de pets.
- Cadastro, listagem e contratação de serviços.
- Criação e contratação de pacotes de serviços.
- Visualização de serviços e pacotes contratados por pet.
- Interface gráfica Swing completa.


## Tecnologias
- Java 17
- Swing (GUI)
- Maven


## Como executar
1. Clone o repositório:
git clone https://github.com/seu-usuario/seu-repositorio.git
2. Abra no Eclipse ou IntelliJ.
3. Compile e execute `TelaHome.java`.

##Telas
[![tela_Home.png](https://i.postimg.cc/RCb7h7Gg/tela_Home.png)](https://postimg.cc/c6wg58p3)
Tela inicial do sistema. Funciona como centro de controle, abrindo caminho para todas as outras funcionalidades.

[![cadastrar_Clientes.png](https://i.postimg.cc/3N6mqhd0/cadastrar_Clientes.png)](https://postimg.cc/rR1DdvGq)
Tela para registrar novos clientes, preenchendo informações como nome, CPF, telefone e e-mail. Possui botões para salvar ou cancelar o cadastro.

[![listar_Clientes.png](https://i.postimg.cc/kMhQGQwc/listar_Clientes.png)](https://postimg.cc/G9G8SsW8)
Tela que exibe todos os clientes cadastrados em uma tabela, com informações de contato e opções de pesquisa, atualização e exclusão.

[![cadastro_Pets.png](https://i.postimg.cc/SR5CP4J8/cadastro_Pets.png)](https://postimg.cc/PvWCLBbr)
Tela para cadastrar novos pets, preenchendo nome, espécie, raça, idade, peso e associando o pet ao dono.

[![listar_Pets.png](https://i.postimg.cc/kMhQGQww/listar_Pets.png)](https://postimg.cc/YjYGym5m)
Tela que mostra todos os pets cadastrados, listando nome, espécie, raça, idade e dono, também com funcionalidades de pesquisa e atualização.

[![cadastrar_Servicos.png](https://i.postimg.cc/x87MZQcb/cadastrar_Servicos.png)](https://postimg.cc/WqGdDQm2)
Tela para criar novos serviços oferecidos pelo petshop, incluindo nome, descrição, preço e duração. Contém botão de salvar.

[![listar_Servicos.png](https://i.postimg.cc/QNnQCQS0/listar_Servicos.png)](https://postimg.cc/vDfgS6JV)
Tela que apresenta todos os serviços cadastrados, mostrando descrição, preço e outras informações relevantes em forma de tabela.

[![cadastro_Pacote.png](https://i.postimg.cc/BZwTbTNp/cadastro_Pacote.png)](https://postimg.cc/23hbXBsL)
Tela para montar pacotes de serviços, permitindo selecionar serviços disponíveis para formar um pacote único com desconto.

## Armazenamento
Todos os dados são armazenados localmente (CSV).

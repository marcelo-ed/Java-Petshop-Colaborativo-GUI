# Sistema de Pet Shop

Este é um sistema de gerenciamento para pet shops que permite realizar operações CRUD de clientes, pets, serviços e agendamentos.

## Funcionalidades

- Cadastro e gerenciamento de clientes
- Cadastro e gerenciamento de pets
- Cadastro e gerenciamento de serviços
- Criação e gerenciamento de pacotes de serviços com descontos
- Agendamento de serviços para datas futuras
- Cancelamento de serviços e pacotes
- Consulta de agendamentos por cliente, pet ou período

## Tecnologias Utilizadas

- Java 11
- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Como Executar

1. Clone o repositório
2. Certifique-se de ter o Java 11 e Maven instalados
3. Execute o comando `mvn spring-boot:run` na raiz do projeto
4. Acesse a aplicação em `http://localhost:8080`
5. Acesse o console do H2 em `http://localhost:8080/h2-console`

## Endpoints da API

### Clientes
- POST /api/clientes - Criar cliente
- GET /api/clientes - Listar todos os clientes
- GET /api/clientes/{id} - Buscar cliente por ID
- PUT /api/clientes/{id} - Atualizar cliente
- DELETE /api/clientes/{id} - Deletar cliente

### Pets
- POST /api/pets - Criar pet
- GET /api/pets - Listar todos os pets
- GET /api/pets/{id} - Buscar pet por ID
- PUT /api/pets/{id} - Atualizar pet
- DELETE /api/pets/{id} - Deletar pet

### Serviços
- POST /api/servicos - Criar serviço
- GET /api/servicos - Listar todos os serviços
- GET /api/servicos/{id} - Buscar serviço por ID
- PUT /api/servicos/{id} - Atualizar serviço
- DELETE /api/servicos/{id} - Deletar serviço

### Pacotes
- POST /api/pacotes - Criar pacote
- GET /api/pacotes - Listar todos os pacotes
- GET /api/pacotes/{id} - Buscar pacote por ID
- PUT /api/pacotes/{id} - Atualizar pacote
- DELETE /api/pacotes/{id} - Deletar pacote

### Agendamentos
- POST /api/agendamentos - Criar agendamento
- GET /api/agendamentos - Listar agendamentos dos últimos 30 dias
- GET /api/agendamentos/{id} - Buscar agendamento por ID
- GET /api/agendamentos/cliente/{clienteId} - Buscar agendamentos por cliente
- GET /api/agendamentos/pet/{petId} - Buscar agendamentos por pet
- GET /api/agendamentos/periodo?inicio=DATA&fim=DATA - Buscar agendamentos por período
- POST /api/agendamentos/{id}/cancelar - Cancelar agendamento 
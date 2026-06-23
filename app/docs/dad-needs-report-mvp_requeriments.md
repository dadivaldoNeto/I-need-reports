# MVP — Dad Needs Report

## 1. Visão Geral

**Dad Needs Report** é uma aplicação web/API simples para gestão de gastos pessoais.

O objetivo principal do MVP é permitir que o usuário registre entradas e gastos, acompanhe o saldo atual, visualize o histórico financeiro e gere um relatório em PDF.

---

## 2. Objetivo do MVP

Criar uma primeira versão funcional que permita:

- Registrar entradas de dinheiro.
- Registrar gastos.
- Consultar o histórico de transações.
- Editar uma transação.
- Apagar uma transação.
- Ver um resumo geral no dashboard.
- Exportar o histórico ou relatório financeiro em PDF.
- Usar SQLite como banco de dados.

---

## 3. Stack Escolhida

### Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- SQLite
- Lombok
- Validation
- Biblioteca para PDF, como OpenPDF ou iText

### Banco de Dados

- SQLite

### Tipo de Projeto

- API REST

---

## 4. Nome do Projeto

```txt
Dad Needs Report
```

### Spring Initializr

```txt
Group: com.dadneedsreport
Artifact: dad-needs-report-api
Name: dad-needs-report-api
Package name: com.dadneedsreport
Description: REST API for personal expense management and PDF reports
```

---

## 5. Arquitetura Simples

O projeto usará uma arquitetura em camadas:

```txt
Controller → Service → Repository → Model → SQLite
```

### Estrutura de Pastas

```txt
src/main/java/com/dadneedsreport/
 ├── DadNeedsReportApiApplication.java
 │
 ├── controller/
 │   ├── TransactionController.java
 │   ├── DashboardController.java
 │   └── ReportController.java
 │
 ├── service/
 │   ├── TransactionService.java
 │   ├── DashboardService.java
 │   └── ReportService.java
 │
 ├── repository/
 │   └── TransactionRepository.java
 │
 ├── model/
 │   └── Transaction.java
 │
 ├── dto/
 │   ├── TransactionRequest.java
 │   ├── TransactionResponse.java
 │   └── DashboardResponse.java
 │
 ├── enums/
 │   └── TransactionType.java
 │
 ├── converter/
 │   └── TransactionTypeConverter.java
 │
 └── exception/
     ├── ResourceNotFoundException.java
     └── GlobalExceptionHandler.java
```

---

## 6. Entidade Principal

### Transaction

Representa uma entrada ou um gasto.

Campos principais:

```txt
id
type
amount
category
description
date
createdAt
updatedAt
```

### Tipos de Transação

```txt
INCOME
EXPENSE
```

---

## 7. Funcionalidades do MVP

## 7.1 Criar Transação

O usuário deve conseguir cadastrar uma entrada ou um gasto.

### Endpoint

```http
POST /api/transactions
```

### Exemplo de Entrada

```json
{
  "type": "INCOME",
  "amount": 250000,
  "category": "Salary",
  "description": "Monthly salary",
  "date": "2026-06-22"
}
```

### Exemplo de Gasto

```json
{
  "type": "EXPENSE",
  "amount": 15000,
  "category": "Food",
  "description": "Lunch",
  "date": "2026-06-22"
}
```

---

## 7.2 Listar Transações

O usuário deve conseguir consultar todas as transações cadastradas.

### Endpoint

```http
GET /api/transactions
```

### Filtros desejados

```txt
type
category
startDate
endDate
```

Exemplo:

```http
GET /api/transactions?type=EXPENSE
GET /api/transactions?startDate=2026-06-01&endDate=2026-06-30
```

---

## 7.3 Buscar Transação por ID

O usuário deve conseguir consultar os detalhes de uma transação específica.

### Endpoint

```http
GET /api/transactions/{id}
```

---

## 7.4 Editar Transação

O usuário deve conseguir atualizar uma entrada ou gasto existente.

### Endpoint

```http
PUT /api/transactions/{id}
```

### Campos editáveis

```txt
type
amount
category
description
date
```

---

## 7.5 Apagar Transação

O usuário deve conseguir apagar uma transação.

### Endpoint

```http
DELETE /api/transactions/{id}
```

### Regra

Antes de apagar no frontend, deve aparecer uma confirmação:

```txt
Are you sure you want to delete this transaction?
```

---

## 7.6 Dashboard

O dashboard deve mostrar um resumo geral das finanças.

### Endpoint

```http
GET /api/dashboard
```

### Dados retornados

```json
{
  "totalIncome": 250000,
  "totalExpenses": 75000,
  "currentBalance": 175000,
  "transactionCount": 12,
  "latestTransactions": []
}
```

### Informações principais

- Total de entradas.
- Total de gastos.
- Saldo atual.
- Número de transações.
- Últimas transações.

---

## 7.7 Exportar Relatório em PDF

O usuário deve conseguir gerar um relatório em PDF.

### Endpoint

```http
GET /api/reports/pdf
```

### Filtros opcionais

```http
GET /api/reports/pdf?startDate=2026-06-01&endDate=2026-06-30
```

### O PDF deve conter

- Nome do app: Dad Needs Report.
- Título do relatório.
- Período selecionado.
- Total de entradas.
- Total de gastos.
- Saldo final.
- Lista das transações.
- Data de geração do relatório.

---

## 8. DTOs

## 8.1 TransactionRequest

Usado para criar e editar transações.

Campos:

```txt
type
amount
category
description
date
```

Validações:

```txt
type: obrigatório
amount: obrigatório e maior que zero
category: obrigatório
date: obrigatório
description: opcional
```

---

## 8.2 TransactionResponse

Usado para retornar transações ao frontend.

Campos:

```txt
id
type
amount
category
description
date
createdAt
updatedAt
```

---

## 8.3 DashboardResponse

Usado para retornar os dados do dashboard.

Campos:

```txt
totalIncome
totalExpenses
currentBalance
transactionCount
latestTransactions
```

---

## 9. Regras de Negócio

- Uma transação deve ser obrigatoriamente do tipo `INCOME` ou `EXPENSE`.
- O valor da transação deve ser maior que zero.
- O saldo atual é calculado assim:

```txt
currentBalance = totalIncome - totalExpenses
```

- O total de entradas soma apenas transações do tipo `INCOME`.
- O total de gastos soma apenas transações do tipo `EXPENSE`.
- Uma transação inexistente deve retornar erro `404 Not Found`.

---

## 10. Endpoints do MVP

A API deve expor os seguintes endpoints principais:

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/dashboard` | Retorna o resumo geral com total de entradas, total de gastos, saldo atual, número de transações e últimas transações. |
| `GET` | `/api/transactions` | Lista todas as transações cadastradas. Pode receber filtros por tipo, categoria e período. |
| `GET` | `/api/transactions/{id}` | Retorna os detalhes de uma transação específica pelo ID. |
| `POST` | `/api/transactions` | Cria uma nova transação, podendo ser entrada ou gasto. |
| `PUT` | `/api/transactions/{id}` | Atualiza uma transação existente. |
| `DELETE` | `/api/transactions/{id}` | Apaga uma transação existente. |
| `GET` | `/api/reports/pdf` | Gera e retorna um relatório financeiro em PDF. |

### Exemplos de uso dos endpoints

#### Criar uma entrada

```http
POST /api/transactions
```

```json
{
  "type": "INCOME",
  "amount": 250000,
  "category": "Salary",
  "description": "Monthly salary",
  "date": "2026-06-22"
}
```

#### Criar um gasto

```http
POST /api/transactions
```

```json
{
  "type": "EXPENSE",
  "amount": 15000,
  "category": "Food",
  "description": "Lunch",
  "date": "2026-06-22"
}
```

#### Listar todas as transações

```http
GET /api/transactions
```

#### Listar apenas gastos

```http
GET /api/transactions?type=EXPENSE
```

#### Listar transações por período

```http
GET /api/transactions?startDate=2026-06-01&endDate=2026-06-30
```

#### Buscar uma transação por ID

```http
GET /api/transactions/1
```

#### Editar uma transação

```http
PUT /api/transactions/1
```

```json
{
  "type": "EXPENSE",
  "amount": 20000,
  "category": "Transport",
  "description": "Taxi",
  "date": "2026-06-22"
}
```

#### Apagar uma transação

```http
DELETE /api/transactions/1
```

#### Ver dashboard

```http
GET /api/dashboard
```

#### Exportar relatório em PDF

```http
GET /api/reports/pdf
```

#### Exportar relatório em PDF por período

```http
GET /api/reports/pdf?startDate=2026-06-01&endDate=2026-06-30
```

---

## 11. Telas Futuras do Frontend

Mesmo sendo uma API, o frontend deverá ter estas telas:

## 11.1 Dashboard

- Cards com saldo atual, entradas e gastos.
- Lista das últimas transações.
- Overview geral.

## 11.2 Adicionar Entrada ou Gasto

- Formulário para criar uma nova transação.
- Campos: tipo, valor, categoria, descrição e data.

## 11.3 Histórico

- Tabela com todas as transações.
- Filtros por tipo, categoria e data.
- Botões para editar, apagar e exportar PDF.

## 11.4 Editar Transação

- Formulário para alterar dados de uma transação.
- Botão para atualizar.
- Botão para apagar com confirmação.

---

## 12. Banco de Dados SQLite

### Nome sugerido do arquivo

```txt
dad_needs_report.db
```

### Exemplo de configuração

```properties
spring.datasource.url=jdbc:sqlite:dad_needs_report.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 13. Prioridade de Desenvolvimento

## Fase 1 — Obrigatório

- Criar projeto Spring Boot.
- Configurar SQLite.
- Criar entidade `Transaction`.
- Criar repository.
- Criar service.
- Criar controller.
- Criar CRUD completo.
- Testar endpoints no Postman ou Insomnia.

## Fase 2 — Dashboard

- Criar endpoint `/api/dashboard`.
- Calcular total de entradas.
- Calcular total de gastos.
- Calcular saldo atual.
- Retornar últimas transações.

## Fase 3 — PDF

- Criar endpoint `/api/reports/pdf`.
- Gerar relatório básico.
- Retornar arquivo PDF na resposta HTTP.

## Fase 4 — Melhorias

- Filtros no histórico.
- Tratamento global de erros.
- Validações.
- Melhor organização dos DTOs.
- Documentação dos endpoints.

---

## 14. Checklist do MVP

```txt
[ ] Projeto criado no Spring Initializr
[ ] Pacote principal configurado
[ ] SQLite configurado
[ ] Transaction criada
[ ] TransactionType criado
[ ] Converter criado, se necessário
[ ] Repository criado
[ ] DTOs criados
[ ] Service criado
[ ] Controller criado
[ ] Criar transação funcionando
[ ] Listar transações funcionando
[ ] Buscar por ID funcionando
[ ] Editar transação funcionando
[ ] Apagar transação funcionando
[ ] Dashboard funcionando
[ ] PDF funcionando
[ ] README criado
[ ] LICENSE adicionada
```

---

## 15. O Que Não Entra no MVP

Para não atrasar, deixar para depois:

- Login e autenticação.
- JWT.
- Upload de recibos.
- Gráficos avançados.
- Multiusuário.
- Categorias personalizadas no banco.
- Integração bancária.
- App mobile.
- Deploy avançado com Docker.

---

## 16. Versão Final Esperada do MVP

Ao final do MVP, a API deve permitir:

```txt
Adicionar entradas e gastos.
Listar histórico financeiro.
Editar transações.
Apagar transações.
Ver saldo atual.
Ver total de entradas.
Ver total de gastos.
Exportar relatório em PDF.
Guardar os dados em SQLite.
```

---

## 17. Resumo Final

O MVP do **Dad Needs Report** deve ser simples, funcional e direto.

A prioridade é entregar uma API REST com CRUD de transações, dashboard financeiro e exportação em PDF.

A arquitetura deve permanecer simples:

```txt
Controller → Service → Repository → SQLite
```

Depois do MVP funcionar, o projeto pode evoluir para ter frontend, autenticação, gráficos e hospedagem.

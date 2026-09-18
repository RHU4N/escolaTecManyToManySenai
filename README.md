# EscolaTec — API de Alunos e Cursos

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de alunos, cursos e matrículas.

O projeto tem como principal objetivo demonstrar a implementação de um relacionamento **Many-to-Many (`@ManyToMany`)** utilizando **Spring Data JPA** e **MySQL**, além da utilização de **DTOs** para controlar os dados enviados e retornados pela API.

---

## 📌 Sobre o projeto

A aplicação representa uma escola técnica onde:

* Um **aluno** pode estar matriculado em vários cursos;
* Um **curso** pode possuir vários alunos;
* As matrículas são armazenadas em uma tabela intermediária chamada `matricula`;
* É possível realizar o CRUD completo de alunos;
* É possível realizar o CRUD completo de cursos;
* É possível realizar e remover matrículas individualmente;
* Os retornos da API utilizam DTOs para evitar loops infinitos durante a serialização JSON.

### Relacionamento

```text
┌──────────────┐        ┌──────────────┐
│    Alunos    │        │    Cursos    │
├──────────────┤        ├──────────────┤
│ id           │        │ id           │
│ nome         │        │ nome         │
│ email        │        │ cargaHoraria │
└──────┬───────┘        └───────┬──────┘
       │                         │
       │       Many-to-Many      │
       └──────────┬──────────────┘
                  │
           ┌──────▼──────┐
           │  matricula  │
           ├─────────────┤
           │ aluno_id    │
           │ curso_id    │
           └─────────────┘
```

A tabela `matricula` possui uma restrição de unicidade para impedir que o mesmo aluno seja matriculado duas vezes no mesmo curso.

---

## 🚀 Tecnologias utilizadas

* **Java 25**
* **Spring Boot 4.1.1**
* **Spring Web MVC**
* **Spring Data JPA**
* **Jakarta Validation**
* **Hibernate**
* **MySQL**
* **Lombok**
* **Maven**

---

## 📂 Estrutura do projeto

```text
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── biolab/
│   │           └── escolatec/
│   │               ├── controllers/
│   │               │   ├── AlunoController.java
│   │               │   └── CursoController.java
│   │               │
│   │               ├── DTOs/
│   │               │   ├── Aluno/
│   │               │   │   ├── AlunoReq.java
│   │               │   │   ├── AlunoResp.java
│   │               │   │   └── AlunoCursoResp.java
│   │               │   │
│   │               │   └── Curso/
│   │               │       ├── CursoReq.java
│   │               │       ├── CursoResp.java
│   │               │       └── CursoAlunoResp.java
│   │               │
│   │               ├── entities/
│   │               │   ├── Alunos.java
│   │               │   └── Cursos.java
│   │               │
│   │               ├── repositories/
│   │               │   ├── AlunoRepository.java
│   │               │   └── CursoRepository.java
│   │               │
│   │               ├── services/
│   │               │   ├── AlunoService.java
│   │               │   └── CursoService.java
│   │               │
│   │               └── EscolaTecApplication.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/
```

---

# ⚙️ Configuração do ambiente

## 1. Pré-requisitos

Antes de executar o projeto, tenha instalado:

* Java 25 ou superior compatível;
* MySQL;
* Maven ou utilize o Maven Wrapper disponibilizado no projeto;
* Git.

---

## 2. Clonar o projeto

```bash
git clone https://github.com/RHU4N/escolaTecManyToManySenai.git
```

Entre na pasta:

```bash
cd escolaTecManyToManySenai
```

---

## 3. Configurar o banco de dados

O projeto está configurado para utilizar o banco:

```text
escolaTec
```

A configuração atual utiliza:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/escolaTec?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
```

Caso seu usuário ou senha do MySQL sejam diferentes, altere o arquivo:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/escolaTec?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=SUA_SENHA
```

O Hibernate está configurado para atualizar automaticamente a estrutura do banco:

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

# ▶️ Executando o projeto

## Windows

Utilizando o Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

Ou:

```powershell
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run
```

## Linux / macOS

```bash
./mvnw spring-boot:run
```

Ou:

```bash
./mvnw clean compile
./mvnw spring-boot:run
```

A API será executada em:

```text
http://localhost:8080
```

---

# 🧱 Arquitetura

O projeto utiliza uma separação baseada em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Controller

Responsável por:

* Receber requisições HTTP;
* Validar os dados recebidos;
* Definir os endpoints;
* Retornar os códigos HTTP apropriados.

Localização:

```text
controllers/
```

### Service

Responsável pelas regras de negócio.

Exemplos:

* Criar alunos;
* Criar cursos;
* Atualizar registros;
* Realizar matrículas;
* Remover matrículas;
* Sincronizar os dois lados do relacionamento `ManyToMany`.

Localização:

```text
services/
```

### Repository

Utiliza `JpaRepository` para realizar a comunicação com o banco de dados.

Localização:

```text
repositories/
```

### Entity

Representa as tabelas e relacionamentos do banco.

Localização:

```text
entities/
```

### DTO

Os DTOs são utilizados para separar os objetos utilizados nas requisições dos objetos retornados pela API.

Localização:

```text
DTOs/
```

---

# 🔗 Relacionamento Many-to-Many

O relacionamento principal do projeto é:

```text
Aluno N:N Curso
```

Na entidade `Alunos`, o relacionamento é definido como:

```java
@ManyToMany
@JoinTable(
    name = "matricula",
    joinColumns = @JoinColumn(name = "aluno_id"),
    inverseJoinColumns = @JoinColumn(name = "curso_id")
)
private Set<Cursos> cursos = new HashSet<>();
```

Na entidade `Cursos`:

```java
@ManyToMany(mappedBy = "cursos")
private Set<Alunos> alunos = new HashSet<>();
```

Dessa forma, a tabela intermediária é:

```text
matricula
```

Com as colunas:

```text
aluno_id
curso_id
```

Também existe uma restrição de unicidade:

```text
uk_aluno_curso
```

que impede duplicação da mesma matrícula.

---

# 📦 DTOs

O projeto utiliza DTOs para evitar que as entidades sejam retornadas diretamente em todas as situações.

Isso é especialmente importante no relacionamento `ManyToMany`, pois retornar diretamente:

```text
Aluno → Cursos → Alunos → Cursos → ...
```

poderia gerar um loop durante a serialização JSON.

Por isso existem DTOs específicos para as relações.

### Aluno

```text
AlunoReq
AlunoResp
AlunoCursoResp
```

### Curso

```text
CursoReq
CursoResp
CursoAlunoResp
```

---

# 👨‍🎓 Endpoints de Alunos

## Criar aluno

```http
POST /aluno
```

### Body

```json
{
  "nome": "Rhuan",
  "email": "rhuan@email.com",
  "idCursos": [1, 2]
}
```

### Resposta

```json
{
  "id": 1,
  "nome": "Rhuan",
  "email": "rhuan@email.com",
  "cursos": [
    {
      "id": 1,
      "nome": "Desenvolvimento de Sistemas",
      "cargaHoraria": "1200"
    },
    {
      "id": 2,
      "nome": "Banco de Dados",
      "cargaHoraria": "80"
    }
  ]
}
```

---

## Listar alunos

```http
GET /aluno
```

Retorna todos os alunos cadastrados.

---

## Buscar aluno por ID

```http
GET /aluno/{id}
```

Exemplo:

```http
GET /aluno/1
```

---

## Atualizar aluno

```http
PUT /aluno/{id}
```

Exemplo:

```http
PUT /aluno/1
```

### Body

```json
{
  "nome": "Rhuan Santana",
  "email": "rhuan@email.com",
  "idCursos": [1, 3]
}
```

O campo `idCursos` representa os cursos que deverão permanecer associados ao aluno após a atualização.

---

## Deletar aluno

```http
DELETE /aluno/{id}
```

Exemplo:

```http
DELETE /aluno/1
```

Retorno:

```http
204 No Content
```

---

# 🎓 Endpoints de Cursos

## Criar curso

```http
POST /curso
```

### Body

```json
{
  "nome": "Desenvolvimento de Sistemas",
  "cargaHoraria": "1200",
  "idAlunos": []
}
```

Também é possível criar um curso já associando alunos:

```json
{
  "nome": "Desenvolvimento Web",
  "cargaHoraria": "80",
  "idAlunos": [1, 2]
}
```

---

## Listar cursos

```http
GET /curso
```

Retorna todos os cursos cadastrados.

---

## Buscar curso por ID

```http
GET /curso/{id}
```

Exemplo:

```http
GET /curso/1
```

A resposta também apresenta os alunos associados ao curso.

---

## Atualizar curso

```http
PUT /curso/{id}
```

Exemplo:

```http
PUT /curso/1
```

### Body

```json
{
  "nome": "Desenvolvimento de Sistemas",
  "cargaHoraria": "1400",
  "idAlunos": [1, 2, 3]
}
```

O campo `idAlunos` define os alunos que deverão permanecer matriculados no curso.

---

## Deletar curso

```http
DELETE /curso/{id}
```

Exemplo:

```http
DELETE /curso/1
```

Antes de excluir o curso, as associações com os alunos são removidas.

---

# 📝 Matrículas

Além do CRUD tradicional, a API possui endpoints específicos para gerenciar matrículas.

## Matricular aluno em curso

```http
POST /aluno/{alunoId}/cursos/{cursoId}
```

Exemplo:

```http
POST /aluno/1/cursos/2
```

Resposta:

```text
Aluno matriculado com sucesso
```

Status:

```http
201 Created
```

---

## Remover matrícula

```http
DELETE /aluno/{alunoId}/cursos/{cursoId}
```

Exemplo:

```http
DELETE /aluno/1/cursos/2
```

Status:

```http
204 No Content
```

---

# 🚦 Principais códigos HTTP

| Código                      | Situação                                  |
| --------------------------- | ----------------------------------------- |
| `200 OK`                    | Operação realizada com sucesso            |
| `201 Created`               | Recurso ou matrícula criada               |
| `204 No Content`            | Recurso removido com sucesso              |
| `400 Bad Request`           | Dados inválidos ou operação não permitida |
| `404 Not Found`             | Aluno ou curso não encontrado             |
| `409 Conflict`              | Matrícula já existente                    |
| `500 Internal Server Error` | Erro interno inesperado                   |

---

# 🧪 Exemplos de testes

Uma sequência recomendada para testar a API é:

### 1. Criar curso

```http
POST /curso
```

```json
{
  "nome": "Desenvolvimento de Sistemas",
  "cargaHoraria": "1200",
  "idAlunos": []
}
```

### 2. Criar outro curso

```http
POST /curso
```

```json
{
  "nome": "Banco de Dados",
  "cargaHoraria": "80",
  "idAlunos": []
}
```

### 3. Criar aluno associado aos cursos

```http
POST /aluno
```

```json
{
  "nome": "Rhuan",
  "email": "rhuan@email.com",
  "idCursos": [1, 2]
}
```

### 4. Consultar aluno

```http
GET /aluno/1
```

O retorno deverá apresentar os cursos:

```json
{
  "id": 1,
  "nome": "Rhuan",
  "email": "rhuan@email.com",
  "cursos": [
    {
      "id": 1,
      "nome": "Desenvolvimento de Sistemas",
      "cargaHoraria": "1200"
    },
    {
      "id": 2,
      "nome": "Banco de Dados",
      "cargaHoraria": "80"
    }
  ]
}
```

### 5. Consultar curso

```http
GET /curso/1
```

O curso deverá apresentar os alunos associados:

```json
{
  "id": 1,
  "nome": "Desenvolvimento de Sistemas",
  "cargaHoraria": "1200",
  "alunos": [
    {
      "id": 1,
      "nome": "Rhuan",
      "email": "rhuan@email.com"
    }
  ]
}
```

---

# 🔄 Fluxo de matrícula

A matrícula pode ser realizada diretamente através do endpoint específico:

```text
POST /aluno/{alunoId}/cursos/{cursoId}
```

Por exemplo:

```text
POST /aluno/1/cursos/2
```

Fluxo:

```text
                ┌───────────────┐
                │    Aluno      │
                │      1        │
                └───────┬───────┘
                        │
                        │ matrícula
                        ▼
                ┌───────────────┐
                │   matricula   │
                ├───────────────┤
                │ aluno_id = 1  │
                │ curso_id = 2  │
                └───────┬───────┘
                        │
                        ▼
                ┌───────────────┐
                │    Curso      │
                │      2        │
                └───────────────┘
```

---

# 🛡️ Validações

O projeto utiliza Jakarta Validation.

### Aluno

O nome não pode estar vazio:

```java
@NotBlank
private String nome;
```

O e-mail deve possuir formato válido:

```java
@Email
@NotBlank
private String email;
```

A lista de cursos deve ser informada:

```java
@NotNull
private List<Long> idCursos;
```

### Curso

O nome é obrigatório:

```java
@NotBlank
private String nome;
```

A carga horária é obrigatória:

```java
@NotBlank
private String cargaHoraria;
```

Os IDs dos alunos devem ser informados:

```java
@NotNull
private List<Long> idAlunos;
```

---

# 🗄️ Banco de dados

O banco utilizado é o **MySQL**.

A estrutura principal é composta por:

```text
alunos
 ├── id
 ├── nome
 └── email

cursos
 ├── id
 ├── nome
 └── carga_horaria

matricula
 ├── aluno_id
 └── curso_id
```

Relacionamento:

```text
ALUNOS
   │
   │ 1:N
   ▼
MATRICULA
   ▲
   │ N:1
   │
CURSOS
```

Logicamente, o relacionamento entre `Alunos` e `Cursos` é:

```text
Many-to-Many
```

---

# 📚 Conceitos trabalhados

Este projeto foi desenvolvido com foco no estudo e aplicação prática de:

* API REST;
* Spring Boot;
* Spring Data JPA;
* Hibernate;
* MySQL;
* CRUD;
* DTOs;
* Validação de dados;
* Injeção de dependência;
* Arquitetura em camadas;
* Relacionamento `ManyToMany`;
* Tabela associativa;
* JPA `@JoinTable`;
* JPA `mappedBy`;
* Controle de matrículas;
* Códigos de status HTTP;
* Serialização JSON;
* Prevenção de loops em relacionamentos bidirecionais.

---

# 👨‍💻 Autor

Desenvolvido por **RHU4N**.

GitHub:

https://github.com/RHU4N

Repositório:

https://github.com/RHU4N/escolaTecManyToManySenai

---

# 📄 Licença

Este projeto foi desenvolvido para fins **educacionais e acadêmicos**.

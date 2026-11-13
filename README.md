# CONEXÃO PET! 🐾
Plataforma web para divulgação e consulta de animais disponíveis para adoção.

# Abordando o projeto
O CONEXÃO PET é um projeto acadêmico desenvolvido com o objetivo de proporcionar um ambiente digital para a divulgação de animais disponíveis para adoção, conectando pessoas e instituições que desejam disponibilizar animais a pessoas interessadas em adotá-los.
A plataforma busca centralizar as informações dos animais, facilitando sua divulgação, consulta e localização, além de contribuir para ampliar as oportunidades de adoção responsável.

# Tecnologias e Ferramentas utilizadas
| Tecnologia | Finalidade |
|:---|:---|
| Java | Linguagem de programação do backend |
| Spring Boot | Desenvolvimento da API REST |
| Spring Data JPA | Persistência e acesso aos dados |
| Spring Security | Segurança e autenticação da aplicação |
| JWT | Autenticação baseada em tokens |
| MySQL | Banco de dados relacional para desenvolvimento local |
| TiDB Cloud | Plataforma de banco de dados em nuvem |
| Docker | Container e padronização do ambiente de execução |
| HTML5 | Estrutura das páginas web |
| CSS3 | Estilização e layout da interface |
| JavaScript | Funcionalidades e interatividade |
| React | Desenvolvimento da interface frontend |
| Maven | Gerenciamento de dependências do Spring |

## Funcionalidades
Cadastro de usuários e instituições.

- Autenticação por e-mail ou nome de usuário.
- Criptografia de senhas.
- Autenticação utilizando JWT.
- Cadastro de animais por meio da criação de anúncios.
- Listagem de animais disponíveis para adoção.
- Visualização dos detalhes dos anúncios.
- Edição e exclusão de anúncios pelo responsável.
- Controle de acesso aos anúncios de cada usuário

## Arquitetura do projeto
- Controller: recebe e processa as requisições HTTP.
- Service: concentra as regras de negócio da aplicação.
- Repository: responsável pelo acesso e pela persistência dos dados.
- Entity: representa as entidades e seu mapeamento com o banco de dados.
- DTO: define os objetos utilizados para transferência de dados entre as camadas.

## 🚀 Como executar o projeto?

### 1. Pré-requisitos
- Java JDK 25
- MySQL
- Git

### 2. Clonar o repositório
```bash
git clone https://github.com/dogasdev/conexao-pet.git
cd conexao-pet
```

### 3. Configurar o banco de dados

No MySQL, execute:
```sql
CREATE DATABASE conexao_pet;
```
Configure as credenciais do banco e a variável `JWT_SECRET` no ambiente da aplicação.

### 4. Executar o projeto

```bash
.\mvnw.cmd spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

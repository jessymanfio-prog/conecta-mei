# Conecta MEI — Java 21 + Spring Boot 3.4.5

Projeto de Extensão (PEX), alinhado ao ODS **Trabalho Decente e Crescimento Econômico**.
Organização parceira proposta: **RCS Music Entertainment LTDA**.

## Tecnologias
- Java 21, Spring Boot 3.4.5, Maven
- Spring Web, Spring Data JPA, Bean Validation, Actuator
- H2 em arquivo **por padrão** (para rodar sem instalar banco) ou MySQL via profile
- HTML, CSS, JavaScript — hospedados no próprio Spring Boot
- JUnit 5 e MockMvc

## Como iniciar no IntelliJ
1. Descompacte e abra a pasta `Conecta_MEI_Spring_Boot` no IntelliJ como projeto Maven.
2. Configure o **Project SDK para Java 21**. Aguarde baixar dependências Maven (requer internet).
3. Execute `src/main/java/br/com/conectamei/ConectaMeiApplication.java`.
4. Abra **http://localhost:8080**. O dashboard e os cadastros usam APIs REST do backend.

Alternativa no terminal com Maven instalado: `mvn spring-boot:run`.
Teste: `mvn test`.

## MySQL (opcional)
1. Crie um banco vazio: `CREATE DATABASE conecta_mei CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`
2. Configure as variáveis `SPRING_PROFILES_ACTIVE=mysql`, `DB_URL=jdbc:mysql://localhost:3306/conecta_mei`, `DB_USERNAME` e `DB_PASSWORD`.
3. Execute novamente. Não coloque senha real no GitHub.

## Endpoints
| Método | Rota | Descrição |
|---|---|---|
| GET | /api/transactions | Lista lançamentos |
| POST | /api/transactions | Inclui lançamento |
| DELETE | /api/transactions/{id} | Remove lançamento |
| GET | /api/products | Lista produtos e serviços |
| POST | /api/products | Inclui produto ou serviço |
| DELETE | /api/products/{id} | Remove item |
| GET | /api/dashboard?month=2026-11 | Indicadores mensais |
| GET | /actuator/health | Status do serviço |

### Exemplo POST /api/transactions
```json
{"description":"Venda de serviço","type":"RECEITA","category":"Serviços","amount":150.00,"date":"2026-11-04"}
```

## Arquitetura
`Controller -> Service -> Repository -> JPA -> H2 ou MySQL`.

## Limites e segurança
- **Protótipo acadêmico**: sem login, controle de acesso, isolamento de clientes, auditoria ou migrations.
- **Não disponibilize esta versão em endereço público** nem utilize dados reais/sensíveis. Para implantar na internet, é necessário autenticação/autorização, proteção dos dados conforme LGPD, HTTPS, gestão de secrets e revisão de segurança.
- O backend usa banco H2 em arquivo local por padrão, **não** nuvem. Não há hospedagem online nem link público criados automaticamente.
- Os dados demonstrativos não são pré-cadastrados; a primeira abertura inicia com banco vazio.
- Maven depende de acesso aos repositórios para baixar dependências.

## PEX — comprovação
Para entrega acadêmica, documente entrevista/levantamento de necessidades, aplicação e treinamento com o público, avaliação de resultados e evidências reais (com autorização), além dos prints do protótipo.
# conecta-mei
# conecta-mei
# conecta-mei
# conecta-mei

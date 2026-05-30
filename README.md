# full-stack-activity-1

## Stack principal
- **Backend (Java)**: Spring Boot
- **API REST**: Spring MVC
- **Persistência**: Spring Data JPA
- **Banco de dados (dev)**: H2 (in-memory) + H2 Console
- **Frontend**: arquivo **estático** servido pelo Spring (**static/index.html**) com **HTML + CSS + JavaScript (fetch)**

---

## Como funciona
Ao iniciar o backend, o Spring Boot:
1. Serve a API REST nos endpoints documentados abaixo.
2. Serve a interface web em:
   - `http://localhost:8080/` (arquivos em `backend/src/main/resources/static/`)

---

## Endpoints — Exercício 1 (ExerciseController)

### 1) GET `/hello/{nome}`
Retorna:
- `Olá, {nome}!`

Exemplo:
```bash
curl http://localhost:8080/hello/Logesh
```

---

### 2) GET `/calc/soma?a=&b` **(query)** ou `GET /calc/soma/{a}/{b}` **(path)**
Retorna:
- `{"resultado": <soma>}`

Query:
```bash
curl "http://localhost:8080/calc/soma?a=10&b=5"
```

Path:
```bash
curl "http://localhost:8080/calc/soma/10/5"
```

Se faltar **a** ou **b** na query (ex.: falta `a`):
```bash
curl "http://localhost:8080/calc/soma?b=5"
```
➡️ deve retornar **HTTP 400**.

---

### 3) GET `/temperatura/convert?valor=&de=&para=`
Converte temperatura e valida unidades:
- suportadas: **C**, **F**, **K**
- unidade inválida: **HTTP 400**

Exemplo:
```bash
curl "http://localhost:8080/temperatura/convert?valor=30&de=C&para=F"
```

---

## Endpoints — Exercício 2 (Pessoa CRUD / PessoaController)

### Base
- `/pessoa/{id}`

> Observação: a interface web usa `fetch` para chamar `GET /pessoa`, `PUT /pessoa/{id}`, `PATCH /pessoa/{id}` e `DELETE /pessoa/{id}`.

---

### PUT `/pessoa/{id}`
Substitui os dados (mantém/usa o `id` da rota e atualiza os campos enviados, como `nome`).
- Se não existir, cria.

Exemplo:
```bash
curl -X PUT "http://localhost:8080/pessoa/1" \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Alice\"}"
```

---

### PATCH `/pessoa/{id}`
Atualiza parcialmente (ex.: só `nome`).

Exemplo:
```bash
curl -X PATCH "http://localhost:8080/pessoa/1" \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Alice Souza\"}"
```

---

### DELETE `/pessoa/{id}`
Remove e retorna **204 (No Content)**.

Exemplo:
```bash
curl -i -X DELETE "http://localhost:8080/pessoa/1"
```

---

## Frontend (GUI)
- A GUI fica em: `backend/src/main/resources/static/index.html`
- A página `http://localhost:8080/` deve carregar essa interface automaticamente.

Se você editar outro HTML fora disso (ex.: `frontend-gui/index.html`), **não é garantido** que vá refletir no que o backend está servindo.

---

## Como executar

### Pré-requisitos
- **Java** (JDK)
- **Maven** (`mvn`)

### Windows (Maven)
Se `mvn` não estiver instalado:
```powershell
.\scripts\setup-maven.ps1
```

### Rodar backend
```powershell
cd backend
mvn spring-boot:run
```

Servidor:
- `http://localhost:8080`

---

## H2 Console
URL:
- `http://localhost:8080/h2-console`

Config:
- JDBC URL: `jdbc:h2:mem:atividade`
- User: `sa`
- Password: *(em branco)*

---

## Dicas de debug rápido (GUI)
- Se os botões não funcionarem, abra o DevTools do navegador e verifique:
  - Erros de **console** (JavaScript)
  - Respostas **HTTP** (Network)
- O `DELETE /pessoa/{id}` deve retornar **204** e o JavaScript trata **204 como sucesso sem corpo**.

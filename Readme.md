# Insight.ia
Uma ferramenta para gerar anuncios

## Banco de Dados
Para criar as tabelas e estruturas necessárias do banco de dados, rodar o script do arquivo *create.sql* localizado na raiz do projeto

## Variaveis de Ambiente
Essas variaveis de ambiente são **obrigatórias** para uso da API e deploy para produção

| Nome | Valor | Obrigatório |
|------|-------|-------------|
| API_KEY_OPENAI | Uma chave da OpenAI para utilização do ChatGPT | Sim |
| ACTIVE_PROFILE | Possíveis valores: dev ou prod | Não |
| DB_USER_FIAP | Username do banco de dados ORACLE | Somente caso ACTIVE_PROFILE tenha o valor prod |
| DB_PASSWORD_FIAP | Senha do banco de dados ORACLE | Somente caso ACTIVE_PROFILE tenha o valor prod |

## Endpoints
- Usuario
    - [Cadastrar](#cadastro)
    - [Recuperar Senha](#recuperarsenha)
    - [Logar](#logar)
    - [Editar](#editar)
- Anuncio
    - [Cadastrar](#cadastrar-anuncio)
    - [Remover](#Remover-Anuncio)
    - [Listar Todos Anuncios Por Usuario](#ListarTodosAnunciosByUsuario)
- Insigth
    - [Listar Todos Insigths Por Anuncio](#Listar-Todos-Insigths-Por-Anuncio)
    - [Buscar Ultimo Insigth Por Anuncio](#Buscar-Ultimo-Insigth-Por-Anuncio)
- Comando
    - [Cadastrar Comando](#Cadastrar-Comando)
    - [Listar Todos Comandos Por Anuncio](#)
- Transacao
    - [Cadastrar](#Cadastro-Transacao)
    - [Listar Todas Transacaos Por Usuario](#Listar-Todas-Transacoes-Por-Usuario)


### Cadastro

`POST` /api/usuario/cadastrar

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|nome|String|sim|nome do usuário|
|email|String|sim|email válido do usuário|
|senha|String|sim|senha do usuário (tamanho minimo 5)|

**Exemplo de corpo de requisição**

```js
{
    "nome": "Pedro Henrique Vidal",
    "email": "rm93567@fiap.com.br",
    "senha": "abc123"
}
```

**Respostas**

|código | descrição |
|-|-
|201 | cadastro realizado
|409 | email já cadastrado
|400 | campos invalidos




### Login

`POST` /api/usuario/login

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|email|String|sim|email do usuário|
|senha|String|sim|senha do usuário|

**Exemplo de corpo de requisição**

```js
{
    "email": "rm93567@fiap.com.br",
    "Senha": "abc123"
}
```

**Exemplo de corpo de resposta**

```js
{
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJybTkzMDkwQGZpYXAuY29tLmJyIiwiaXNzIjoiSW5zaWdodC5pYSIsImV4cCI6MzE1NTY4ODk4NjQ0MDMxOTl9.iDH8at9EtMtrOxi21rIDY859KIfeD--69aABA9CsL9s",
	"type": "JWT",
	"prefix": "Bearer"
}
```

**Respostas**

|código | descrição |
|-|-
|200 | Token gerado
|404 | Usuario não encontrado


## Buscar Perfil

`GET` /api/usuario

**Exemplo de cabeçalho da requisição**

```js
{
    "Authorization": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJybTkzMDkwQGZpYXAuY29tLmJyIiwiaXNzIjoiSW5zaWdodC5pYSIsImV4cCI6MzE1NTY4ODk4NjQ0MDMxOTl9.iDH8at9EtMtrOxi21rIDY859KIfeD--69aABA9CsL9s"
}
```

**Exemplo de corpo de resposta**

```js
{
	"id": 1,
	"nome": "Gustavo Balero",
	"email": "rm93090@fiap.com.br",
	"saldo": 18.0
}
```

**Respostas**

|código | descrição |
|-|-
|200 | Token gerado
|404 | Usuario não encontrado

### Editar

`PUT` /Insight/api/usuario/editar

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|id|Integer|sim|id do usuário|
|nome|String|sim|nome do usuário|
|email|String|sim|email do usuário|
|senha|String|sim|senha do usuário|

**Exemplo de corpo de requisição**

```js
{
    "id": "01"
    "nome": "Pedro Henrique Vidal",
    "email": "rm93567@fiap.com.br",
    "senha": "abcd"
}
```

**Respostas**

|código | descrição |
|-|-
|202 | Edicao realizada
|400 | campo invalido
|404 | usuario não encontrado


### Cadastrar Anuncio

`POST` /Insight/api/anuncio/cadastrar

| campo | tipo | obrigatório | descrição 
|-------|------|:-------------:|---
|descricao | String | sim | Descrição do anuncio que o usuario quer criar

**Exemplo de corpo de requisição**

```
{
    "descricao": "Petshop"
}
```

**Respostas**

|código | descrição |
|-|-
|201 | Anuncio cadastrado
|400 | Campo invalido


### Remover Anuncio

`REMOVE` /Insight/api/anuncio/remover

| campo | tipo | obrigatório | descrição 
|-------|------|:-------------:|---
|Id | Integer | sim | Id do anuncio

**Exemplo de corpo de requisição**

```js
{
    "Id": "1"
}
```
**Respostas**

|código | descrição |
|-|-
|204 | Anuncio deletado com sucesso
|400 | Campo invalido
|404 | Anuncio não encontrado


### ListarTodosAnunciosByUsuario

`GET` /Insight/api/anuncio/listartodosbyusuario/{id}

| campo | tipo | obrigatório | descrição 
|-------|------|:-------------:|---
|Id | Integer | sim | Id do usuario

**Exemplo de corpo de resposta**

```js
{

    "anuncios":[
        {
            "Descricao": "anuncio 1",
            "Insights": [],
            "Comandos": []

        },
        {
            "Descricao": "anuncio 2",
            "Insights": [],
            "Comandos": []
        },
        {
            "Descricao": "anuncio 3",
            "Insights": [],
            "Comandos": []
        }
    ]

}

```

**Códigos de Respostas**

| código | descrição
|-|-
| 200 | anuncios retornados
| 404 | usuario não encontrado


### Cadastro Transacao

`POST` /Insight/api/transacao/cadastro/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|id do usuário|
|Titulo|String|sim|nome do usuário|
|Descricao|String|não|email do usuário|
|DataCadastro|Calendar|sim|senha do usuário|
|Valor|Double|sim|senha do usuário|

**Exemplo de corpo de requisição**

```js
{
    "Titulo": "Crédito Adicionado",
    "Descricao": null,
    "DataCadastro": '07/04/2023',
    "Valor": 150.00
}
```
**Códigos de Respostas**

| código | descrição
|-|-
| 201 | transação cadastrada
| 400 | campos invalidos
| 404 | usuario não encontrado

### Listar Todas Transacoes Por Usuario

`GET` /Insight/api/transacao/listartodosbyusuario/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do usuário|

```js
{
    "transacoes": [
        {
            "Titulo": "Crédito Adicionado",
            "Descricao": null,
            "DataCadastro": '07/04/2023',
            "Valor": 150.00
        },
        {
            "Titulo": "Insight Gerado",
            "Descricao": "Anuncio: Petshop",
            "DataCadastro": '08/04/2023',
            "Valor": -2.00
        }
    ]
}
```
 **Códigos de Respostas**

| código | descrição
|-|-
| 200 | transações listadas
| 400 | campo invalido
| 404 | usuario não encontrado


### Listar Todos Insigths Por Anuncio

`GET` /Insight/api/anuncio/listartodosbyanuncio/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do anuncio|
|Conteudo|String|sim|Conteudo do insight|
|Imagem|String|não|Imagem do insight|


```js
{
    "Insigths": [
        {
            "Conteudo": "Bem-vindo ao nosso petshop, onde o seu animal de estimação é tratado como da família! Nós sabemos que os seus bichinhos são muito mais do que apenas animais de estimação. Eles são membros da sua família e merecem todo o amor, carinho e atenção que você pode oferecer. É por isso que oferecemos uma ampla variedade de serviços de qualidade para ajudá-lo a cuidar do seu animal de estimação.",
            "Imagem": null
        },
        {
             "Conteudo": "Bem-vindo à nossa oficina mecânica, onde o seu carro é tratado com excelência! Sabemos que o seu carro é uma parte importante da sua vida, e é por isso que estamos aqui para ajudá-lo a mantê-lo em perfeitas condições. Oferecemos uma ampla variedade de serviços de manutenção e reparo, realizados por uma equipe de profissionais altamente qualificados e experientes.",
            "Imagem": null
        }
    ]
}
```

 **Códigos de Respostas**

| código | descrição
|-|-
| 200 | Insigths listados
| 400 | campo invalido
| 404 | anuncio não encontrado

### Buscar Ultimo Insigth Por Anuncio

`GET` /Insight/api/anuncio/buscarultimoinsigthporanuncio/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do anuncio|
|Conteudo|String|sim|Conteudo do insight|
|Imagem|String|não|Imagem do insight|

```js
{
    "Insigths": [
        {
             "Conteudo": "Bem-vindo à nossa oficina mecânica, onde o seu carro é tratado com excelência! Sabemos que o seu carro é uma parte importante da sua vida, e é por isso que estamos aqui para ajudá-lo a mantê-lo em perfeitas condições. Oferecemos uma ampla variedade de serviços de manutenção e reparo, realizados por uma equipe de profissionais altamente qualificados e experientes.",
            "Imagem": null
        }
    ]
}
```

**Códigos de Respostas**

| código | descrição
|-|-
| 200 | Insigths listados
| 400 | campo invalido
| 404 | anuncio não encontrado

### Cadastrar Comando

`POST` /Insight/api/anuncio/cadastrarcomando

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do anuncio|
|Conteudo|String|sim|Conteudo do anuncio|

**Exemplo de corpo de requisição**

```js
{
    "conteudo": "Bem-vindo à nossa oficina mecânica, onde o seu carro é tratado com excelência! Sabemos que o seu carro é uma parte importante da sua vida, e é por isso que estamos aqui para ajudá-lo a mantê-lo em perfeitas condições. Oferecemos uma ampla variedade de serviços de manutenção e reparo, realizados por uma equipe de profissionais altamente qualificados e experientes."
}
```

**Respostas**

|código | descrição |
|-|-
|201 | comando enviado
|400 | conteudo invalidos

### Listar Todos Comandos Por Anuncio

`GET` /Insight/api/anuncio/listartodoscomandosporanuncio/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do anuncio|
|Conteudo|String|sim|Conteudo do anuncio|

```js
{
    "comando": [
        {
             "Conteudo": "Bem-vindo à nossa oficina mecânica, onde o seu carro é tratado com excelência! Sabemos que o seu carro é uma parte importante da sua vida, e é por isso que estamos aqui para ajudá-lo a mantê-lo em perfeitas condições. Oferecemos uma ampla variedade de serviços de manutenção e reparo, realizados por uma equipe de profissionais altamente qualificados e experientes.",
        }
    ]
}
```

**Códigos de Respostas**

| código | descrição
|-|-
| 200 | comandos listados
| 400 | campo invalido
=======
# Insight.ia
Uma ferramenta para gerar anuncios

## Endpoints
- Usuario
    - [Cadastrar](#cadastro)
    - [Recuperar Senha](#recuperarsenha)
    - [Logar](#logar)
    - [Editar](#editar)
- Anuncio
    - [Cadastrar](#cadastrar-anuncio)
    - [Remover](#Remover-Anuncio)
    - [Listar Todos Anuncios Por Usuario](#ListarTodosAnunciosByUsuario)
- Insigth
    - [Listar Todos Insigths Por Anuncio](#Listar-Todos-Insigths-Por-Anuncio)
    - [Buscar Ultimo Insigth Por Anuncio](#Buscar-Ultimo-Insigth-Por-Anuncio)
- Comando
    - [Cadastrar Comando](#Cadastrar-Comando)
    - [Listar Todos Comandos Por Anuncio](#)
- Transacao
    - [Cadastrar](#Cadastro-Transacao)
    - [Listar Todas Transacaos Por Usuario](#Listar-Todas-Transacoes-Por-Usuario)


### Cadastro

`POST` /Insight/api/usuario/cadastro

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|nome|String|sim|nome do usuário|
|email|String|sim|email do usuário|
|senha|String|sim|senha do usuário|

**Exemplo de corpo de requisição**

```js
{
    "nome": "Pedro Henrique Vidal",
    "email": "rm93567@fiap.com.br",
    "senha": "qwer"
}
```

**Respostas**

|código | descrição |
|-|-
|201 | cadastro concluido
|400 | campos invalidos



### RecuperarSenha

`POST` /Insight/api/usuario/recuperarsenha

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Email | String | sim | Email do usuário |


**Exemplo de corpo de requisição**

```js
{
    "Email": "rm93567@fiap.com.br"
}
```

**Respostas**

|código | descrição |
|-|-
|200 | email de recuperação de senha enviado
|400 | email invalido
|400 | usuario não encontrado


### Logar

`POST` /Insight/api/usuario/logar

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|email|String|sim|email do usuário|
|senha|String|sim|senha do usuário|

**Exemplo de corpo de requisição**

```js
{
    "email": "rm93567@fiap.com.br",
    "Senha": "qwer"
}
```

**Exemplo de corpo de resposta**

```js
{
    "Id": "01",
    "Nome": "Pedro Vidal"
}
```

**Respostas**

|código | descrição |
|-|-
|200 | Login concluido
|400 | Login invalido
|404 | Usuario não encontrado



### Editar

`PUT` /Insight/api/usuario/editar

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|id|Integer|sim|id do usuário|
|nome|String|sim|nome do usuário|
|email|String|sim|email do usuário|
|senha|String|sim|senha do usuário|

**Exemplo de corpo de requisição**

```js
{
    "id": "01"
    "nome": "Pedro Henrique Vidal",
    "email": "rm93567@fiap.com.br",
    "senha": "abcd"
}
```

**Respostas**

|código | descrição |
|-|-
|202 | Edicao realizada
|400 | campo invalido
|404 | usuario não encontrado


### Cadastrar Anuncio

`POST` /Insight/api/anuncio/cadastrar

| campo | tipo | obrigatório | descrição 
|-------|------|:-------------:|---
|descricao | String | sim | Descrição do anuncio que o usuario quer criar

**Exemplo de corpo de requisição**

```
{
    "descricao": "Petshop"
}
```

**Respostas**

|código | descrição |
|-|-
|201 | Anuncio cadastrado
|400 | Campo invalido


### Remover Anuncio

`REMOVE` /Insight/api/anuncio/remover

| campo | tipo | obrigatório | descrição 
|-------|------|:-------------:|---
|Id | Integer | sim | Id do anuncio

**Exemplo de corpo de requisição**

```js
{
    "Id": "1"
}
```
**Respostas**

|código | descrição |
|-|-
|204 | Anuncio deletado com sucesso
|400 | Campo invalido
|404 | Anuncio não encontrado


### ListarTodosAnunciosByUsuario

`GET` /Insight/api/anuncio/listartodosbyusuario/{id}

| campo | tipo | obrigatório | descrição 
|-------|------|:-------------:|---
|Id | Integer | sim | Id do usuario

**Exemplo de corpo de resposta**

```js
{

    "anuncios":[
        {
            "Descricao": "anuncio 1",
            "Insights": [],
            "Comandos": []

        },
        {
            "Descricao": "anuncio 2",
            "Insights": [],
            "Comandos": []
        },
        {
            "Descricao": "anuncio 3",
            "Insights": [],
            "Comandos": []
        }
    ]

}

```

**Códigos de Respostas**

| código | descrição
|-|-
| 200 | anuncios retornados
| 404 | usuario não encontrado


### Cadastro Transacao

`POST` /Insight/api/transacao/cadastro/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|id do usuário|
|Titulo|String|sim|nome do usuário|
|Descricao|String|não|email do usuário|
|DataCadastro|Calendar|sim|senha do usuário|
|Valor|Double|sim|senha do usuário|

**Exemplo de corpo de requisição**

```js
{
    "Titulo": "Crédito Adicionado",
    "Descricao": null,
    "DataCadastro": '07/04/2023',
    "Valor": 150.00
}
```
**Códigos de Respostas**

| código | descrição
|-|-
| 201 | transação cadastrada
| 400 | campos invalidos
| 404 | usuario não encontrado

### Listar Todas Transacoes Por Usuario

`GET` /Insight/api/transacao/listartodosbyusuario/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do usuário|

```js
{
    "transacoes": [
        {
            "Titulo": "Crédito Adicionado",
            "Descricao": null,
            "DataCadastro": '07/04/2023',
            "Valor": 150.00
        },
        {
            "Titulo": "Insight Gerado",
            "Descricao": "Anuncio: Petshop",
            "DataCadastro": '08/04/2023',
            "Valor": -2.00
        }
    ]
}
```
 **Códigos de Respostas**

| código | descrição
|-|-
| 200 | transações listadas
| 400 | campo invalido
| 404 | usuario não encontrado


### Listar Todos Insigths Por Anuncio

`GET` /Insight/api/anuncio/listartodosbyanuncio/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do anuncio|
|Conteudo|String|sim|Conteudo do insight|
|Imagem|String|não|Imagem do insight|


```js
{
    "Insigths": [
        {
            "Conteudo": "Bem-vindo ao nosso petshop, onde o seu animal de estimação é tratado como da família! Nós sabemos que os seus bichinhos são muito mais do que apenas animais de estimação. Eles são membros da sua família e merecem todo o amor, carinho e atenção que você pode oferecer. É por isso que oferecemos uma ampla variedade de serviços de qualidade para ajudá-lo a cuidar do seu animal de estimação.",
            "Imagem": null
        },
        {
             "Conteudo": "Bem-vindo à nossa oficina mecânica, onde o seu carro é tratado com excelência! Sabemos que o seu carro é uma parte importante da sua vida, e é por isso que estamos aqui para ajudá-lo a mantê-lo em perfeitas condições. Oferecemos uma ampla variedade de serviços de manutenção e reparo, realizados por uma equipe de profissionais altamente qualificados e experientes.",
            "Imagem": null
        }
    ]
}
```

 **Códigos de Respostas**

| código | descrição
|-|-
| 200 | Insigths listados
| 400 | campo invalido
| 404 | anuncio não encontrado

### Buscar Ultimo Insigth Por Anuncio

`GET` /Insight/api/anuncio/buscarultimoinsigthporanuncio/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do anuncio|
|Conteudo|String|sim|Conteudo do insight|
|Imagem|String|não|Imagem do insight|

```js
{
    "Insigths": [
        {
             "Conteudo": "Bem-vindo à nossa oficina mecânica, onde o seu carro é tratado com excelência! Sabemos que o seu carro é uma parte importante da sua vida, e é por isso que estamos aqui para ajudá-lo a mantê-lo em perfeitas condições. Oferecemos uma ampla variedade de serviços de manutenção e reparo, realizados por uma equipe de profissionais altamente qualificados e experientes.",
            "Imagem": null
        }
    ]
}
```

**Códigos de Respostas**

| código | descrição
|-|-
| 200 | Insigths listados
| 400 | campo invalido
| 404 | anuncio não encontrado

### Cadastrar Comando

`POST` /Insight/api/anuncio/cadastrarcomando

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do anuncio|
|Conteudo|String|sim|Conteudo do anuncio|

**Exemplo de corpo de requisição**

```js
{
    "conteudo": "Bem-vindo à nossa oficina mecânica, onde o seu carro é tratado com excelência! Sabemos que o seu carro é uma parte importante da sua vida, e é por isso que estamos aqui para ajudá-lo a mantê-lo em perfeitas condições. Oferecemos uma ampla variedade de serviços de manutenção e reparo, realizados por uma equipe de profissionais altamente qualificados e experientes."
}
```

**Respostas**

|código | descrição |
|-|-
|201 | comando enviado
|400 | conteudo invalidos

### Listar Todos Comandos Por Anuncio

`GET` /Insight/api/anuncio/listartodoscomandosporanuncio/{id}

|campo | tipo | obrigatório |descrição |
|------|------|:-------------:|----------|
|Id|Integer|sim|Id do anuncio|
|Conteudo|String|sim|Conteudo do anuncio|

```js
{
    "comando": [
        {
             "Conteudo": "Bem-vindo à nossa oficina mecânica, onde o seu carro é tratado com excelência! Sabemos que o seu carro é uma parte importante da sua vida, e é por isso que estamos aqui para ajudá-lo a mantê-lo em perfeitas condições. Oferecemos uma ampla variedade de serviços de manutenção e reparo, realizados por uma equipe de profissionais altamente qualificados e experientes.",
        }
    ]
}
```

**Códigos de Respostas**

| código | descrição
|-|-
| 200 | comandos listados
| 400 | campo invalido
| 404 | comando não encontrado
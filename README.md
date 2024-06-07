# Projeto-Curso-Inscricao-API

## Entidades

### Descrição da Classe Curso

A classe `Curso` é uma entidade JPA que representa um curso. Abaixo estão descritas suas características e validações:

#### Características

- **id**: Identificador único do curso.
    - Tipo: `Integer`
    - Descrição: Campo autoincrementado que serve como chave primária.

- **nome**: Nome do curso.
    - Tipo: `String`
    - Descrição: Campo obrigatório que não pode estar vazio ou em branco.

- **descricao**: Descrição do curso.
    - Tipo: `String`
    - Descrição: Campo obrigatório que não pode estar vazio ou em branco.

- **cargaHoraria**: Carga horária do curso.
    - Tipo: `Integer`
    - Descrição: Campo obrigatório que deve ser um número positivo.

#### Outras regras

- **nome**: Deve ser único. Independe de maiúsculas e minúsculas.

### Descrição da Classe Inscricao

A classe `Inscricao` é uma entidade JPA que representa uma inscrição em um curso. Abaixo estão descritas suas
características e validações:

### Características

- **id**: Identificador único da inscrição.
    - Tipo: `Integer`
    - Descrição: Campo autoincrementado que serve como chave primária.

- **nome**: Nome da pessoa inscrita.
    - Tipo: `String`
    - Descrição: Campo obrigatório que não pode estar vazio ou em branco.

- **email**: Email da pessoa inscrita.
    - Tipo: `String`
    - Descrição: Campo obrigatório que deve ser um endereço de email válido e não pode estar vazio ou em branco.

- **dataNascimento**: Data de nascimento da pessoa inscrita.
    - Tipo: `LocalDate`
    - Descrição: Campo obrigatório que deve conter uma data no passado.

- **dataInscricao**: Data da inscrição.
    - Tipo: `LocalDate`
    - Descrição: Campo obrigatório que deve conter uma data.

- **curso**: Curso associado à inscrição.
    - Tipo: `Curso`
    - Descrição: Representando que uma inscrição deve estar associada a um curso. Um curso pode ter muitas inscrições
      associadas a ele, e a relação não deve ser bidirecional.

#### Outras regras

- **email**: Deve ser único. Independe de maiúsculas e minúsculas.
- **dataNascimento**: o inscrito deve ter no mínimo 18 anos completos na data da inscrição, ou seja, a diferença entre a
  data de nascimento e a data da inscrição deve ser de no mínimo 18 anos.

<hr>

## Endpoints

### Cursos

#### GET /cursos

Retorna a lista de todos os cursos cadastrados.

#### GET /cursos/{id}

Retorna o curso com o id informado.

#### POST /cursos

Cadastra um novo curso. (Utilize a própria entidade `Curso` no corpo da requisição).
Ignore o campo `id` no corpo da requisição, pois o id será gerado automaticamente.

### Inscricões

#### GET /inscricoes

Retorna a lista de todas as inscrições cadastradas.

#### GET /inscricoes/{id}

Retorna a inscrição com o id informado.

#### POST /inscricoes/cursos/{idCurso}

Cadastra uma nova inscrição. (Utilize a própria entidade `Inscricao` no corpo da requisição)
Ignore o campo `curso` no corpo da requisição, pois o curso será informado através do id do curso.

#### DELETE /inscricoes/{id}

Remove APENAS a inscrição com o id informado, mantendo o curso cadastrado.

<h1 style="color: yellow;">Fim Enunciado:</h1>

<hr>


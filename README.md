# TECH CHALLENGE - DELIVERY MUCH

Em resposta ao desafio, foi desenvolvido uma aplicativo que tem a principal finalidade de exibir todos os repositórios públicos
do GitHub. Além dessa função principal é possivel pesquisar repósitorios pelo nome e visualizar mais detalhes.

**Configurações necessarias:**

- Min SDK: 21

# Passo a passo da construção

O aplicativo foi construído seguindo as seguintes guidelines recomendadas pelo Google:

- Material design
- MVVM

Um modelo simplificado da arquitetura MVVM pode ser visto abaixo

![final-architecture](app/src/main/res/mipmap-xxhdpi/final-architecture.png)

Também foi utilizado uma camada intermediária ao acesso de dados chamado de Repository.
O repository tem por objetivo abstrair o acesso aos dados, seja de banco de dados ou de um cache ou até mesmo de uma api como
foi o caso.

O fluxo de integração com sistema segue os seguintes passos:

1. O usuário interage com o sistema
2. A view chama uma função do viewmodel
3. O viewmodel chama o repository para acessar os dados
4. O repository acessa os dados e retorna para o viewmodel
5. O viewmodel processa esse dados vindo do repository e o resultado é colcoado em uma variável observable
6. A view nota que a variavel do viewmodel foi modificada e se atualiza

A estrutura de pastas utilizada foi a seguinte:

config - contém todas as classes que configuram a aplicação. São classes globais.
datasource - todo o acesso a dados vem nesse pasta. Também inclui os repositories.
presentation - contém as classes de view e viewmodel.

### O código

Existem duas activities no app. RepoActivity lista todos os repositórios além de ter um campo de pesquisa na AppBar.

RepoDetailActivity mostra os detalhes do repositório selecionado no RepoActivity, mostra também a imagem de avatar do dono. Para exibir
a imagem foi utilizado a biblioteca Picasso para agilizar e facilitar a atividade.

Como mencionado ateriormente, a activity RepoActivity trata-se de uma lista, por isso, normalmente se faz necessário criar um adaptador customizado mas para aumentar a produtividade foi utilizado
a biblioteca FastAdapter com o fim de facilitar essa criação. Todas as actiities instanciam seus respectivos viewmodel e injetam as
dependências necessárias. Poderia ser usado uma biblioteca pra injetar essas dependências de forma mais robusta mas como o projeto náo é muito complexo foi feito da forma mais simples possível.

Falando dos viewmodels, o RepoDetailViewmodel contém apenas os livedatas (observables) para que a view seja notificada, uma única resalva é
que foi necessario criar um binding adapter para converter a url em um livedata.

No RepoViewmodel tem a maior parte das regras de negócio. Como a pesquisa e o carregamento são basicamente a mesma coisa, foi optado então
usar a mesma função. Basicamente a função controla a paginação, chama o repository pedindo os dados que veem da api, pega os dados
e coloca nos livedatas para serem consumidos pela view.

### Testes

Um teste unitário foi feito para testar a funcionalidade do viewmodel do RepoViewmodel. Todos os teste passaram.
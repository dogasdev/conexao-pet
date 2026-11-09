# Conexão Pet — instalação e integração

## Onde colocar os arquivos
Base: dogasdev/conexao-pet, backend inspecionado no commit e0596a0.
Extraia o ZIP e copie TODO o conteúdo de frontend para conexao-pet/frontend. O resultado é conexao-pet/frontend/package.json, sem repetir frontend/frontend. Faça backup de alterações próprias antes de substituir a versão anterior. Os antigos diretórios vazios assets/css/js/pages podem permanecer: não são usados pelo Vite.
Não copie src do frontend para src do Java. Não substitua pom.xml nem os arquivos do backend. O ZIP contém o frontend completo, não uma nova cópia do Java.
Abra MAPA_DOS_ARQUIVOS.html para visualizar a organização e os arquivos desta entrega.

## Executar pelo IntelliJ ou outro terminal
Abra a raiz conexao-pet no IntelliJ. Use dois terminais.

Terminal 1, na raiz, com JDK configurado e MySQL iniciado:
```powershell
.\mvnw.cmd -version
.\mvnw.cmd spring-boot:run
```
O Maven deve usar um JDK compatível com o projeto (compilação release 21), não Java 8/JRE. Confira JAVA_HOME e o JDK usado pelo Maven no IntelliJ.

O backend atual usa o banco conexao_pet e ddl-auto=none: não cria as tabelas automaticamente. Peça à equipe o SQL atualizado de criação das tabelas e execute-o no seu MySQL. Criar apenas um banco vazio não basta. Configure usuário e senha de acesso no ambiente do Java, sem publicar credenciais no Git.

O JWT_SECRET deve existir no ambiente do processo Java. Para gerar uma chave de desenvolvimento em Base64 no PowerShell, antes de iniciar o Maven:
```powershell
$jwtBytes = New-Object byte[] 32
$jwtRng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$jwtRng.GetBytes($jwtBytes)
$jwtRng.Dispose()
$env:JWT_SECRET = [Convert]::ToBase64String($jwtBytes)
```
Não publique essa chave e não a coloque no frontend. Gerar outra chave invalida tokens assinados pela anterior. Se iniciar pelo botão Run do IntelliJ, configure JWT_SECRET nas variáveis da configuração de execução; o ambiente de um terminal não é transferido para outro processo já aberto.

Terminal 2:
```powershell
cd frontend
npm ci
npm run dev
```
Abra a URL mostrada pelo Vite. Não abra index.html com duplo clique.
Por padrão, /api é encaminhado para http://localhost:8080. Para outra porta, copie .env.example para .env e ajuste API_PROXY_TARGET. Reinicie o Vite.

## Páginas e arquivos
- #inicio: página principal brutalista, busca por cidade com sugestões, filtros e cards animados.
- #login: login centralizado (src/pages/Login.jsx, NOVO).
- #cadastro: pessoa física ou instituição (src/pages/Register.jsx, ATUALIZADO).
- #privacidade e #termos: src/pages/Legal.jsx, ATUALIZADO; textos preliminares.
- #animal/1: detalhe de um animal demonstrativo.
- src/brutal.css: NOVO visual da página principal e login.
- src/services/api.js: NOVO cliente HTTP comum.
- src/services/auth.js: NOVO login, validação na rota protegida e requisições autenticadas.
- src/lib/session.js: NOVO gerenciamento da sessão em memória.
- src/services/registration.js e src/lib/registration.js: ATUALIZADOS.
- src/main.jsx: ATUALIZADO, navegação e estado de login.
- src/lib/auth.test.js: NOVOS testes; registration.test.js atualizado.
- public/images/logo-original.png: logo original preservada.
As páginas são componentes React; não precisam de um arquivo HTML por página.

## Contrato do backend
POST /api/auth/login envia {identificador, senha}. Identificador aceita e-mail ou username. Sucesso HTTP 200: {id, username, nome, email, token}. Erro de credenciais: HTTP 401, inclusive sem corpo.
A sessão começa após o login. O Java verifica o JWT nas operações protegidas.

POST /api/auth/cadastro/usuario envia username, email, senha, telefone, nome, sobrenome, cpf, cidade.
POST /api/auth/cadastro/instituicao envia os mesmos campos mais nomeInstituicao, cnpj, rua, numero, bairro, cep.
Cadastro espera HTTP 201 com {mensagem}; erros HTTP 400 com {erro}. Telefone é obrigatório e único no backend. Nome aceita até 80 caracteres, e-mail 255 e nome de instituição 150.
Cidade segue o campo textual atual, por exemplo “Fortaleza, CE”; instituição e responsável compartilham esse campo. CPF, telefone e CEP são enviados somente com números. CNPJ é enviado sem pontuação e em maiúsculas. Confirmar senha e aceite não são enviados: não existem no DTO. O servidor ainda não registra evidência do aceite.

## Sessão e segurança
Token e usuário ficam somente na memória da aba, sem localStorage/sessionStorage e sem guardar senha. Recarregar a página exige novo login. O JWT atual expira em duas horas; a sessão local acompanha exp e é encerrada também ao receber 401 nas requisições autenticadas. Sair limpa a sessão local: não revoga o token no servidor, que ainda não possui essa rota. Não existe recuperação de senha no contrato atual.
O frontend nunca se conecta diretamente ao MySQL; somente o Java acessa o banco. Use HTTPS em produção. As validações do front não substituem as do servidor.

## Produção e prévia
O site de demonstração publicado não envia cadastros/senhas sem uma API configurada. No desenvolvimento npm run dev habilita a API por meio do proxy.
Para produção, antes de npm run build, configure VITE_API_ENABLED=true e VITE_API_BASE_URL=https://dominio-da-api (sem /api no final). Para API na mesma origem, deixe a base vazia e configure o servidor para encaminhar /api ao Java. Publique dist.
Variáveis VITE_ são públicas: nunca coloque nelas senha do banco ou JWT_SECRET. O proxy Vite funciona apenas em desenvolvimento. Para origens diferentes, o backend precisa de CORS permitindo a origem do front, GET/POST/PUT/PATCH/DELETE/OPTIONS e Content-Type/Authorization/Accept. Não use localhost como API de um site publicado.

## Anúncios e busca
Abra #anunciar para criar um anúncio. Após o login você retorna ao formulário. Informe uma foto já hospedada por link. A página inicial consulta GET /api/anuncios e a ficha consulta GET /api/anuncios/{idAnuncio}. A busca por cidade usa cidadeResponsavel das fichas, consultando até quatro por vez e reutilizando localidades na memória da aba. Em bases grandes, um filtro por cidade no Java reduzirá essas consultas. Cadastros antigos cuja cidade seja ambígua e não possua UF não podem ser atribuídos com segurança a um estado.

Na ficha, “Gerenciar meu anúncio” abre o formulário de edição. O Java verifica a autoria em cada alteração. A API ainda não informa o ID do dono nas leituras, então o acesso à tela é visível e ações sobre anúncios de terceiros retornam erro de permissão. O responsável pode editar (PUT), excluir (DELETE) e concluir a adoção (PATCH /api/anuncios/{id}/adotar). Adoção e exclusão removem o anúncio e o animal. Ambas exigem confirmação. A API não fornece contato ou upload de fotos.

A prévia hospedada usa os exemplos enquanto uma API pública não for configurada. No modo conectado, uma falha de rede aparece como erro e uma lista vazia mostra zero resultados.

## Conferir no IntelliJ
1. Inicie o Java e o front conforme as instruções acima. Cadastre uma conta de teste e entre. Em F12 > Network, o cadastro deve retornar 201 e o login 200.
2. Clique em Anunciar um animal, preencha o formulário e publique. O POST deve retornar 201 e abrir a ficha real. Verifique o card e a busca pela cidade do responsável.
3. Na ficha, entre em Gerenciar meu anúncio. Edite e confirme o PUT 200. Com outra conta, a alteração deve ser recusada.
4. Em um anúncio descartável confirme a adoção: PATCH 204. Confira que ele desapareceu da vitrine. Para testar exclusão separadamente use outro anúncio de teste: DELETE 204.
5. Se o front e Java forem publicados em domínios diferentes, a política CORS do Java deve permitir a origem exata, cabeçalho Authorization e GET, POST, PUT, PATCH, DELETE e OPTIONS. O proxy local do Vite já encaminha /api.

## Arquivos desta atualização
src/pages/AnimalEditor.jsx contém publicação, edição e confirmações. src/services/animals.js integra os anúncios e adapta as respostas do Java. src/services/auth.js, src/services/api.js, src/main.jsx e as páginas de login e termos foram atualizados. Execute npm test e npm run build para validar o front. Os testes do contrato usam respostas controladas.

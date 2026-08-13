Eu atualizaria a STORY-008 desta forma, mantendo o MVP simples mas já preparando o Profile para trabalhar com sessão persistida.

STORY-008 - Profile
Branch

feature/profile

Dependências

Obrigatórias:

STORY-003-auth-and-navigation

Pode iniciar após:

Navegação principal funcionando
MainScreen funcionando
Bottom Navigation funcionando
Objetivo

Implementar a tela de Perfil do usuário.

A tela deve exibir informações da sessão atual do usuário e configurações básicas da aplicação.

Escopo

Implementar:

Profile Screen

Dados do Usuário

Exibir:

Nome
E-mail

Utilizar:

PayFlowProfileHeader

Os dados devem ser carregados do mecanismo de sessão da aplicação.

Caso não existam dados persistidos, utilizar valores padrão:

Nome:
Usuário PayFlow

E-mail:
usuario@payflow.app

Sessão do Usuário

Utilizar DataStore para persistir:

userName
userEmail
isLoggedIn


O Profile deverá consumir essas informações.

Configurações

Utilizar:

PayFlowSettingsItem

Itens sugeridos:

Notificações
Política de Privacidade
Termos de Uso
Sobre o Aplicativo
Informações do Aplicativo

Exibir:

PayFlow
Versão 1.0.0
Equipe Responsável

Componentes Obrigatórios

Utilizar:

PayFlowProfileHeader
PayFlowSettingsItem
PayFlowCard
PayFlowTopBar

Não criar novos componentes.

Estrutura Esperada
feature/profile

├── ui
├── viewmodel
└── model

ViewModel

Criar:

ProfileViewModel


Obrigatório:

Herdar BaseViewModel
Utilizar UiState
Persistência

Implementar utilizando DataStore.

Dados mínimos:

userName
userEmail
isLoggedIn


Não utilizar Room.

API

Não obrigatória.

Os dados podem ser obtidos da sessão persistida localmente.

Navegação

Origem:

Bottom Navigation


Fluxo:

Main
↓
Profile

Critérios de Aceite
Tela acessível pela Bottom Navigation
Nome exibido corretamente
E-mail exibido corretamente
Dados carregados do DataStore
Configurações exibidas corretamente
Informações do aplicativo exibidas corretamente
Design System reutilizado
Não criar componentes duplicados
Navegação funcionando sem erros
Estados Esperados
Loading
Success
Error

Utilizando UiState.

Fora de Escopo

Não implementar:

Login real
Firebase Auth
Google Sign-In
OAuth
Alteração de senha
Upload de foto
Cadastro de usuário
Backend real
Observações Técnicas

Utilizar exclusivamente componentes já existentes no Design System.

Reutilizar:

PayFlowProfileHeader
PayFlowSettingsItem
PayFlowCard

Persistência da sessão deve utilizar DataStore.

Resultado Esperado

Ao final desta história o usuário consegue visualizar suas informações básicas através de uma sessão persistida localmente utilizando DataStore.

Minha recomendação como Tech Lead

Com essa mudança, a STORY-003 continua simples:

Email
Senha
Entrar
Navegação


E a STORY-008 assume a responsabilidade da sessão:

DataStore
userName
userEmail
isLoggedIn
Profile


Isso deixa a arquitetura mais limpa e evita que você invada as responsabilidades da STORY-001 (Room) e STORY-002 (API). 🚀
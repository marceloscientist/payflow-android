# STORY-008 - Profile

## Branch

feature/profile

---

## Dependências

Obrigatórias:

- STORY-003-auth-and-navigation

Pode iniciar após:

- Navegação principal funcionando
- MainScreen funcionando
- Bottom Navigation funcionando
- Infraestrutura de sessão disponível em `core/session`

---

## Objetivo

Implementar a tela de Perfil do usuário.

A tela deve exibir informações da sessão atual do usuário e configurações básicas da aplicação.

---

## Escopo

Implementar:

- Profile Screen

---

## Dados do Usuário

Exibir:

- Nome
- E-mail

Utilizar:

- PayFlowProfileHeader

Os dados devem ser carregados da infraestrutura de sessão disponível em:

```text
core/session
```

Utilizar:

- UserSession
- SessionRepository

Caso não exista sessão ativa, utilizar:

```text
Nome:
Usuário PayFlow

E-mail:
usuario@payflow.app
```

---

## Sessão do Usuário

Consumir:

- UserSession

Através de:

- SessionRepository

Dados disponíveis:

```text
name
email
isLoggedIn
```

---

## Configurações

Utilizar:

- PayFlowSettingsItem

Itens sugeridos:

- Notificações
- Política de Privacidade
- Termos de Uso
- Sobre o Aplicativo

---

## Informações do Aplicativo

Exibir:

```text
PayFlow

Versão:
1.0.0

Equipe Responsável
```

---

## Componentes Obrigatórios

Utilizar:

- PayFlowProfileHeader
- PayFlowSettingsItem
- PayFlowCard
- PayFlowTopBar

Não criar novos componentes.

---

## Estrutura Esperada

```text
feature/profile

├── ui
├── viewmodel
└── model
```

---

## ViewModel

Criar:

- ProfileViewModel

Obrigatório:

- Herdar BaseViewModel
- Utilizar UiState

---

## Persistência

Não utilizar Room.

Consumir os dados através do:

- SessionRepository

A implementação atual da sessão utiliza infraestrutura própria baseada em:

- UserSession
- SessionRepository
- SessionRepositoryImpl

Uma futura evolução poderá migrar a implementação para DataStore sem impacto na feature.

---

## API

Não obrigatória.

---

## Navegação

Origem:

- Bottom Navigation

Fluxo:

```text
Main
↓
Profile
```

---

## Critérios de Aceite

- [ ] Tela acessível pela Bottom Navigation
- [ ] Nome exibido corretamente
- [ ] E-mail exibido corretamente
- [ ] Dados carregados via SessionRepository
- [ ] Configurações exibidas corretamente
- [ ] Informações do aplicativo exibidas corretamente
- [ ] Design System reutilizado
- [ ] Não criar componentes duplicados
- [ ] Navegação funcionando sem erros

---

## Estados Esperados

Utilizar:

- Loading
- Success
- Error

Através de:

- UiState

---

## Fora de Escopo

Não implementar:

- Login real
- Firebase Auth
- Google Sign-In
- OAuth
- Alteração de senha
- Upload de foto
- Cadastro de usuário
- Backend real

---

## Observações Técnicas

Utilizar exclusivamente componentes já existentes no Design System.

Reutilizar:

- PayFlowProfileHeader
- PayFlowSettingsItem
- PayFlowCard
- PayFlowTopBar

Não acessar diretamente estruturas de Auth.

Consumir exclusivamente a infraestrutura compartilhada em:

```text
core/session
```

---

## Resultado Esperado

Ao final desta história o usuário consegue visualizar suas informações básicas através da sessão atual da aplicação utilizando:

- UserSession
- SessionRepository

A tela deve estar preparada para futuras evoluções da persistência sem necessidade de alterações significativas na feature.

---

## Stories Relacionadas

Relaciona-se com:

- STORY-003-auth-and-navigation

Não desbloqueia outras histórias do MVP.
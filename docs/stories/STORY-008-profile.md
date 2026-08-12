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

---

## Objetivo

Implementar a tela de Perfil do usuário.

Esta história complementa o fluxo principal do aplicativo e representa uma das telas opcionais recomendadas para o MVP. 【1-080b72】

---

## Requisito do Professor

O direcionamento oficial permite telas complementares como:

- Perfil
- Configurações
- Segurança
- Preferências

Esta história será responsável pela implementação da área de Perfil do usuário. 【1-080b72】

---

## Escopo

Implementar:

Profile Screen

---

## Informações Exibidas

### Dados do Usuário

Exibir:

- Nome
- E-mail

Utilizar:

PayFlowProfileHeader

Nesta versão os dados podem ser mockados.

Exemplo:

Nome:
Marcelo Santana

E-mail:
marcelo@email.com

---

## Configurações

Utilizar:

PayFlowSettingsItem

Itens sugeridos:

- Notificações
- Política de Privacidade
- Termos de Uso
- Sobre o Aplicativo

---

## Informações do Aplicativo

Exibir:

- Nome do Aplicativo
- Versão
- Equipe Responsável

Exemplo:

PayFlow

Versão:
1.0.0

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

feature/profile

├── ui
├── viewmodel
└── model

---

## ViewModel

Opcional para o MVP.

Caso implementado:

Criar:

ProfileViewModel

Obrigatório:

- Herdar BaseViewModel
- Utilizar UiState

---

## Persistência

Não obrigatória nesta história.

---

## API

Não obrigatória nesta história.

---

## Navegação

Origem:

Bottom Navigation

Fluxo:

Main
↓
Profile

---

## Critérios de Aceite

- [ ] Tela acessível pela Bottom Navigation
- [ ] Nome exibido corretamente
- [ ] E-mail exibido corretamente
- [ ] Configurações exibidas corretamente
- [ ] Informações do aplicativo exibidas corretamente
- [ ] Design System reutilizado
- [ ] Não criar componentes duplicados
- [ ] Navegação funcionando sem erros

---

## Estados Esperados

- Success

Loading e Error são opcionais para o MVP.

---

## Fora de Escopo

Não implementar:

- Login real
- Alteração de senha
- Upload de foto
- Cadastro de usuário
- Integração com backend
- Integração com redes sociais

---

## Testes Manuais

### Cenário 1

Abrir aplicativo

↓

Selecionar Perfil

Resultado esperado:

Tela abre corretamente.

---

### Cenário 2

Visualizar:

- Nome
- E-mail

Resultado esperado:

Informações exibidas corretamente.

---

### Cenário 3

Selecionar:

Sobre o Aplicativo

Resultado esperado:

Ação executada sem erro.

---

## Observações Técnicas

Utilizar exclusivamente componentes já existentes no Design System.

Não criar componentes específicos para esta tela.

Reutilizar:

- PayFlowProfileHeader
- PayFlowSettingsItem
- PayFlowCard

---

## Resultado Esperado

Ao final desta história o usuário consegue visualizar suas informações básicas e acessar configurações e informações institucionais da aplicação.

---

## Stories Relacionadas

Relaciona-se com:

- STORY-003-auth-and-navigation

Não desbloqueia outras histórias do MVP.
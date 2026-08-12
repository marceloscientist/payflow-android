# STORY-005 - Subscriptions List

## Branch

feature/subscriptions-list

---

## Dependências

Obrigatórias:

- STORY-001-local-persistence-room

Recomendadas:

- STORY-003-auth-and-navigation

Pode iniciar após:

- SubscriptionEntity criado
- SubscriptionDao criado
- SubscriptionRepository criado

---

## Objetivo

Implementar a tela principal de listagem das assinaturas cadastradas.

Esta história atende ao requisito do professor de possuir:

Listagem / Histórico / Extrato

adaptado ao contexto do projeto PayFlow.

---

## Requisito do Professor

O projeto deve possuir uma tela de:

Listagem / Histórico / Extrato

contendo dados relevantes do tema escolhido.

Para o PayFlow:

Listagem de Assinaturas.

---

## Escopo

Implementar:

Subscriptions Screen

com leitura dos dados persistidos localmente.

---

## Funcionalidades

### Listagem

Exibir:

- Nome da assinatura
- Categoria
- Valor
- Frequência
- Próxima cobrança

---

### Busca

Permitir pesquisa por:

- Nome

Utilizar:

PayFlowSearchBar

---

### Estado Vazio

Se não houver assinaturas:

Exibir:

PayFlowEmptyState

---

### Estado de Carregamento

Exibir:

PayFlowLoadingState

durante a recuperação dos dados.

---

### Navegação

Ao clicar em uma assinatura:

Navegar para:

Subscription Details

(STORY-006)

---

## Componentes Obrigatórios

Utilizar:

- PayFlowSearchBar
- PayFlowSubscriptionCard
- PayFlowLoadingState
- PayFlowEmptyState
- PayFlowStatusBadge

Não criar novos componentes.

---

## Estrutura Esperada

```text
feature/subscriptions

├── ui
├── viewmodel
└── model
```

---

## ViewModel

Criar:

SubscriptionsViewModel

Obrigatório:

- Herdar BaseViewModel
- Utilizar UiState

---

## Persistência

Utilizar:

SubscriptionRepository

Fluxo:

View
↓
ViewModel
↓
Repository
↓
Dao
↓
Room

---

## API

Não obrigatória nesta história.

A listagem deve funcionar sem internet.

---

## Critérios de Aceite

- [ ] Lista carregada a partir do Room
- [ ] Busca funcionando
- [ ] Loading funcionando
- [ ] Empty State funcionando
- [ ] UiState utilizado
- [ ] BaseViewModel utilizado
- [ ] Navegação para Details funcionando
- [ ] Nenhum componente visual novo criado

---

## Estados Esperados

Loading

Success

Empty

Error

---

## Fora de Escopo

Não implementar:

- Dashboard
- Simulador
- API
- Perfil

---

## Testes Manuais

Cenário 1:

Cadastrar:

Netflix

Spotify

Disney+

↓

Abrir Assinaturas

↓

Visualizar 3 itens

---

Cenário 2:

Pesquisar:

Netflix

↓

Exibir apenas Netflix

---

Cenário 3:

Base sem registros

↓

Exibir EmptyState

---

## Observações Técnicas

Reutilizar integralmente o Design System.

Não criar card específico para esta tela.

Utilizar:

PayFlowSubscriptionCard

---

## Resultado Esperado

O usuário consegue visualizar todas as assinaturas cadastradas e localizar registros através da busca.

---

## Stories Dependentes

Esta história desbloqueia:

- STORY-006-subscription-details
- STORY-007-dashboard
- STORY-009-simulator
# STORY-009 - Subscription Savings Simulator

## Branch

feature/subscription-simulator

---

## Dependências

Obrigatórias:

- STORY-001-local-persistence-room
- STORY-005-subscriptions-list
- STORY-007-dashboard

Pode iniciar somente após:

- SubscriptionRepository implementado
- Dados persistidos no Room
- Dashboard funcional

---

## Objetivo

Implementar o diferencial do projeto PayFlow.

Permitir que o usuário simule quanto economizaria ao cancelar uma ou mais assinaturas cadastradas.

Esta funcionalidade representa um dos principais diferenciais de negócio do aplicativo.

---

## Requisito do Professor

O projeto PayFlow possui como diferencial sugerido:

"Mostrar gastos com assinaturas pouco utilizadas."

Esta história é responsável pela implementação deste diferencial.

---

## Escopo

Implementar:

Subscription Savings Simulator Screen

---

## Funcionalidades

### Listagem de Assinaturas

Exibir todas as assinaturas cadastradas.

Permitir seleção individual para simulação.

---

### Simulação de Economia Mensal

Exibir:

Valor economizado por mês.

---

### Simulação de Economia Anual

Exibir:

Valor economizado por ano.

---

### Quantidade de Assinaturas Selecionadas

Exibir:

Número total de assinaturas marcadas para cancelamento.

---

### Resumo da Simulação

Exibir:

- Economia Mensal
- Economia Anual
- Quantidade de Assinaturas
- Lista de Assinaturas Selecionadas

---

### Cenário de Cancelamento

Permitir selecionar:

- Uma assinatura
- Múltiplas assinaturas

A atualização dos valores deve ocorrer automaticamente.

---

## Componentes Obrigatórios

Utilizar:

- PayFlowSubscriptionCard
- PayFlowMetricCard
- PayFlowButton
- PayFlowCard
- PayFlowEmptyState
- PayFlowLoadingState

Não criar novos componentes.

---

## Estrutura Esperada

feature/simulator

├── ui
├── viewmodel
└── model

---

## ViewModel

Criar:

SubscriptionSimulatorViewModel

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
Room

---

## API

Não obrigatória.

Todos os cálculos podem ser realizados utilizando dados locais.

---

## Regras de Negócio

### Economia Mensal

Somar os valores mensais das assinaturas selecionadas.

Exemplo:

Netflix = R$ 44,90

Spotify = R$ 21,90

Resultado:

Economia Mensal = R$ 66,80

---

### Economia Anual

Economia Mensal × 12

Exemplo:

R$ 66,80 × 12

Resultado:

R$ 801,60

---

## Critérios de Aceite

- [ ] Assinaturas carregadas corretamente
- [ ] Seleção funcionando
- [ ] Economia mensal calculada corretamente
- [ ] Economia anual calculada corretamente
  - [ ] Quantidade de assinaturas calculada corretamente
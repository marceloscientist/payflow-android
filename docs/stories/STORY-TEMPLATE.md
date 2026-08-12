# STORY-XXX

## Título

[Nome da Funcionalidade]

---

## Objetivo

Descrever claramente o objetivo da funcionalidade.

Exemplo:

Permitir que o usuário visualize métricas financeiras
relacionadas às suas assinaturas.

---

## Critérios de Aceite

- [ ] Critério 1
- [ ] Critério 2
- [ ] Critério 3

---

## Componentes Permitidos

Utilizar apenas componentes existentes no Design System.

Exemplos:

- PayFlowButton
- PayFlowCard
- PayFlowMetricCard
- PayFlowSubscriptionCard
- PayFlowSearchBar

Caso um componente não exista:

Criar primeiro no Design System e registrar no Developer Playground.

---

## Models Utilizados

Exemplo:

- Subscription
- User

Não criar modelos duplicados.

---

## ViewModel

Obrigatório:

- Herdar BaseViewModel

---

## Estado

Obrigatório:

UiState

Estados permitidos:

- Loading
- Success
- Error
- Empty

---

## Navegação

Utilizar:

- Routes
- PayFlowNavGraph

Não criar navegação paralela.

---

## Persistência

Utilizar:

- Room
- Repository

quando aplicável.

---

## API

Utilizar:

- Retrofit
- ApiClient

quando aplicável.

---

## Fora de Escopo

Descrever explicitamente o que não deve ser implementado.

Exemplo:

- Não implementar sincronização remota.
- Não implementar notificações.

---

## Observações para IA

Antes de implementar:

Leia:

- docs/ai-context/project-context.md
- docs/architecture/architecture.md
- docs/decisions/architecture-decisions.md
- docs/prompts/feature-prompt.md

Respeite obrigatoriamente:

- MVVM
- Feature-Based Architecture
- Design System existente
- UiState
- BaseViewModel
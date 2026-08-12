# PayFlow Feature Development Prompt

Você é um desenvolvedor do projeto PayFlow.

Antes de implementar qualquer funcionalidade:

Leia:

- docs/ai-context/project-context.md
- docs/architecture/architecture.md
- docs/decisions/architecture-decisions.md

---

# Objetivo

Implementar apenas a feature solicitada.

Não criar infraestrutura nova.

Não criar arquitetura nova.

Não criar componentes novos sem necessidade.

---

# Regras Obrigatórias

Arquitetura:

- MVVM
- Feature-Based

Estrutura:

feature/
data/
core/

---

# UI

Utilizar apenas componentes existentes em:

core/components

Consultar:

Developer Playground

antes de criar novos componentes.

---

# Estado

Utilizar apenas:

UiState

Estados permitidos:

- Loading
- Success
- Error
- Empty

---

# ViewModel

Todo ViewModel deve herdar:

BaseViewModel

Local:

core/base/BaseViewModel.kt

---

# Navegação

Toda navegação deve utilizar:

Routes

PayFlowNavGraph

Não criar rotas paralelas.

---

# API

Toda chamada remota deve utilizar:

Retrofit

ApiClient

---

# Persistência

Toda persistência local deve utilizar:

Room

PayFlowDatabase

---

# Proibido

Não criar:

- Segunda navegação
- Segundo Design System
- Componentes visuais duplicados
- ViewModels sem BaseViewModel
- Estados fora de UiState
- URLs hardcoded
- Repositories duplicados

---

# Resultado Esperado

Gerar:

- Código completo
- Seguindo MVVM
- Reutilizando Design System
- Compatível com a arquitetura atual

Sem alterar a infraestrutura existente.
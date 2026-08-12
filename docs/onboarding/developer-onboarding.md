# Developer Onboarding

Bem-vindo ao projeto PayFlow.

Antes de desenvolver qualquer funcionalidade, leia os documentos abaixo:

1. docs/ai-context/project-context.md
2. docs/architecture/architecture.md
3. docs/decisions/architecture-decisions.md
4. docs/ai-context/design-system.md
5. docs/prompts/feature-prompt.md

---

# Objetivo do Projeto

PayFlow é um aplicativo Android para gerenciamento de assinaturas digitais.

O usuário poderá:

- Cadastrar assinaturas
- Visualizar gastos recorrentes
- Visualizar indicadores financeiros
- Simular economia
- Gerenciar categorias de serviços

---

# Tecnologias

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Room
- Retrofit

---

# Estrutura do Projeto

```text
app/

core/
data/
feature/
```

---

# Arquitetura

Feature-Based MVVM

Fluxo:

View
↓
ViewModel
↓
Repository
↓
Datasource
↓
Room / Retrofit

---

# Design System

Todos os componentes visuais estão em:

```text
core/components
```

Referência visual:

```text
Developer Playground
```

Não criar componentes novos sem verificar os existentes.

---

# Navegação

Arquivos oficiais:

```text
core/navigation/Routes.kt

core/navigation/PayFlowNavGraph.kt
```

Não criar fluxo alternativo.

---

# Estado

Utilizar obrigatoriamente:

```text
UiState
```

Estados permitidos:

- Loading
- Success
- Error
- Empty

---

# ViewModels

Todos os ViewModels devem herdar:

```text
BaseViewModel
```

---

# Desenvolvimento de Features

Cada feature deve possuir:

```text
feature/<feature-name>

├── ui
├── viewmodel
└── model
```

Exemplo:

```text
feature/dashboard

feature/profile

feature/subscriptions
```

---

# Antes de abrir um Pull Request

Verifique:

- [ ] Utilizou Design System
- [ ] Utilizou UiState
- [ ] Herdou BaseViewModel
- [ ] Seguiu MVVM
- [ ] Não criou componentes duplicados
- [ ] Não criou navegação paralela
- [ ] Não alterou arquitetura sem aprovação

---

# MVP

Incluído:

- Dashboard
- Assinaturas
- Nova Assinatura
- Perfil
- Simulador

---

# Fora do MVP

- Dark Theme
- Firebase
- Analytics
- Crashlytics
- Multi-module
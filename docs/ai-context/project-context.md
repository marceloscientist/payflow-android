# PayFlow - Project Context

## Objetivo

PayFlow é um aplicativo Android para gerenciamento de assinaturas digitais.

O usuário poderá:

- Cadastrar assinaturas
- Visualizar gastos recorrentes
- Visualizar métricas financeiras
- Simular economia com cancelamentos
- Gerenciar categorias de serviços

---

## Tecnologias

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Room
- Retrofit

---

## Arquitetura

Feature-Based MVVM

Estrutura principal:

core/
data/
feature/

---

## Design System

Todos os componentes visuais devem reutilizar os componentes existentes em:

core/components

Consultar sempre:

Developer Playground

antes de criar novos componentes.

---

## Estado Global

Utilizar:

UiState

para representar:

- Loading
- Success
- Error
- Empty

---

## ViewModels

Todos os ViewModels devem herdar:

BaseViewModel

---

## Navegação

Toda navegação deve utilizar:

Routes

e

PayFlowNavGraph

---

## Proibições

Não criar:

- Navegação paralela
- Design System paralelo
- Estados fora de UiState
- ViewModels sem BaseViewModel

---

## MVP

Inclui:

- Dashboard
- Assinaturas
- Nova Assinatura
- Perfil
- Simulador

---

## Fora do MVP

- Dark Theme
- Analytics
- Firebase
- Crashlytics
- Multi-module
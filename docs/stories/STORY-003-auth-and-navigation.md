# STORY-003 - Authentication and Navigation

## Branch

feature/auth-navigation

---

## Dependências

Nenhuma

Pode ser desenvolvida em paralelo com:

- STORY-001-local-persistence-room
- STORY-002-api-integration

---

## Objetivo

Implementar o fluxo inicial de entrada do usuário e navegação principal do aplicativo PayFlow.

Esta história atende ao requisito obrigatório de:

- Jetpack Compose
- Navegação entre telas

---

## Requisitos do Professor

O projeto deve possuir:

- Interface em Jetpack Compose
- Navegação funcional
- Fluxo principal estável

【1-43e6e0】

---

## Escopo

Implementar:

Login

↓

Main

↓

Dashboard

↓

Subscriptions

↓

Profile

↓

Simulator

↓

Developer Playground

---

## Funcionalidades

### Login

Exibir:

- Nome do App
- Botão Entrar

Utilizar:

- PayFlowButton

---

### Navegação

Implementar fluxo:

Login
↓
Main

---

### Bottom Navigation

Permitir acesso para:

- Dashboard
- Assinaturas
- Simulador
- Perfil
- Developer Playground

---

### MainScreen

Responsável por:

- Scaffold
- Bottom Navigation
- Navegação interna

---

## Componentes Permitidos

Utilizar:

- PayFlowButton
- PayFlowTopBar
- PayFlowBottomNavigation

Não criar novos componentes.

---

## Critérios de Aceite

- [ ] App inicia na LoginScreen
- [ ] Existe botão Entrar
- [ ] Clicar em Entrar navega para Main
- [ ] Main possui Bottom Navigation
- [ ] Dashboard abre corretamente
- [ ] Assinaturas abre corretamente
- [ ] Simulador abre corretamente
- [ ] Perfil abre corretamente
- [ ] Developer Playground abre corretamente
- [ ] Não existem travamentos durante navegação

---

## Arquivos Esperados

Utilizar:

core/navigation/

Routes.kt

PayFlowNavGraph.kt

feature/main/ui/

MainScreen.kt

feature/auth/ui/

LoginScreen.kt

---

## ViewModel

Não obrigatório.

Login fake é suficiente para o MVP.

---

## Persistência

Não implementar.

---

## API

Não implementar.

---

## Fora de Escopo

Não implementar:

- OAuth
- JWT
- Firebase Auth
- Google Login
- Login Social

---

## Observações Técnicas

Utilizar:

- Jetpack Compose
- Navigation Compose

Respeitar:

- MVVM
- Feature-Based Architecture
- Design System

---

## Resultado Esperado

Ao final desta história o usuário consegue acessar o aplicativo e navegar por todas as telas principais sem erros.

---

## Status Atual

Grande parte desta história já foi implementada durante a Sprint 0.

Revisar funcionalidades existentes antes de desenvolver novas alterações.
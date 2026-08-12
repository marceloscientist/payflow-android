# Architecture Decisions (AD)

## AD-001

### Título

Arquitetura MVVM

### Decisão

O projeto utilizará MVVM.

Estrutura:

View
↓
ViewModel
↓
Repository
↓
Datasource
↓
Room / Retrofit

### Motivo

Separação de responsabilidades.

---

## AD-002

### Título

Feature-Based Architecture

### Decisão

O projeto será organizado por Feature.

Estrutura:

feature/
data/
core/

### Motivo

Escalabilidade.

---

## AD-003

### Título

Jetpack Compose

### Decisão

Toda UI será implementada usando Compose.

### Motivo

Padronização.

---

## AD-004

### Título

Material 3

### Decisão

Utilizar Material 3 como base visual.

### Motivo

Consistência e produtividade.

---

## AD-005

### Título

Navigation Compose

### Decisão

Toda navegação utilizará Navigation Compose.

### Motivo

Padrão oficial Android.

---

## AD-006

### Título

Developer Playground

### Decisão

Todo componente visual deve ser demonstrado no Developer Playground.

### Motivo

Evitar componentes duplicados.

---

## AD-007

### Título

UiState

### Decisão

Todas as telas utilizarão UiState.

Estados:

- Loading
- Success
- Error
- Empty

### Motivo

Padronização.

---

## AD-008

### Título

BaseViewModel

### Decisão

Todos os ViewModels devem herdar BaseViewModel.

### Motivo

Reutilização de comportamento comum.

---

## AD-009

### Título

Retrofit

### Decisão

Comunicação remota será realizada com Retrofit.

### Motivo

Padrão amplamente utilizado.

---

## AD-010

### Título

Room

### Decisão

Persistência local será realizada usando Room.

### Motivo

Integração nativa com Android.

---

## AD-011

### Título

Design System Centralizado

### Decisão

Todos os componentes visuais devem existir em:

core/components

### Motivo

Consistência visual.

---

## AD-012

### Título

Documentação para IA

### Decisão

Toda IA deve utilizar os documentos da pasta docs.

### Motivo

Evitar implementações divergentes.

---

## AD-013

### Título

Proibição de Navegação Paralela

### Decisão

Toda navegação passa por:

Routes
PayFlowNavGraph

### Motivo

Evitar fluxos duplicados.

---

## AD-014

### Título

Configuração Centralizada

### Decisão

Variáveis globais devem ficar em:

core/config/AppConfig.kt

### Motivo

Evitar valores hardcoded.

---

## AD-015

### Título

Dark Theme fora do MVP

### Decisão

Dark Theme não faz parte do MVP.

### Motivo

Priorização da entrega das funcionalidades principais.
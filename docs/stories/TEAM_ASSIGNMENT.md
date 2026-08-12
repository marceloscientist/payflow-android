# TEAM ASSIGNMENT

## Projeto

PayFlow - Organizador de Assinaturas e Gastos Recorrentes

---

## Objetivo

Distribuir as histórias entre os integrantes da equipe de forma equilibrada, minimizando dependências e permitindo desenvolvimento paralelo.

---

## Estrutura de Papéis

| Integrante | Papel Principal |
|------------|------------|
| Marcelo Santana | Tech Lead / Arquiteto / Reviewer |
| Sergio Ferreira | Backend Local (Room) |
| Lucas Lima | Integração API |
| Gutemberg Nascimento | Cadastro e Detalhamento |
| Wesley Silva | Listagem e Simulador |
| Pietro Vellozo | Dashboard e Perfil |

---

# Distribuição das Stories

| Story | Branch | Responsável | Dependências |
|---------|---------|---------|---------|
| STORY-001-local-persistence-room | feature/room-persistence | Sergio Ferreira | Nenhuma |
| STORY-002-api-integration | feature/api-integration | Lucas Lima | Nenhuma |
| STORY-003-auth-and-navigation | feature/auth-navigation | Marcelo Santana (já implementada) | Nenhuma |
| STORY-004-add-subscription | feature/add-subscription | Gutemberg Nascimento | STORY-001 |
| STORY-005-subscriptions-list | feature/subscriptions-list | Wesley Silva | STORY-001 |
| STORY-006-subscription-details | feature/subscription-details | Gutemberg Nascimento | STORY-001, STORY-005 |
| STORY-007-dashboard | feature/dashboard | Pietro Vellozo | STORY-001, STORY-005 |
| STORY-008-profile | feature/profile | Pietro Vellozo | STORY-003 |
| STORY-009-subscription-simulator | feature/subscription-simulator | Wesley Silva | STORY-001, STORY-005, STORY-007 |

---

# Ordem Recomendada de Desenvolvimento

## Fase 1 - Fundação

Executar em paralelo.

| Story | Responsável |
|---------|---------|
| STORY-001-local-persistence-room | Sergio |
| STORY-002-api-integration | Lucas |
| STORY-003-auth-and-navigation | Marcelo |

Objetivo:

- Concluir persistência local
- Concluir API
- Garantir navegação

---

## Fase 2 - Operações Principais

Iniciar após conclusão da STORY-001.

| Story | Responsável |
|---------|---------|
| STORY-004-add-subscription | Gutemberg |
| STORY-005-subscriptions-list | Wesley |

Objetivo:

- Cadastrar assinaturas
- Visualizar assinaturas

---

## Fase 3 - Camada de Negócio

Iniciar após STORY-004 e STORY-005.

| Story | Responsável |
|---------|---------|
| STORY-006-subscription-details | Gutemberg |
| STORY-007-dashboard | Pietro |

Objetivo:

- Visualizar detalhes
- Construir indicadores financeiros

---

## Fase 4 - Finalização do MVP

| Story | Responsável |
|---------|---------|
| STORY-008-profile | Pietro |
| STORY-009-subscription-simulator | Wesley |

Objetivo:

- Finalizar fluxo do usuário
- Implementar diferencial do projeto

---

# Responsabilidades do Tech Lead

## Marcelo Santana

Responsável por:

- Revisão de código
- Aprovação de Pull Requests
- Garantia da arquitetura MVVM
- Garantia do uso do Design System
- Garantia do uso de UiState
- Garantia do uso de BaseViewModel
- Integração das branches
- Revisão do README
- Coordenação da apresentação

---

# Cronograma

## 12/08

Planejamento concluído.

✅ Arquitetura

✅ Design System

✅ Developer Playground

✅ Stories

✅ Documentação

✅ Infraestrutura Base

---

## 13/08

Desenvolvimento:

- STORY-001
- STORY-002
- STORY-004
- STORY-005

Review técnico contínuo por Marcelo.

---

## 14/08

Desenvolvimento:

- STORY-006
- STORY-007

Primeiros merges para develop.

Review técnico contínuo por Marcelo.

---

## 15/08

Desenvolvimento:

- STORY-008
- STORY-009

Integração final.

Correções.

Validação do README.

Captura de prints.

Preparação da apresentação.

---

## 16/08

Entrega Final.

Validação de:

- Navegação
- Persistência Local
- API
- Dashboard
- Simulador
- README

---

# Regras Obrigatórias

Antes de iniciar qualquer Story:

Ler obrigatoriamente:

- docs/ai-context/project-context.md
- docs/architecture/architecture.md
- docs/decisions/architecture-decisions.md
- docs/prompts/feature-prompt.md
- Story correspondente

---

## Não é permitido

- Criar navegação paralela
- Criar Design System paralelo
- Criar componentes duplicados
- Criar ViewModels fora do BaseViewModel
- Criar estados fora do UiState
- Alterar infraestrutura sem alinhamento com o Tech Lead

---

# Definição de Concluído

Uma Story somente será considerada concluída quando:

- Build executa sem erros
- Critérios de aceite atendidos
- Pull Request revisado
- Merge aprovado pelo Tech Lead
- Funcionalidade validada manualmente
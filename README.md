# PayFlow

## Fonte da Verdade

Este README é a principal referência funcional e arquitetural do projeto.

Sempre que houver dúvidas sobre:

- Escopo
- Arquitetura
- Navegação
- Componentes
- Entidades
- Responsabilidades

A equipe deverá consultar este documento.

Mudanças importantes no projeto devem ser refletidas neste README.

---

# Visão Geral

PayFlow é um aplicativo mobile para gerenciamento de assinaturas digitais e serviços recorrentes.

O objetivo é ajudar usuários a:

- Monitorar assinaturas ativas
- Visualizar gastos recorrentes
- Identificar oportunidades de economia
- Organizar serviços digitais em um único local

Exemplos:

- Netflix
- Spotify
- Disney+
- Prime Video
- Xbox Game Pass
- PlayStation Plus
- ChatGPT Plus
- Canva Pro
- Google One
- Adobe Creative Cloud

---

# Problema

Muitas pessoas possuem diversas assinaturas digitais e acabam perdendo visibilidade sobre:

- Quanto gastam por mês
- Quanto gastam por ano
- Quantos serviços possuem ativos
- Quando ocorrerão os próximos vencimentos
- Quanto poderiam economizar

O PayFlow centraliza essas informações em uma única experiência.

---

# Escopo do MVP

## Incluído

- Login com Google
- Dashboard Financeiro
- Gerenciamento de Assinaturas
- Cadastro de Assinaturas
- Savings Simulator
- Perfil do Usuário
- Persistência Local
- Consumo de API
- MVVM
- Jetpack Compose

---

## Não Incluído

- Open Banking
- PIX
- Pagamento de contas
- Controle financeiro completo
- Assinaturas compartilhadas
- Multiusuário colaborativo
- Controle de inadimplência
- Notificações avançadas
- Integrações com plataformas reais

---

# Público-Alvo

Usuários que desejam acompanhar e otimizar seus gastos com serviços digitais.

---

# Autenticação

## Google Sign-In

Fluxo:

Primeiro acesso:

Login Google
↓
Dashboard

Próximos acessos:

Dashboard

---

# Sessão

Sessão persistente.

O usuário permanece autenticado até realizar logout.

---

# Navegação

## Bottom Navigation

- Dashboard
- Assinaturas
- Simulador
- Perfil

---

## Telas

### 1. Login

Entrada do usuário através da conta Google.

---

### 2. Dashboard

Visão geral do usuário.

Indicadores:

- Gasto Mensal
- Gasto Anual
- Assinaturas Ativas
- Próximos Vencimentos

---

### 3. Assinaturas

Listagem das assinaturas cadastradas.

Funcionalidades:

- Pesquisa
- Filtros
- Visualização
- Acesso ao cadastro

---

### 4. Nova Assinatura

Cadastro guiado de novas assinaturas.

---

### 5. Detalhes da Assinatura

Visualização completa da assinatura.

Funcionalidades:

- Editar
- Cancelar
- Reativar

---

### 6. Savings Simulator

Simulação de economia.

Permite ao usuário selecionar serviços e visualizar:

- Economia Mensal
- Economia Anual

---

### 7. Perfil

Dados do usuário e configurações.

---

# Categorias

- Streaming
- Música
- Games
- IA & Produtividade
- Cloud Storage
- Educação
- Outros

---

# Status das Assinaturas

ACTIVE

CANCELLED

Não haverá exclusão física.

Ao cancelar:

ACTIVE
↓
CANCELLED

O histórico permanece disponível.

---

# Catálogo de Serviços

O aplicativo possuirá catálogo pré-cadastrado.

Exemplos:

- Netflix
- Spotify
- Disney+
- Prime Video
- Xbox Game Pass
- PlayStation Plus
- ChatGPT Plus
- Canva Pro
- Google One

Também será possível cadastrar:

Outro Serviço

---

# API

API utilizada para fornecer catálogo de serviços digitais.

Objetivos:

- Catálogo centralizado
- Evolução futura
- Atendimento ao requisito do projeto

---

# Arquitetura

## Padrão

MVVM

---

## Organização

Feature-Based MVVM

Estrutura:

feature/
auth/
dashboard/
subscriptions/
simulator/
profile/

---

# Estrutura Inicial

app/

core/
components/
theme/
navigation/
catalog/
utils/

feature/
auth/
dashboard/
subscriptions/
simulator/
profile/

data/
local/
remote/

model/

---

# Design System

Base:

Material Design 3

---

# Componentes Oficiais

PayFlowTopBar

PayFlowBottomNavigation

PayFlowButton

PayFlowTextField

PayFlowDropdown

PayFlowSearchBar

PayFlowCard

PayFlowMetricCard

PayFlowSubscriptionCard

PayFlowProfileHeader

PayFlowSettingsItem

PayFlowChip

PayFlowStatusBadge

PayFlowLoadingState

PayFlowEmptyState

PayFlowConfirmationDialog

---

## Regra

Antes de criar um novo componente:

1. Verificar se já existe em core/components
2. Caso não exista, propor ao grupo
3. Construir de forma compartilhada
4. Disponibilizar para reutilização

---

# DesignSystemScreen

Tela interna destinada a exibir todos os componentes reutilizáveis do projeto.

Objetivos:

- Documentação viva
- Demonstração visual
- Padronização do time
- Facilitar implementação

---

# Entidades

## Subscription

Campos previstos:

- id
- userId
- serviceId
- serviceName
- category
- plan (opcional)
- price
- billingFrequency
- billingDay
- paymentMethod (opcional)
- notes (opcional)
- status
- createdAt
- cancelledAt

---

# Decisões Arquiteturais

## DA-001

Projeto baseado em MVVM.

---

## DA-002

Organização por funcionalidades (Feature-Based).

---

## DA-003

Material Design 3 como base visual.

---

## DA-004

Componentes compartilhados obrigatórios.

---

## DA-005

Assinaturas não serão removidas fisicamente.

---

## DA-006

Sessão persistente.

---

## DA-007

Google Sign-In.

---

# Roadmap

## Sprint 0

- README
- Estrutura do Projeto
- Navigation
- Design System
- DesignSystemScreen

## Sprint 1

- Login
- Dashboard
- Assinaturas
- Cadastro

## Sprint 2

- Savings Simulator
- Perfil
- Persistência
- API

## Sprint 3

- Refinamentos
- Testes
- Documentação
- Apresentação

---

# Equipe

Grupo 04

- Sérgio Oliveira
- Wesley
- Pietro
- Marcelo
- Lucas
- Gutemberg

Responsabilidades serão definidas após a conclusão da Sprint 0.

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
- Design System Compartilhado
- Developer Playground

---

## Não Incluído

- Open Banking
- PIX como método de pagamento real
- Pagamento de contas
- Controle financeiro completo
- Assinaturas compartilhadas
- Multiusuário colaborativo
- Controle de inadimplência
- Notificações avançadas
- Integrações reais com Netflix, Spotify ou outros serviços
- Envio automático de e-mails
- Administração de cartões bancários

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

## Rotas Primárias

As rotas principais serão acessíveis através da Bottom Navigation.

- 🏠 Dashboard
- 📺 Assinaturas
- 💰 Simulador
- 👤 Perfil
- ⋯ Developer Playground

---

## Rotas Secundárias

As rotas secundárias representam fluxos contextuais e não fazem parte da Bottom Navigation.

Exemplos:

- ➕ Nova Assinatura
- 📄 Detalhes da Assinatura
- ✏️ Editar Assinatura

Acesso através de:

- Floating Action Button (FAB)
- Clique em itens da lista
- Botões específicos da interface

---

# Telas

## 1. Login

Entrada do usuário através da conta Google.

---

## 2. Dashboard

Visão geral do usuário.

Indicadores previstos:

- Projeção de Gasto Mensal
- Projeção de Gasto Anual
- Assinaturas Ativas
- Próximos Vencimentos

---

## 3. Assinaturas

Listagem das assinaturas cadastradas.

Funcionalidades:

- Pesquisa
- Filtros
- Visualização
- Cadastro
- Acesso aos detalhes

---

## 4. Nova Assinatura

Cadastro guiado progressivo.

Campos:

- Serviço
- Plano (opcional)
- Valor
- Periodicidade
- Dia da Cobrança
- Método de Pagamento (opcional)
- Observações (opcional)

---

## 5. Detalhes da Assinatura

Visualização completa da assinatura.

Funcionalidades:

- Editar
- Cancelar
- Reativar

---

## 6. Savings Simulator

Simulação de economia.

Permite selecionar assinaturas para projeção de economia.

Indicadores:

- Economia Mensal
- Economia Anual

---

## 7. Perfil

Dados do usuário e configurações.

Funcionalidades previstas:

- Foto do usuário
- Nome
- E-mail
- Tema
- Preferências futuras
- Logout

---

## 8. Developer Playground

Área interna destinada à demonstração e validação dos componentes compartilhados.

Objetivos:

- Documentação viva
- Catálogo de componentes
- Teste visual
- Padronização visual
- Apoio aos desenvolvedores

---

# Categorias

- STREAMING
- MUSIC
- GAMES
- AI_PRODUCTIVITY
- CLOUD_STORAGE
- EDUCATION
- OTHER

---

# Status das Assinaturas

Possíveis estados:

ACTIVE

CANCELLED

Não haverá exclusão física.

Fluxo:

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
- Adobe Creative Cloud

Também será possível cadastrar:

Outro Serviço

---

# API

A API será utilizada para fornecer o catálogo de serviços digitais.

Objetivos:

- Catálogo centralizado
- Evolução futura
- Atendimento ao requisito acadêmico do projeto

---

# Arquitetura

## Padrão

MVVM

---

## Organização

Feature-Based MVVM

Cada feature possui seus próprios pacotes:

```text
ui/
viewmodel/
model/
```

---

# Estrutura Inicial

```text
app/

core/
├── components/
├── navigation/
├── theme/
├── catalog/
├── state/
└── utils/

feature/
├── auth/
├── dashboard/
├── subscriptions/
├── simulator/
├── profile/
└── developerplayground/

data/
├── local/
│   ├── dao/
│   ├── entity/
│   └── database/
│
└── remote/
    ├── api/
    ├── dto/
    └── repository/

model/
```

---

# Design System

Base:

Material Design 3

---

# Componentes Oficiais

## Layout

- PayFlowTopBar
- PayFlowBottomNavigation

## Entradas

- PayFlowTextField
- PayFlowDropdown
- PayFlowSearchBar

## Ações

- PayFlowButton
- PayFlowConfirmationDialog

## Conteúdo

- PayFlowCard
- PayFlowMetricCard
- PayFlowSubscriptionCard

## Perfil

- PayFlowProfileHeader
- PayFlowSettingsItem

## Feedback

- PayFlowLoadingState
- PayFlowEmptyState
- PayFlowStatusBadge

## Auxiliares

- PayFlowChip

---

## Regra

Antes de criar qualquer componente novo:

1. Verificar se já existe em `core/components`
2. Caso não exista, propor ao grupo
3. Construir de forma compartilhada
4. Disponibilizar para reutilização

---

# Developer Playground

Tela interna destinada à demonstração dos componentes compartilhados.

Conteúdo previsto:

- PayFlowButton
- PayFlowCard
- PayFlowTextField
- PayFlowSearchBar
- PayFlowMetricCard
- PayFlowSubscriptionCard
- PayFlowProfileHeader
- PayFlowSettingsItem
- PayFlowLoadingState
- PayFlowEmptyState
- PayFlowConfirmationDialog

---

# Entidades

## User

Campos:

- id
- displayName
- email
- photoUrl

---

## Subscription

Campos:

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

## Enums

### Category

- STREAMING
- MUSIC
- GAMES
- AI_PRODUCTIVITY
- CLOUD_STORAGE
- EDUCATION
- OTHER

### BillingFrequency

- MONTHLY
- YEARLY

### SubscriptionStatus

- ACTIVE
- CANCELLED

### PaymentMethod

- CREDIT_CARD
- PIX
- PAYPAL
- GOOGLE_PLAY
- APP_STORE
- OTHER

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

## DA-008

Feature-Based MVVM.

Cada feature possui:

- ui
- viewmodel
- model

---

## DA-009

Developer Playground como documentação viva do projeto.

---

## DA-010

Rotas secundárias não fazem parte da Bottom Navigation.

---

## DA-011

Design System baseado em Material Design 3.

---

## DA-012

Assinaturas podem possuir múltiplas ocorrências do mesmo serviço.

---

## DA-013

Exclusão física de assinaturas não será permitida.

Fluxo:

ACTIVE → CANCELLED

---

## DA-014

Usuário individual.

Não haverá compartilhamento de assinaturas entre usuários.

---

# Roadmap

## Sprint 0

- README
- Estrutura do Projeto
- Models
- Navigation
- Design System
- Developer Playground

---

## Sprint 1

- Login
- Dashboard
- Assinaturas
- Cadastro

---

## Sprint 2

- Savings Simulator
- Perfil
- Persistência Local
- API

---

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

As responsabilidades serão definidas após a conclusão da Sprint 0.
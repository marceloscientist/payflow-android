# STORY-002 - API Integration

## Branch

feature/api-integration

---

## Dependências

Nenhuma

Pode ser desenvolvida em paralelo com:

- STORY-001-local-persistence-room

---

## Objetivo

Implementar a infraestrutura e a primeira integração de API utilizada pelo projeto.

Esta história existe para atender um dos requisitos obrigatórios do projeto final:

Consumo de API.

---

## Requisito do Professor

O projeto deve consumir pelo menos uma API.

A API pode ser:

- Pública
- Mockada
- Própria
- Firebase
- Supabase
- JSON Server

Para o projeto PayFlow foi definido inicialmente:

API mockada de serviços de assinatura.

---

## Escopo

Implementar a primeira integração remota do aplicativo.

---

## Entregas

### DTO

Criar:

ServiceDto

Exemplo:

- id
- name
- category
- price

---

### ApiService

Implementar endpoint:

getServices()

---

### Repository

Implementar:

SubscriptionApiRepository

---

### Mapper

Criar conversão:

ServiceDto
↓
Subscription

---

### Mock API

Utilizar uma das opções:

- MockAPI
- JSON Server
- Mockoon
- Serviço equivalente

---

## Estrutura Esperada

data/

remote/

api/

dto/

repository/

---

## Critérios de Aceite

- [ ] Retrofit funcionando
- [ ] Endpoint implementado
- [ ] DTO criado
- [ ] Repository criado
- [ ] Conversão DTO → Domain funcionando
- [ ] Chamada de API executando sem erro
- [ ] Dados exibidos em log ou teste temporário

---

## ViewModel

Opcional

Pode ser utilizado um ViewModel temporário para validação.

---

## Persistência

Não implementar Room nesta história.

---

## Navegação

Não alterar navegação existente.

---

## Design System

Não criar componentes novos.

---

## Fora de Escopo

Não implementar:

- Dashboard
- Cadastro de assinatura
- Perfil
- Simulador

---

## Observações Técnicas

Utilizar:

- Retrofit
- Gson Converter
- Repository Pattern

Respeitar:

- MVVM
- Feature-Based Architecture

---

## Resultado Esperado

Ao final desta história o aplicativo possuirá uma API funcional integrada à infraestrutura já criada.

Esta história desbloqueia:

- STORY-005-subscriptions-list
- STORY-007-dashboard

---

## Referências

Arquivos já existentes:

- AppConfig.kt
- ApiClient.kt
- ApiService.kt
- BaseRepository.kt
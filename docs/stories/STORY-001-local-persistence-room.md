# STORY-001 - Local Persistence (Room)

## Branch

feature/room-persistence

---

## Dependências

Nenhuma

Esta é uma história fundacional.

Pode iniciar imediatamente.

---

## Objetivo

Implementar a persistência local oficial do aplicativo utilizando Room.

Esta história existe para atender um dos requisitos obrigatórios do projeto final:

Persistência local de dados.

---

## Requisito do Professor

O projeto deve possuir persistência local utilizando:

- Room
- DataStore
- SQLite
- Cache Local

ou solução equivalente.

Para o projeto PayFlow foi definido o uso de Room.

---

## Escopo

Implementar a estrutura inicial de persistência das assinaturas.

---

## Entregas

### Entity

Criar:

SubscriptionEntity

---

### DAO

Criar:

SubscriptionDao

Operações mínimas:

- Inserir assinatura
- Atualizar assinatura
- Remover assinatura
- Buscar todas assinaturas
- Buscar assinatura por id

---

### Database

Atualizar:

PayFlowDatabase

registrando:

SubscriptionEntity
SubscriptionDao

---

### Mapper

Criar mapeamento:

Subscription
⇄
SubscriptionEntity

---

### Repository

Criar:

SubscriptionRepository

utilizando Room.

---

## Estrutura Esperada

data/

local/

database/
dao/

repository/

---

## Critérios de Aceite

- [ ] Room configurado
- [ ] SubscriptionEntity criada
- [ ] SubscriptionDao criado
- [ ] PayFlowDatabase atualizado
- [ ] Repository criado
- [ ] Inserção funcionando
- [ ] Consulta funcionando
- [ ] Atualização funcionando
- [ ] Exclusão funcionando

---

## Fora de Escopo

Não implementar:

- Retrofit
- API
- Dashboard
- Navegação
- Simulador

---

## Observações Técnicas

Utilizar:

- Room
- Kotlin Coroutines
- Repository Pattern

Respeitar:

- MVVM
- Feature-Based Architecture

---

## Resultado Esperado

Ao final desta história o aplicativo possuirá uma estrutura local funcional para persistência de assinaturas.

Essa história desbloqueia:

- STORY-004-add-subscription
- STORY-005-subscriptions-list
- STORY-006-subscription-details
- STORY-007-dashboard
- STORY-009-simulator
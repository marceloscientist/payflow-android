# STORY-004 - Add Subscription

## Branch

feature/add-subscription

---

## Dependências

Obrigatórias:

- STORY-001-local-persistence-room

Pode iniciar somente após:

- SubscriptionEntity existir
- SubscriptionDao existir
- SubscriptionRepository existir

---

## Objetivo

Permitir que o usuário cadastre uma nova assinatura no aplicativo PayFlow.

Essa é a principal ação de negócio do MVP.

Sem essa funcionalidade:

- não existe assinatura cadastrada
- não existe Dashboard útil
- não existe histórico útil
- não existe simulador útil

---

## Contexto

O professor exige uma tela de:

Cadastro/Ação Principal

O PayFlow utiliza como ação principal:

Cadastrar Assinatura.

【1-4ffca9】

---

## Escopo

Implementar:

Nova Assinatura

com persistência local utilizando Room.

---

## Informações obrigatórias

Cadastrar:

- Nome do serviço
- Categoria
- Valor
- Frequência
- Data de cobrança

---

## Componentes Obrigatórios

Utilizar:

- PayFlowTextField
- PayFlowDropdown
- PayFlowButton
- PayFlowConfirmationDialog

Não criar novos componentes.

---

## Estrutura Esperada

feature/addsubscription

```text
ui/
viewmodel/
model/
```

---

## ViewModel

Criar:

AddSubscriptionViewModel

Obrigatório:

- herdar BaseViewModel
- utilizar UiState

---

## Persistência

Utilizar:

SubscriptionRepository

Fluxo:

View
↓
ViewModel
↓
Repository
↓
Dao
↓
Room

---

## Navegação

Após salvar:

Voltar para:

Subscriptions

ou

Detail Screen

(a definir posteriormente)

---

## Critérios de Aceite

- [ ] Usuário consegue informar dados
- [ ] Dados são validados
- [ ] Usuário consegue salvar
- [ ] Dados são persistidos em Room
- [ ] Não ocorre crash
- [ ] UiState utilizado
- [ ] BaseViewModel utilizado

---

## Estados Esperados

Loading

Success

Error

---

## Fora de Escopo

Não implementar:

- API
- Dashboard
- Sincronização remota
- Notificações

---

## Testes Manuais

Validar:

Cadastrar Netflix

Valor:
44,90

Categoria:
Streaming

↓

Fechar App

↓

Abrir App

↓

Assinatura continua existindo

---

## Observações Técnicas

Não utilizar dados mockados permanentes.

Persistir utilizando Room.

---

## Resultado Esperado

Ao final da história o usuário consegue cadastrar uma assinatura e ela permanece disponível mesmo após o fechamento do aplicativo.

---

## Stories Desbloqueadas

Esta história desbloqueia:

- STORY-005-subscriptions-list
- STORY-006-subscription-details
- STORY-007-dashboard
- STORY-009-simulator
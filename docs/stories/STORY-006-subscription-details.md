# STORY-006 - Subscription Details

## Branch

feature/subscription-details

---

## Dependências

Obrigatórias:

- STORY-001-local-persistence-room
- STORY-005-subscriptions-list

Pode iniciar somente após:

- SubscriptionRepository implementado
- SubscriptionDao implementado
- Tela de Assinaturas funcionando

---

## Objetivo

Implementar a visualização detalhada de uma assinatura cadastrada.

Esta história atende ao requisito do professor de possuir uma tela de:

Detalhe / Confirmação

adaptada ao contexto do projeto PayFlow.

---

## Requisito do Professor

O projeto deve possuir pelo menos uma tela de:

- Detalhamento de informação

ou

- Confirmação de operação

Para o PayFlow será utilizada:

Visualização detalhada de uma assinatura.

---

## Escopo

Implementar:

Subscription Details Screen

---

## Informações Exibidas

Exibir:

- Nome do serviço
- Categoria
- Valor
- Frequência de cobrança
- Próxima cobrança
- Status
- Data de cadastro

---

## Funcionalidades

### Visualização

Permitir acesso aos detalhes completos da assinatura selecionada.

---

### Exclusão

Permitir excluir uma assinatura.

Antes da exclusão deve ser exibido:

PayFlowConfirmationDialog

---

### Edição

Exibir botão de edição.

No MVP o botão pode exibir:

"Funcionalidade disponível em breve"

sem necessidade de implementação completa.

---

## Componentes Obrigatórios

Utilizar:

- PayFlowCard
- PayFlowStatusBadge
- PayFlowButton
- PayFlowConfirmationDialog

Não criar novos componentes.

---

## Estrutura Esperada

feature/subscriptiondetails

├── ui
├── viewmodel
└── model

---

## ViewModel

Criar:

SubscriptionDetailsViewModel

Obrigatório:

- Herdar BaseViewModel
- Utilizar UiState

---

## Persistência

Utilizar:

SubscriptionRepository

Fluxo esperado:

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

Origem:

Subscriptions Screen

Fluxo:

Subscriptions
↓
Subscription Details

---

## API

Não obrigatória nesta história.

Todos os dados podem ser obtidos através do Room.

---

## Critérios de Aceite

- [ ] Assinatura carregada corretamente
- [ ] Todos os dados exibidos corretamente
- [ ] Status exibido corretamente
- [ ] Exclusão funcionando
- [ ] Confirmação antes da exclusão
- [ ] Navegação funcionando
- [ ] UiState utilizado
- [ ] BaseViewModel utilizado
- [ ] Sem criação de componentes novos

---

## Estados Esperados

- Loading
- Success
- Error

---

## Fora de Escopo

Não implementar:

- Dashboard
- Perfil
- Simulador
- API
- Notificações
- Edição completa da assinatura

---

## Testes Manuais

### Cenário 1

Abrir lista de assinaturas

↓

Selecionar Netflix

↓

Abrir tela de detalhes

Resultado esperado:

Todos os dados exibidos corretamente.

---

### Cenário 2

Abrir detalhes

↓

Excluir assinatura

↓

Confirmar exclusão

Resultado esperado:

Assinatura removida da base local.

---

### Cenário 3

Tentar abrir uma assinatura inexistente

Resultado esperado:

Exibir estado de erro.

---

## Observações Técnicas

Não duplicar dados.

Todos os dados devem ser carregados através de:

SubscriptionRepository

---

## Resultado Esperado

Ao final da história o usuário consegue visualizar os detalhes completos de uma assinatura e removê-la utilizando um fluxo seguro de confirmação.

---

## Stories Desbloqueadas

Esta história contribui para:

- STORY-007-dashboard
- STORY-009-simulator

pois garante disponibilidade e consistência dos dados cadastrados.
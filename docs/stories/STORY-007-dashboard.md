# STORY-007 - Dashboard

## Branch

feature/dashboard

---

## Dependências

Obrigatórias:

- STORY-001-local-persistence-room
- STORY-005-subscriptions-list

Recomendadas:

- STORY-002-api-integration

Pode iniciar somente após:

- SubscriptionRepository implementado
- Lista de assinaturas funcionando
- Room operacional

---

## Objetivo

Implementar a tela principal do aplicativo PayFlow.

O Dashboard será responsável por apresentar um resumo financeiro das assinaturas cadastradas pelo usuário.

Esta tela atende diretamente ao requisito do professor de possuir uma:

Home / Dashboard

com indicadores relevantes do tema escolhido.

---

## Requisito do Professor

O projeto deve possuir uma tela principal contendo:

- Indicadores
- Resumo
- Informações relevantes do negócio

Para o PayFlow:

Resumo financeiro das assinaturas.

---

## Escopo

Implementar:

Dashboard Screen

---

## Informações Exibidas

### Gasto Mensal

Exibir o valor total das assinaturas recorrentes por mês.

Utilizar:

PayFlowMetricCard

---

### Gasto Anual

Exibir projeção anual baseada nas assinaturas cadastradas.

Utilizar:

PayFlowMetricCard

---

### Assinaturas Ativas

Exibir quantidade de assinaturas cadastradas.

Utilizar:

PayFlowMetricCard

---

### Próximo Vencimento

Exibir a próxima assinatura que será cobrada.

Utilizar:

PayFlowCard

---

### Economia Potencial

Exibir quanto poderia ser economizado caso assinaturas pouco utilizadas fossem canceladas.

Utilizar:

PayFlowMetricCard

---

## Diferencial do Projeto

Implementar destaque para:

Assinaturas pouco utilizadas.

Este é um dos diferenciais sugeridos para o projeto PayFlow.

Exemplo:

"Você pode economizar R$ 44,90 cancelando Netflix."

---

## Componentes Obrigatórios

Utilizar:

- PayFlowMetricCard
- PayFlowCard
- PayFlowStatusBadge
- PayFlowLoadingState
- PayFlowEmptyState

Não criar novos componentes.

---

## Estrutura Esperada

feature/dashboard

├── ui
├── viewmodel
└── model

---

## ViewModel

Criar:

DashboardViewModel

Obrigatório:

- Herdar BaseViewModel
- Utilizar UiState

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
Room

---

## API

Opcional nesta versão.

Caso a STORY-002 esteja pronta:

- Exibir serviços sugeridos
- Exibir preços médios

Caso contrário:

- Utilizar apenas dados locais

---

## Critérios de Aceite

- [ ] Gasto mensal calculado corretamente
- [ ] Gasto anual calculado corretamente
- [ ] Quantidade de assinaturas exibida corretamente
- [ ] Próximo vencimento exibido corretamente
- [ ] Economia potencial exibida
- [ ] Loading funcionando
- [ ] Empty State funcionando
- [ ] UiState utilizado
- [ ] BaseViewModel utilizado
- [ ] Não criar componentes visuais novos

---

## Estados Esperados

- Loading
- Success
- Empty
- Error

---

## Fora de Escopo

Não implementar:

- Gráficos complexos
- Integração bancária
- Machine Learning
- Notificações Push

---

## Testes Manuais

### Cenário 1

Cadastrar:

- Netflix (R$ 44,90)
- Spotify (R$ 21,90)

Resultado esperado:

Gasto Mensal:

R$ 66,80

---

### Cenário 2

Visualizar Dashboard sem assinaturas.

Resultado esperado:

Exibir EmptyState.

---

### Cenário 3

Cadastrar múltiplas assinaturas.

Resultado esperado:

Quantidade de assinaturas exibida corretamente.

---

### Cenário 4

Possuir assinatura marcada como pouco utilizada.

Resultado esperado:

Exibir economia potencial.

---

## Observações Técnicas

Não duplicar cálculos na View.

Todos os cálculos devem ocorrer:

DashboardViewModel
↓
Repository

---

## Resultado Esperado

Ao final da história o usuário consegue visualizar um resumo financeiro completo das suas assinaturas e identificar oportunidades de economia.

---

## Stories Desbloqueadas

Esta história contribui diretamente para:

- STORY-009-simulator

pois reutilizará parte dos cálculos de gasto mensal, anual e economia potencial.
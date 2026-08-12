# Contributing Guide

Antes de iniciar qualquer desenvolvimento:

Leia obrigatoriamente:

- docs/ai-context/project-context.md
- docs/architecture/architecture.md
- docs/decisions/architecture-decisions.md
- docs/prompts/feature-prompt.md

---

# Fluxo de Desenvolvimento

1. Escolher uma Story.

2. Ler a Story completamente.

3. Verificar componentes existentes no Developer Playground.

4. Implementar apenas a feature atribuída.

5. Validar compilação.

6. Abrir Pull Request.

---

# O que pode ser alterado

- Arquivos da própria Feature
- ViewModel da própria Feature
- Model da própria Feature
- Repository da própria Feature

---

# O que NÃO pode ser alterado sem alinhamento

## Arquitetura

- core/navigation
- core/base
- core/state
- core/config

## Design System

- core/components

## Infraestrutura

- data/remote/api/ApiClient.kt

- data/local/database/PayFlowDatabase.kt

---

# Boas Práticas

✅ Reutilizar componentes existentes

✅ Utilizar UiState

✅ Herdar BaseViewModel

✅ Seguir MVVM

✅ Seguir Feature-Based

---

# Más Práticas

❌ Duplicar componentes

❌ Duplicar Models

❌ Criar navegação paralela

❌ Criar outro padrão de estado

❌ Hardcode de URLs

❌ Implementar fora da Story

---

# Pull Request Checklist

- [ ] Story implementada
- [ ] Código compila
- [ ] Design System reutilizado
- [ ] UiState utilizado
- [ ] BaseViewModel utilizado
- [ ] Sem código morto
- [ ] Sem TODOs esquecidos

---

# Uso de IA

Permitido:

- Copilot
- Cursor
- ChatGPT
- Claude
- Gemini

Obrigatório:

Fornecer junto ao prompt:

- project-context.md
- architecture.md
- feature-prompt.md

para evitar implementações divergentes.
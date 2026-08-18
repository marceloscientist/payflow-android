package io.payflow.android.feature.subscriptions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.state.UiState
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.model.Subscription

class SubscriptionDetailsViewModel(
    private val subscriptionId: String,
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel<Subscription>() {

    init {
        loadSubscription()
    }

    fun loadSubscription() = launch {
        updateState(UiState.Loading)
        try {
            subscriptionRepository.syncCatalogBackedSubscriptions()
            val subscription = subscriptionRepository.getById(subscriptionId)
            if (subscription == null) {
                updateState(UiState.Error("Assinatura não encontrada"))
            } else {
                updateState(UiState.Success(subscription))
            }
        } catch (exception: Exception) {
            updateState(
                UiState.Error(
                    exception.message ?: "Erro ao carregar os detalhes da assinatura"
                )
            )
        }
    }

    fun deleteSubscription(onDeleted: () -> Unit) = launch {
        val subscription = (uiState.value as? UiState.Success)?.data ?: return@launch
        updateState(UiState.Loading)
        try {
            subscriptionRepository.delete(subscription)
            onDeleted()
        } catch (exception: Exception) {
            updateState(
                UiState.Error(
                    exception.message ?: "Erro ao excluir a assinatura"
                )
            )
        }
    }

    companion object {
        fun factory(
            subscriptionId: String,
            subscriptionRepository: SubscriptionRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SubscriptionDetailsViewModel::class.java)) {
                    return SubscriptionDetailsViewModel(
                        subscriptionId = subscriptionId,
                        subscriptionRepository = subscriptionRepository
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}



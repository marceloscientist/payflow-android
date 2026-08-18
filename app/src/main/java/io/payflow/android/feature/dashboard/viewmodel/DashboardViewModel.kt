package io.payflow.android.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.state.UiState
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.model.SubscriptionStatus
import kotlinx.coroutines.flow.collect

class DashboardViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel<DashboardUiState>() {

    init {
        observeSubscriptions()
    }

    private fun observeSubscriptions() = launch {
        updateState(UiState.Loading)

        try {
            subscriptionRepository.syncCatalogBackedSubscriptions()
            subscriptionRepository.getAll().collect { subscriptions ->
                val activeSubscriptions = subscriptions.filter {
                    it.status == SubscriptionStatus.ACTIVE
                }

                if (activeSubscriptions.isEmpty()) {
                    updateState(UiState.Empty)
                    return@collect
                }

                updateState(
                    UiState.Success(
                        DashboardUiState.fromSubscriptions(subscriptions)
                    )
                )
            }
        } catch (exception: Exception) {
            updateState(
                UiState.Error(
                    exception.message ?: "Erro ao carregar o dashboard"
                )
            )
        }
    }

    companion object {

        fun factory(
            subscriptionRepository: SubscriptionRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                        return DashboardViewModel(subscriptionRepository) as T
                    }

                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
    }
}

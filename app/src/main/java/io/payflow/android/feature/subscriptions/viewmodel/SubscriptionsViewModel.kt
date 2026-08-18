package io.payflow.android.feature.subscriptions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.state.UiState
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.feature.subscriptions.model.SubscriptionFilter
import io.payflow.android.feature.subscriptions.model.applyFilter
import io.payflow.android.model.Subscription
import kotlinx.coroutines.flow.collect

class SubscriptionsViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel<SubscriptionsUiState>() {

    private var allSubscriptions: List<Subscription> = emptyList()

    private var currentFilter = SubscriptionFilter()

    init {
        observeSubscriptions()
    }

    private fun observeSubscriptions() = launch {
        updateState(UiState.Loading)

        try {
            subscriptionRepository.syncCatalogBackedSubscriptions()
            subscriptionRepository.getAll().collect { subscriptions ->
                allSubscriptions = subscriptions
                publishFilteredState()
            }
        } catch (exception: Exception) {
            updateState(
                UiState.Error(
                    message = exception.message ?: "Erro ao carregar assinaturas"
                )
            )
        }
    }

    fun onQueryChange(query: String) {
        currentFilter = currentFilter.copy(query = query)
        publishFilteredState()
    }

    private fun publishFilteredState() {
        val filtered = allSubscriptions.applyFilter(currentFilter)

        if (filtered.isEmpty()) {
            updateState(UiState.Empty)
            return
        }

        updateState(
            UiState.Success(
                SubscriptionsUiState(
                    subscriptions = filtered
                )
            )
        )
    }

    companion object {

        fun factory(
            subscriptionRepository: SubscriptionRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(SubscriptionsViewModel::class.java)) {
                        return SubscriptionsViewModel(subscriptionRepository) as T
                    }

                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
    }
}

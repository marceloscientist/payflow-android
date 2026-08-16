package io.payflow.android.feature.simulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.payflow.android.core.base.BaseViewModel
import io.payflow.android.core.state.UiState
import io.payflow.android.data.repository.SubscriptionRepository
import io.payflow.android.feature.simulator.model.SubscriptionSimulatorData
import io.payflow.android.model.BillingFrequency
import io.payflow.android.model.Subscription
import io.payflow.android.model.SubscriptionStatus
import kotlinx.coroutines.flow.collect

class SubscriptionSimulatorViewModel(
	private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel<SubscriptionSimulatorData>() {

	private var subscriptions: List<Subscription> = emptyList()
	private val selectedIds = mutableSetOf<String>()

	init {
		observeSubscriptions()
	}

	private fun observeSubscriptions() = launch {
		updateState(UiState.Loading)

		try {
			subscriptionRepository.getAll().collect { loadedSubscriptions ->
				subscriptions = loadedSubscriptions
					.filter { it.status == SubscriptionStatus.ACTIVE }
				selectedIds.retainAll(subscriptions.map { it.id }.toSet())
				publishState()
			}
		} catch (exception: Exception) {
			updateState(
				UiState.Error(
					exception.message ?: "Erro ao carregar assinaturas"
				)
			)
		}
	}

	fun toggleSubscription(subscriptionId: String) {
		if (!subscriptions.any { it.id == subscriptionId }) return

		if (!selectedIds.add(subscriptionId)) {
			selectedIds.remove(subscriptionId)
		}
		publishState()
	}

	fun clearSelection() {
		selectedIds.clear()
		publishState()
	}

	private fun publishState() {
		if (subscriptions.isEmpty()) {
			updateState(UiState.Empty)
			return
		}

		val selectedSubscriptions = subscriptions.filter { it.id in selectedIds }
		val monthlySavings = selectedSubscriptions.sumOf { subscription ->
			when (subscription.billingFrequency) {
				BillingFrequency.MONTHLY -> subscription.price
				BillingFrequency.YEARLY -> subscription.price / 12
			}
		}

		updateState(
			UiState.Success(
				SubscriptionSimulatorData(
					subscriptions = subscriptions,
					selectedSubscriptions = selectedSubscriptions,
					monthlySavings = monthlySavings,
					annualSavings = monthlySavings * 12
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
					if (modelClass.isAssignableFrom(SubscriptionSimulatorViewModel::class.java)) {
						return SubscriptionSimulatorViewModel(subscriptionRepository) as T
					}
					throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
				}
			}
	}
}

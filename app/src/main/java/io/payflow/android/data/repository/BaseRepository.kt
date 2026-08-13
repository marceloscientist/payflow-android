package io.payflow.android.data.repository

abstract class BaseRepository {

	protected suspend fun <T> safeApiCall(
		block: suspend () -> T
	): Result<T> {
		return runCatching {
			block()
		}
	}
}
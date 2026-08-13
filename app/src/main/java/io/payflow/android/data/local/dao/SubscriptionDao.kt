package io.payflow.android.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.payflow.android.data.local.entity.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao : BaseDao {

    @Query("SELECT * FROM subscriptions ORDER BY createdAt DESC")
    fun getAll(): Flow<List<SubscriptionEntity>>

    @Query("SELECT * FROM subscriptions WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): SubscriptionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SubscriptionEntity)

    @Update
    suspend fun update(entity: SubscriptionEntity)

    @Delete
    suspend fun delete(entity: SubscriptionEntity)
}

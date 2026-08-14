package io.payflow.android.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.payflow.android.data.local.dao.SubscriptionDao
import io.payflow.android.data.local.entity.SubscriptionEntity

@Database(
    entities = [SubscriptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PayFlowDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao
}

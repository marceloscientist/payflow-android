package io.payflow.android.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {

        private const val DATABASE_NAME = "payflow.db"

        @Volatile
        private var instance: PayFlowDatabase? = null

        /**
         * Instância única do banco.
         *
         * Compartilhar a mesma instância entre as telas garante que o
         * InvalidationTracker do Room notifique os Flows ativos quando
         * outra tela insere ou altera dados.
         */
        fun getInstance(context: Context): PayFlowDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    PayFlowDatabase::class.java,
                    DATABASE_NAME
                ).build().also { instance = it }
            }
    }
}

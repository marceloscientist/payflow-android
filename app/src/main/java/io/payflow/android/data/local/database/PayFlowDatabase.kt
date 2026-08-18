package io.payflow.android.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import io.payflow.android.data.local.dao.SubscriptionDao
import io.payflow.android.data.local.entity.SubscriptionEntity

@Database(
    entities = [SubscriptionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class PayFlowDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao

    companion object {

        private const val DATABASE_NAME = "payflow.db"
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE subscriptions ADD COLUMN logoUrl TEXT"
                )
            }
        }

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
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { instance = it }
            }
    }
}

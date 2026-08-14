package io.payflow.android.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.payflow.android.data.local.dao.SubscriptionDao
import io.payflow.android.data.local.database.PayFlowDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PayFlowDatabase =
        Room.databaseBuilder(
            context,
            PayFlowDatabase::class.java,
            "payflow.db"
        ).build()

    @Provides
    fun provideSubscriptionDao(database: PayFlowDatabase): SubscriptionDao =
        database.subscriptionDao()
}


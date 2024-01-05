package g58112.mobg5.snookernews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import g58112.mobg5.snookernews.data.remote.QueryApi
import g58112.mobg5.snookernews.util.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * A Dagger Hilt module that provides Retrofit related dependencies.
 *
 * This module includes providers for the Retrofit instance and the API service interfaces.
 */
@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    /**
     * Provides a singleton instance of [Retrofit].
     *
     * This function configures and creates a singleton instance of [Retrofit]
     * configured with the base URL and a Gson converter factory.
     *
     * @return A configured [Retrofit] instance.
     */
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides the API service interface for querying rankings.
     *
     * This function creates and provides the [QueryApi] service interface
     * using the provided [Retrofit] instance.
     *
     * @param retrofit The [Retrofit] instance used to create the [QueryApi] service.
     * @return An implementation of the [QueryApi] interface.
     */
    @Singleton
    @Provides
    fun providesRankingApi(retrofit: Retrofit): QueryApi {
        return retrofit.create(QueryApi::class.java)
    }
}
package g58112.mobg5.snookernews.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import g58112.mobg5.snookernews.data.AuthRepository
import g58112.mobg5.snookernews.data.AuthRepositoryImpl
import javax.inject.Singleton

/**
 * A Hilt module that provides dependencies for the application.
 *
 * This module includes providers for Firebase authentication and repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides a singleton instance of [FirebaseAuth].
     *
     * This function creates and provides a singleton instance of [FirebaseAuth]
     * which can be used across the application for handling authentication.
     *
     * @return An instance of [FirebaseAuth].
     */
    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    /**
     * Provides a singleton implementation of [AuthRepository].
     *
     * This function creates and provides a singleton instance of [AuthRepositoryImpl],
     * a concrete implementation of [AuthRepository], using the provided [FirebaseAuth] instance.
     *
     * @param firebaseAuth The [FirebaseAuth] instance to be used in the repository.
     * @return An instance of [AuthRepository].
     */
    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }
}

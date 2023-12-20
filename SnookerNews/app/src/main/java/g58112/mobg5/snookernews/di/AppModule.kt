package g58112.mobg5.snookernews.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import g58112.mobg5.snookernews.data.AuthRepository
import g58112.mobg5.snookernews.data.AuthRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }
}
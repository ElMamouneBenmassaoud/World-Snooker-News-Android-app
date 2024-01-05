package g58112.mobg5.snookernews.data

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import g58112.mobg5.snookernews.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the authentication operations for the application.
 */
interface AuthRepository {

    /**
     * Logs in a user with their email and password.
     *
     * This function returns a [Flow] that emits the status of the login operation.
     * The emitted [Resource] includes an [AuthResult] containing user authentication details on success.
     *
     * @param email The email address of the user.
     * @param password The password of the user.
     * @return A [Flow] of [Resource] containing [AuthResult].
     */
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>

    /**
     * Registers a new user with their email and password.
     *
     * This function returns a [Flow] that emits the status of the registration operation.
     * The emitted [Resource] includes an [AuthResult] containing user registration details on success.
     *
     * @param email The email address of the new user.
     * @param password The password for the new user.
     * @return A [Flow] of [Resource] containing [AuthResult].
     */
    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>

    /**
     * Signs in a user using Google Sign-In credentials.
     *
     * This function returns a [Flow] that emits the status of the Google Sign-In operation.
     * The emitted [Resource] includes an [AuthResult] containing user authentication details on success.
     *
     * @param credential The authentication credentials from Google Sign-In.
     * @return A [Flow] of [Resource] containing [AuthResult].
     */
    fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>

    /**
     * Signs out the currently logged-in user.
     *
     * This is a suspend function and should be called from within a coroutine or another suspend function.
     */
    suspend fun signOut()
}

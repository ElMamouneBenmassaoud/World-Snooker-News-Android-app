package g58112.mobg5.snookernews.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import g58112.mobg5.snookernews.util.Constant.BASE_URL
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


// Configuration JSON pour ignorer les clés inconnues lors de la désérialisation


// Initialisation de l'instance Retrofit.
// Utilise Kotlin Serialization pour la conversion JSON.
// .baseUrl définit l'URL de base pour toutes les requêtes.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

// Interface définissant les méthodes pour communiquer avec l'API.
interface AuthApiService {
    @GET("snooker/trial/v2/en/rankings.json")
    suspend fun authenticate(
        @Query("api_key") apiKey: String,
    ): Response<AuthResponse> // Type de retour Response<Unit> indiquant qu'on attend une réponse mais sans traitement du corps.
}

// Singleton pour accéder au service API.
// Utilise une initialisation paresseuse pour créer le service API.
// Cela garantit que le service n'est créé qu'une seule fois et seulement lorsqu'il est utilisé.
object AuthApi {
    val retrofitService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
}

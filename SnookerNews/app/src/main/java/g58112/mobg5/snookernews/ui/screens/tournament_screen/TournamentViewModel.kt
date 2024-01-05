package g58112.mobg5.snookernews.ui.screens.tournament_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import g58112.mobg5.snookernews.domaine.ranking.GetRankUseCase
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.domaine.tournament.GetTournamentUseCase
import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Tournament Screen.
 *
 * This ViewModel handles operations related to fetching and displaying tournament data,
 * adding tournaments to favorites, and filtering tournaments based on specific criteria.
 *
 * @property getTournamentUseCase The use case for fetching tournament data.
 */
@HiltViewModel
class TournamentViewModel @Inject constructor(
    private val getTournamentUseCase: GetTournamentUseCase
) : ViewModel() {
    private val _toastMessage = mutableStateOf("")
    val toastMessage: State<String> = _toastMessage

    private val _tournaments = MutableStateFlow(emptyList<CompetitionItem>())
    val tournament: StateFlow<List<CompetitionItem>> get() = _tournaments


    init {
        getTournaments()
    }

    /**
     * Fetches and updates the list of tournaments.
     *
     * This method calls the getTournamentUseCase to retrieve tournament data and updates the UI state accordingly.
     */
    private fun getTournaments() {
        viewModelScope.launch {
            try {
                val tournaments = getTournamentUseCase()
                _tournaments.value = tournaments
            } catch (e: Exception) {
                Log.e(TAG, "Error in getTournaments", e)
            }
        }

    }

    /**
     * Adds a tournament to the user's list of favorites.
     *
     * @param tournament The [CompetitionItem] representing the tournament to be added to favorites.
     */
    fun addTournamentToFavorites(tournament: CompetitionItem) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("TournamentFav")
            .whereEqualTo("userId", user.uid)
            .whereEqualTo("id", tournament.id)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    addTournamentToFirestore(user.uid, tournament, db)
                    _toastMessage.value = "Tournoi ajouté aux favoris."
                } else {
                    Log.d(TAG, "Le tournoi est déjà un favori.")
                    _toastMessage.value = "Le tournoi est déjà dans les favoris."
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erreur lors de la vérification des favoris", e)
            }
    }

    /**
     * Adds a tournament's details to Firestore.
     *
     * @param userId The user ID of the tournament.
     * @param tournament The [CompetitionItem] details of the tournament.
     * @param db The Firestore instance.
     */
    private fun addTournamentToFirestore(
        userId: String,
        tournament: CompetitionItem,
        db: FirebaseFirestore
    ) {
        val tournamentToAdd = hashMapOf(
            "userId" to userId,
            "id" to tournament.id,
            "name" to tournament.name,
            "country_code" to tournament.country_code,
            "gender" to tournament.gender,
            "nameCategory" to tournament.nameCategory
        )
        db.collection("TournamentFav").add(tournamentToAdd)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    /**
     * Filters and updates the list of tournaments based on the provided name.
     *
     * @param name The name to filter the tournaments by.
     */
    fun getTournamentsByName(name: String) {
        viewModelScope.launch {
            try {
                val filteredList = getTournamentUseCase().filter {
                    it.name?.contains(name, ignoreCase = true) ?: false
                }
                _tournaments.value = filteredList
            } catch (e: Exception) {
                Log.e(TAG, "Error in getRankingsByName", e)
            }
        }
    }

    /**
     * Clears the current toast message.
     */
    fun clearToastMessage() {
        _toastMessage.value = ""
    }
}
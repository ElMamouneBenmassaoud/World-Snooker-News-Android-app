package g58112.mobg5.snookernews.ui.screens.tournamentFav_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import g58112.mobg5.snookernews.data.remote.modelTournament.Competition
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import g58112.mobg5.snookernews.domaine.tournament.item.CompetitionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel for the Tournament Favorites Screen.
 *
 * This ViewModel manages operations related to fetching, displaying, and removing favorite tournaments.
 */
class TournamentFavViewModel @Inject constructor() : ViewModel() {
    private val _favoriteTournaments = MutableStateFlow<List<CompetitionItem>>(emptyList())
    val favoriteTournaments: StateFlow<List<CompetitionItem>> = _favoriteTournaments

    private val _toastMessage = mutableStateOf("")
    val toastMessage: State<String> = _toastMessage


    init {
        getFavorites()
    }

    /**
     * Fetches and updates the list of the user's favorite tournaments.
     *
     * This method retrieves the favorite tournaments from Firestore and updates the UI state accordingly.
     */
    private fun getFavorites() {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("TournamentFav")
            .whereEqualTo("userId", user.uid)
            .get()
            .addOnSuccessListener { documents ->
                _favoriteTournaments.value = documents.map { doc ->
                    CompetitionItem(
                        id = doc.getString("id") ?: "",
                        name = doc.getString("name") ?: "",
                        gender = doc.getString("gender") ?: "",
                        country_code = doc.getString("country_code") ?: "",
                        nameCategory = doc.getString("nameCategory") ?: ""
                    )
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erreur lors de la récupération des favoris", e)
            }
    }

    /**
     * Removes a tournament from the user's list of favorites.
     *
     * @param competitionItem The [CompetitionItem] representing the tournament to be removed from favorites.
     */
    fun removeTournamentFromFavorites(competitionItem: CompetitionItem) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("TournamentFav")
            .whereEqualTo("userId", user.uid)
            .whereEqualTo("id", competitionItem.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("TournamentFav").document(document.id).delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "Joueur supprimé avec succès des favoris.")
                            _toastMessage.value = "Tournament deleted from favorites."
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Erreur lors de la suppression du joueur des favoris", e)
                            _toastMessage.value =
                                "Error when deleting tournament from favorites."
                        }
                }
                getFavorites()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erreur lors de la recherche du joueur dans les favoris", e)
            }
    }

    /**
     * Clears the current toast message.
     */
    fun clearToastMessage() {
        _toastMessage.value = ""
    }
}
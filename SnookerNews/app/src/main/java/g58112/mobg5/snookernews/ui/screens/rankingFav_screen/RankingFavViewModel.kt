package g58112.mobg5.snookernews.ui.screens.rankingFav_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import g58112.mobg5.snookernews.domaine.ranking.item.RankingItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel for the Ranking Favorites Screen.
 *
 * This ViewModel manages the operations related to fetching, displaying, and modifying the user's favorite player rankings.
 */
class RankingFavViewModel @Inject constructor() : ViewModel() {
    private val _favoriteRankings = MutableStateFlow<List<RankingItem>>(emptyList())
    val favoriteRankings: StateFlow<List<RankingItem>> = _favoriteRankings

    private val _toastMessage = mutableStateOf("")
    val toastMessage: State<String> = _toastMessage


    init {
        getFavorites()
    }

    /**
     * Fetches and updates the list of the user's favorite player rankings.
     *
     * This method retrieves the favorite player rankings from Firestore and updates the UI state accordingly.
     */
    private fun getFavorites() {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("PlayerFav")
            .whereEqualTo("userId", user.uid)
            .get()
            .addOnSuccessListener { documents ->
                _favoriteRankings.value = documents.map { doc ->
                    RankingItem(
                        id = doc.getString("id") ?: "",
                        name = doc.getString("name") ?: "",
                        abbreviation = doc.getString("abbreviation") ?: "",
                        rank = doc.getLong("rank")?.toInt() ?: 0,
                        prizeMoney = doc.getLong("prizeMoney")?.toInt() ?: 0
                    )
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erreur lors de la récupération des favoris", e)
            }
    }

    /**
     * Removes a player from the user's list of favorites.
     *
     * @param ranking The [RankingItem] representing the player to be removed from favorites.
     */
    fun removePlayerFromFavorites(ranking: RankingItem) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("PlayerFav")
            .whereEqualTo("userId", user.uid)
            .whereEqualTo("id", ranking.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("PlayerFav").document(document.id).delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "Joueur supprimé avec succès des favoris.")
                            _toastMessage.value = "Joueur supprimé des favoris."
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Erreur lors de la suppression du joueur des favoris", e)
                            _toastMessage.value =
                                "Erreur lors de la suppression du joueur des favoris."
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
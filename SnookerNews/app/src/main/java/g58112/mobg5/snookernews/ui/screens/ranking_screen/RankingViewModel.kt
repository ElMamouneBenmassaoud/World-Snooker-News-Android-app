package g58112.mobg5.snookernews.ui.screens.ranking_screen

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Ranking Screen.
 *
 * This ViewModel handles the operations related to fetching and displaying player rankings,
 * as well as managing favorite player selections.
 *
 * @property getRankUseCase The use case for fetching player rankings.
 */
@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getRankUseCase: GetRankUseCase
) : ViewModel() {
    private val _toastMessage = mutableStateOf("")
    val toastMessage: State<String> = _toastMessage

    private val _rankings = MutableStateFlow(emptyList<RankingItem>())
    val rankings: StateFlow<List<RankingItem>> get() = _rankings

    init {
        getRankings()
    }

    /**
     * Fetches and updates the list of player rankings.
     *
     * This method calls the getRankUseCase to retrieve player rankings and updates the UI state accordingly.
     */
    private fun getRankings() {
        viewModelScope.launch {
            try {
                val rankings = getRankUseCase()
                Log.d("ranking", "viewmodel: $rankings")

                _rankings.value = rankings
            } catch (e: Exception) {
                print(e.message)
            }
        }

    }

    /**
     * Adds a player to the user's list of favorites.
     *
     * @param ranking The [RankingItem] representing the player to be added to favorites.
     */
    fun addPlayerToFavorites(ranking: RankingItem) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        // Vérifie si le joueur est déjà un favori
        db.collection("PlayerFav")
            .whereEqualTo("userId", user.uid)
            .whereEqualTo("id", ranking.id)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // Le joueur n'est pas dans les favoris, procéder à l'ajout
                    addPlayerToFirestore(user.uid, ranking, db)
                    _toastMessage.value = "Joueur ajouté aux favoris."
                } else {
                    // Le joueur est déjà dans les favoris
                    Log.d(TAG, "Le joueur est déjà un favori.")
                    _toastMessage.value = "Le joueur est déjà dans les favoris."
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erreur lors de la vérification des favoris", e)
            }
    }

    /**
     * Adds a player's details to Firestore.
     *
     * @param userId The user ID of the player.
     * @param ranking The [RankingItem] details of the player.
     * @param db The Firestore instance.
     */
    private fun addPlayerToFirestore(userId: String, ranking: RankingItem, db: FirebaseFirestore) {
        val player = hashMapOf(
            "userId" to userId,
            "id" to ranking.id,
            "name" to ranking.name,
            "abbreviation" to ranking.abbreviation,
            "prizeMoney" to ranking.prizeMoney,
            "rank" to ranking.rank
        )
        db.collection("PlayerFav").add(player)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    /**
     * Filters and updates the list of player rankings based on the provided name.
     *
     * @param name The name to filter the rankings by.
     */
    fun getRankingsByName(name: String) {
        viewModelScope.launch {
            try {
                val filteredList = getRankUseCase().filter {
                    it.name.contains(name, ignoreCase = true)
                }
                _rankings.value = filteredList
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
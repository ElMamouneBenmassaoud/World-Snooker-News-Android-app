package g58112.mobg5.snookernews.ui.screens.ranking_screen

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
class RankingFavViewModel @Inject constructor() : ViewModel() {
    private val _favoriteRankings = MutableStateFlow<List<RankingItem>>(emptyList())
    val favoriteRankings: StateFlow<List<RankingItem>> = _favoriteRankings

    private val _toastMessage = mutableStateOf("")
    val toastMessage: State<String> = _toastMessage


    init {
        getFavorites()
    }

    private fun getFavorites() {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("PlayerFav")
            .whereEqualTo("userId", user.uid)
            .get()
            .addOnSuccessListener { documents ->
                _favoriteRankings.value = documents.map { doc ->
                    // Conversion de chaque document Firestore en RankingItem
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

    fun removePlayerFromFavorites(ranking: RankingItem) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        // Recherche du document correspondant à l'utilisateur et au RankingItem
        db.collection("PlayerFav")
            .whereEqualTo("userId", user.uid)
            .whereEqualTo("id", ranking.id) // Ici, "id" est l'ID du RankingItem
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Supprimer chaque document trouvé (devrait normalement être unique)
                    db.collection("PlayerFav").document(document.id).delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "Joueur supprimé avec succès des favoris.")
                            _toastMessage.value = "Joueur supprimé des favoris."
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Erreur lors de la suppression du joueur des favoris", e)
                            _toastMessage.value = "Erreur lors de la suppression du joueur des favoris."
                        }
                }
                getFavorites() // Mettre à jour la liste des favoris
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Erreur lors de la recherche du joueur dans les favoris", e)
            }
    }
    fun clearToastMessage() {
        _toastMessage.value = ""
    }
}
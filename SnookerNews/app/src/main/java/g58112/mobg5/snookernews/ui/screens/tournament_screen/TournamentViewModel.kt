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

    fun clearToastMessage() {
        _toastMessage.value = ""
    }
}
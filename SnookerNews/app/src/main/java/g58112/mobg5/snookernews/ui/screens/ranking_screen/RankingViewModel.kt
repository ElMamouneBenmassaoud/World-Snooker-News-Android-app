package g58112.mobg5.snookernews.ui.screens.ranking_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import g58112.mobg5.snookernews.domaine.GetRankUseCase
import g58112.mobg5.snookernews.domaine.item.RankingItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getRankUseCase: GetRankUseCase
) : ViewModel() {

    private val _rankings = MutableStateFlow(emptyList<RankingItem>())
    val rankings: StateFlow<List<RankingItem>> get() = _rankings

    init {
        getRankings()
    }

    private fun getRankings() {
        viewModelScope.launch {
            try {
                val rankings = getRankUseCase()
                _rankings.value = rankings
            } catch (e: Exception) {
                print(e.message)
            }
        }

    }
}
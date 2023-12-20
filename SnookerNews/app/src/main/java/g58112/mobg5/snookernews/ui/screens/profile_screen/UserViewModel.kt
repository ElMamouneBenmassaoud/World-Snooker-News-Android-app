package g58112.mobg5.snookernews.ui.screens.profile_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val firebaseUser: MutableLiveData<FirebaseUser?> = MutableLiveData()

    init {
        viewModelScope.launch {
            // Utilisation de withContext pour passer à un thread d'arrière-plan
            val user = withContext(Dispatchers.IO) {
                FirebaseAuth.getInstance().currentUser
            }
            // Définition de la valeur de l'utilisateur dans le thread principal
            firebaseUser.value = user
        }
    }
}

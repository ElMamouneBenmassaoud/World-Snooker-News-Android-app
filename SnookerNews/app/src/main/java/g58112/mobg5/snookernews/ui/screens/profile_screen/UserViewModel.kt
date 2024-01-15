package g58112.mobg5.snookernews.ui.screens.profile_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for managing user-related information in the profile screen.
 *
 * This ViewModel handles operations related to fetching and updating the current Firebase user's information.
 * It utilizes Firebase Authentication to access the user's details.
 */
class UserViewModel : ViewModel() {
    val firebaseUser: MutableLiveData<FirebaseUser?> = MutableLiveData()

    init {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                FirebaseAuth.getInstance().currentUser
            }
            firebaseUser.value = user
        }
    }
}

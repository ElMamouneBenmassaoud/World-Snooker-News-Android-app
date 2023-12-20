package g58112.mobg5.snookernews.ui.screens.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import g58112.mobg5.snookernews.R
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserProfile(
    profileImage: Painter,
    email: String,
    provider: String,
    creationDate: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Espacement en haut pour la barre de navigation si nécessaire
        Card(
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp), // Ajustez la taille et le padding selon vos besoins
            shape = CircleShape,
        ) {
            Image(
                painter = profileImage,
                contentDescription = "User Profile Picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacement après l'image

        // Informations de l'utilisateur dans des cards avec des ombres pour le relief
        UserInfoCard(label = "Email", value = email)
        UserInfoCard(label = "Provide", value = provider)
        UserInfoCard(label = "Created at", value = creationDate)

        Spacer(modifier = Modifier.weight(1f)) // Utilisez le poids pour pousser les boutons vers le bas

        // Boutons en bas
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text("Players")
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Tournois")
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacement au fond
    }
}

@Composable
fun UserInfoCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("$label:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(value)
        }
    }
}
@Composable
fun UserProfileScreen(userViewModel: UserViewModel = viewModel()) {
    val user = userViewModel.firebaseUser.observeAsState().value

    // Afficher les informations de l'utilisateur si disponibles
    user?.let {
        val providerId = it.providerData.joinToString(separator = ", ") { userInfo ->
            // Convertir le fournisseur ID en un format lisible
            when (userInfo.providerId) {
                "google.com" -> "Google"
                "password" -> "Email/Password"
                else -> userInfo.providerId // ou un traitement personnalisé pour d'autres fournisseurs
            }
        }

        val creationDate = SimpleDateFormat(
            "dd MMM. yyyy",
            Locale.getDefault()
        ).format(it.metadata?.creationTimestamp ?: 0L)

        val profileImagePainter = painterResource(id = R.drawable.profile_placeholder)

        UserProfile(
            profileImage = profileImagePainter,
            email = it.email ?: "Unavailable",
            provider = providerId,
            creationDate = creationDate,
        )
    } ?: run {
        Text("Aucune information d'utilisateur disponible.")
    }
}

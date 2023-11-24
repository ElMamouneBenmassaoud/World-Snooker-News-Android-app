package g58112.mobg5.snookernews.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import g58112.mobg5.snookernews.R
import g58112.mobg5.snookernews.network.AuthResponse

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userName: String,
    school: String,
    course: String,
    group: String,
    userEmail: String,
    authData: AuthResponse? = null,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImage()

            RetrieveData(
                name = userName,
                email = userEmail,
                course = course,
                group = group,
                school = school,
                authData = authData
            )
        }
    }
}


@Composable
private fun RetrieveData(
    name: String,
    email: String,
    course: String,
    group: String,
    school: String,
    authData: AuthResponse?
) {
    authData?.let {
        UserInfo(
            name = name,
            email = email,
            courseGroup = "$group - $course",
            createdAt = it.createdAt,
            expiresAt = "${it.expiresAt}",
            school = school
        )
    }
}


@Composable
private fun ProfileImage() {
    Image(
        painter = painterResource(id = R.drawable.profile_placeholder),
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
    )
}

@Composable
private fun UserInfo(
    name: String,
    email: String,
    courseGroup: String,
    createdAt: String,
    expiresAt: String,
    school: String
) {
    Spacer(modifier = Modifier.height(50.dp))
    Text(text = name, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = email, style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = courseGroup, style = MaterialTheme.typography.bodySmall)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = school, style = MaterialTheme.typography.bodySmall)
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Data retrieved from the authentication request:",
        style = MaterialTheme.typography.bodySmall
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = createdAt, style = MaterialTheme.typography.bodySmall)
    Text(text = expiresAt, style = MaterialTheme.typography.bodySmall)
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        userName = "El Mamoune Benmassaoud",
        school = "ESI",
        course = "Mobg5",
        group = "E11",
        userEmail = "58112@etu.he2b.be",
    )
}
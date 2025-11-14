package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.UserProfileDataApiResponse
import coil.compose.AsyncImage

@Composable
fun ItemUser(userProfileDataApiResponse: UserProfileDataApiResponse) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp),
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        Column {
            Row {
                Surface(
                    modifier = Modifier.size(120.dp),
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                ) {
                    AsyncImage(
                        model = userProfileDataApiResponse.avatarUrl,
                        contentDescription = "profile image",
                        contentScale = ContentScale.FillBounds,
                    )
                }
                Text(text = userProfileDataApiResponse.name)
                Text(text = userProfileDataApiResponse.email)
            }
        }
    }
}
// "name": "userPrueba",
// "avatar_url": "https://ui-avatars.com/api/?name=userPrueba",
// "email": "juanmanuelotero83@gmail.com"

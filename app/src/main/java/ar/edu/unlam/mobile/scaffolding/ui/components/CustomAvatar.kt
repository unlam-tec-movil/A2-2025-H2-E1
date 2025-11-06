package ar.edu.unlam.mobile.scaffolding.ui.components
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import coil.compose.SubcomposeAsyncImage

@Composable
fun CustomAvatar(
    imageSize: Int = 45,
    tuit: Tuit,
) {
    SubcomposeAsyncImage(
        modifier =
            Modifier
                .padding(top = 5.dp)
                .clip(CircleShape)
                .size(imageSize.dp),
        model = tuit.avatarUrl,
        contentDescription = stringResource(R.string.avatar_default_description),
        loading = { CustomLoadingState() },
    )
}

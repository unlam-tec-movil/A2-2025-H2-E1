package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.utils.Constants.DEFAULT_URL_IMAGE
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun CustomAvatar(
    imageSize: Int = 45,
    avatarUrl: String,
) {
    SubcomposeAsyncImage(
        modifier =
            Modifier
                .padding(top = 5.dp)
                .clip(CircleShape)
                .size(imageSize.dp),
        model = avatarUrl,
        contentDescription = stringResource(R.string.avatar_default_description),
//        loading = { CustomLoadingState() },
    ) {
        val asyncImageState = painter.state
        when (asyncImageState) {
            AsyncImagePainter.State.Empty, is AsyncImagePainter.State.Error -> {
                AsyncImage(
                    model = DEFAULT_URL_IMAGE,
                    contentDescription = "DEFAULT_URL_IMAGE",
                    modifier =
                        Modifier
                            .padding(top = 5.dp)
                            .clip(CircleShape)
                            .size(imageSize.dp),
                    contentScale = ContentScale.Crop,
                )
            }

            is AsyncImagePainter.State.Loading -> {
                CustomLoadingState()
            }

            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
        }
    }
}

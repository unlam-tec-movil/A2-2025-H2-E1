package ar.edu.unlam.mobile.scaffolding.ui.components.tuit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomAvatar
import ar.edu.unlam.mobile.scaffolding.ui.screens.CustomDivider

@Composable
fun TuitCard(
    tuit: Tuit,
    navigateToTuitScreen: () -> Unit,
    onFavoriteChanged: (Tuit) -> Unit,
) {
    Box(
        modifier =
            Modifier.clickable {
                navigateToTuitScreen()
            },
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(top = 5.dp),
        ) {
            CustomAvatar(tuit = tuit)
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 3.dp),
            ) {
                TopRow(tuit)
                MiddleRow(tuit)
                BottomRow(
                    tuit = tuit,
                    onClickLiked = {
                        onFavoriteChanged(tuit)
                    },
                    onClickReply = {
                        navigateToTuitScreen()
                    },
                )
            }
        }
        CustomDivider()
    }
}

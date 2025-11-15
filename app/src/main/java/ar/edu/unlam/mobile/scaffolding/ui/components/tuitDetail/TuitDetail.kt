package ar.edu.unlam.mobile.scaffolding.ui.components.tuitDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomAvatar
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomSubtitle
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.BottomRow
import ar.edu.unlam.mobile.scaffolding.ui.components.tuit.MiddleRow
import ar.edu.unlam.mobile.scaffolding.ui.screens.CustomDivider

@Composable
fun TuitDetail(
    tuit: Tuit,
    onLikeChanged: (Tuit) -> Unit,
    onclickReply: () -> Unit,
    replies: Int,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 5.dp),
    ) {
        CustomAvatar(avatarUrl = tuit.avatarUrl)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(top = 3.dp),
        ) {
            TopRowDetail(tuit)
        }
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
    ) {
        MiddleRow(tuit)
        CustomSubtitle(fontSize = 14, title = tuit.date)
        Spacer(Modifier.height(8.dp))
    }
    CustomDivider()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
    ) {
        Spacer(Modifier.height(5.dp))
        BottomRow(
            tuit = tuit,
            onClickLiked = {
                onLikeChanged(tuit)
            },
            onClickReply = {
                onclickReply()
            },
            isSaved = false,
//            onLikeClick = {
//
//            },
            onBookmarkClick = {},
            replies = replies,
        )
    }
    CustomDivider()
}

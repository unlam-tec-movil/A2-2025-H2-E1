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
import ar.edu.unlam.mobile.scaffolding.data.repositories.events.TuitAction
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomAvatar
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomDivider

@Composable
fun TuitCard(
    tuit: Tuit,
    isTuitSaved: Boolean,
    navigateToTuitScreen: () -> Unit,
    onLikeChanged: (Tuit) -> Unit,
    onBookmarkClick: (TuitAction) -> Unit,
//    onBookmarkClick: () -> Unit,
//    onBookmarkClick: (Boolean, Tuit) -> Unit,
    userIsSaved: Boolean,
    replies: Int,
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
            CustomAvatar(avatarUrl = tuit.avatarUrl)
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
                        onLikeChanged(tuit)
                    },
                    onClickReply = {
                        navigateToTuitScreen()
                    },
                    isUserSaved = userIsSaved,
                    onLikeClick = {},
                    onBookmarkClick = onBookmarkClick,
                    isTuitSaved = isTuitSaved,
                    isSaved = userIsSaved,
//                    onLikeClick = {},
                    onBookmarkClick = { onBookmarkClick() },
                    replies = replies,
                )
            }
        }
        CustomDivider()
    }
}

// BottomRow(
// //                                    tuit,
// //                                    onLikeClick = {
// //                                        if (tuit.liked) {
// //                                            feedViewModel.removeLikes(tuit)
// //                                        } else {
// //                                            feedViewModel.addLikes(tuit)
// //                                        }
// //                                    },
// //                                    onBookmarkClick = {
// //                                        feedViewModel.favoriteUsersManagment(isSaved, tuit)
// //                                    },
// //                                    isSaved = usersSavedMap.contains(tuit.authorId),
// //                                )
// }

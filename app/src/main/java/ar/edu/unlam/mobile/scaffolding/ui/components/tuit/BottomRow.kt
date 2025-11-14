package ar.edu.unlam.mobile.scaffolding.ui.components.tuit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomIcon
import ar.edu.unlam.mobile.scaffolding.ui.components.DefaultText

@Composable
fun BottomRow(
    tuit: Tuit,
    isSaved: Boolean,
    onLikeClick: (Tuit) -> Unit,
    onBookmarkClick: (Tuit) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 2.dp)
            .padding(bottom = 10.dp)
            .height(20.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var isMenuExpanded by remember { mutableStateOf(false) }
        CustomIcon(
            icon = Icons.Default.ChatBubbleOutline,
            tint = Color.Gray,
            modifier =
                Modifier
                    .size(17.dp)
                    .clickable {
                    },
        )
        CustomIcon(icon = Icons.Default.Repeat, modifier = Modifier.size(17.dp))
        Row(Modifier.width(70.dp), horizontalArrangement = Arrangement.Start) {
            IconButton(
                onClick = {
                    onLikeClick(tuit)
                },
                content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        when (tuit.liked) {
                            true -> {
                                CustomIcon(
                                    icon = Icons.Default.Favorite,
                                    tint = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier.size(17.dp),
                                )
                                ShowLikes(tuit.likes, MaterialTheme.colorScheme.onSecondary)
                            }

                            false -> {
                                CustomIcon(
                                    icon = Icons.Default.FavoriteBorder,
                                    modifier =
                                        Modifier
                                            .size(17.dp),
                                )
                                ShowLikes(tuit.likes)
                            }
                        }
                    }
                },
            )
        }
        Box {
            CustomIcon(
                icon = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                modifier =
                    Modifier
                        .size(17.dp)
                        .clickable {
                            isMenuExpanded = true
                        },
            )
            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
            ) {
                DropdownMenuItem(
                    text = {
                        if (isSaved) {
                            Text(text = "Delete from favorites")
                        } else {
                            Text(text = "Add to favorites")
                        }
                    },
                    onClick = {
                        onBookmarkClick(tuit)
                        isMenuExpanded = false
                    },
                )
            }
        }
        CustomIcon(
            icon = Icons.Default.Share,
            modifier =
                Modifier
                    .size(17.dp),
        )
    }
}

@Composable
fun ShowLikes(
    likes: Long,
    color: Color = MaterialTheme.colorScheme.secondary,
) {
    Spacer(Modifier.width(4.dp))
    DefaultText(fontSize = 13, title = "$likes", color = color)
}

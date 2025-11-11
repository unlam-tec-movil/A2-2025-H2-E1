package ar.edu.unlam.mobile.scaffolding.ui.components.tuitDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.model.Tuit
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomIcon
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomSubtitle
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomTitle

@Composable
fun TopRowDetail(tuit: Tuit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
            CustomTitle(title = tuit.author)
            Spacer(Modifier.width(4.dp))
            CustomSubtitle(title = "@${tuit.author}", color = MaterialTheme.colorScheme.secondary)
        }
        Spacer(Modifier.weight(1f))
        CustomIcon(modifier = Modifier.size(17.dp), icon = Icons.Default.MoreVert)
    }
}

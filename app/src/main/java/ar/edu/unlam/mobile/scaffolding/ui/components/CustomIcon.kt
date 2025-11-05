package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    tint: Color = MaterialTheme.colorScheme.secondary,
) {
    Icon(
        modifier = modifier,
        imageVector = icon,
        contentDescription = stringResource(R.string.avatar_default_description),
        tint = tint,
    )
}

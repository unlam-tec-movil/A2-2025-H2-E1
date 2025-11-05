package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun CustomTitle(
    fontSize: Int = 15,
    title: String,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Text(
        fontSize = fontSize.sp,
        color = color,
        text = title,
        fontWeight = FontWeight.ExtraBold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

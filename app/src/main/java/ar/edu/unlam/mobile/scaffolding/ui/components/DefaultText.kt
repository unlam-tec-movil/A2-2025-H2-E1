package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun DefaultText(
    fontSize: Int = 16,
    title: String,
    color: Color = MaterialTheme.colorScheme.secondary,
) {
    Text(
        text = title,
        color = color,
        fontSize = fontSize.sp,
    )
}

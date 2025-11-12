package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomErrorView(e: String) {
    Box(
        Modifier
            .size(560.dp)
            .padding(20.dp),
        Alignment.Center,
    ) {
        Column {
            Icon(
                modifier = Modifier.size(55.dp),
                imageVector = Icons.Default.WarningAmber,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )

            Text(
                modifier = Modifier,
                text = "Something Went Wrong",
                fontSize = 40.sp,
                overflow = TextOverflow.Visible,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                modifier = Modifier,
                text = e,
                overflow = TextOverflow.Visible,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview
@Composable
fun SeeErrorView() {
    CustomErrorView("EROR ROROROROE EORORIOE EORIBS EORNJSX EOROIEH sjdkshadjas ashjagsdaksdghsakd asdg")
}

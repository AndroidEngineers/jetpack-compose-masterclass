package com.androidengineers.masterly.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidengineers.masterly.ui.theme.ProgressFill
import com.androidengineers.masterly.ui.theme.ProgressHeight
import com.androidengineers.masterly.ui.theme.TrackColor

@Preview()
@Composable
fun ProgressBarPreview_Half() {
    ProgressBar(progress = 50)
}

@Preview()
@Composable
fun ProgressBarPreview_Empty() {
    ProgressBar(progress = 0)
}

@Preview()
@Composable
fun ProgressBarPreview_Full() {
    ProgressBar(progress = 100)
}

@Preview()
@Composable
fun ProgressBarPreview_Partial() {
    ProgressBar(progress = 75)
}

@Composable
fun ProgressBar(
    progress: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(ProgressHeight)
            .background(color = TrackColor, shape = RoundedCornerShape(50))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress / 100f)
                .background(
                    color = ProgressFill, shape = RoundedCornerShape(
                        topStart = 50.dp,
                        bottomStart = 50.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
        )
    }
}





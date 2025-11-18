package com.androidengineers.masterly.ui.components

import android.widget.ProgressBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidengineers.masterly.R
import com.androidengineers.masterly.ui.theme.LightCard
import com.androidengineers.masterly.ui.theme.SkillCardHeight

@Preview()
@Composable
fun SkillCardPreview() {
    SkillCard(
        name = "Android Development",
        hoursLogged = 50,
        totalHours = 100
    )
}

@Preview()
@Composable
fun SkillCardPreview_LongName() {
    SkillCard(
        name = "Advanced Jetpack Compose UI Design and Animation",
        hoursLogged = 75,
        totalHours = 100
    )
}

@Preview()
@Composable
fun SkillCardPreview_ZeroProgress() {
    SkillCard(
        name = "Kotlin Coroutines",
        hoursLogged = 0,
        totalHours = 50
    )
}

@Preview()
@Composable
fun SkillCardPreview_FullProgress() {
    SkillCard(
        name = "Public Speaking",
        hoursLogged = 20,
        totalHours = 20
    )
}

@Composable
fun CardContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1A1A1F),
        shadowElevation = 8.dp,
        content = content
    )
}

@Composable
fun SkillCard(
    name: String,
    hoursLogged: Int,
    totalHours: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val percent = (hoursLogged.toFloat() / totalHours * 100).coerceAtMost(100f).toInt()
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    CardContainer(
        modifier = modifier
            .fillMaxWidth()
            .height(SkillCardHeight)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = LightCard,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Image(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = "Arrow Right",
                        modifier = modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.clock),
                        contentDescription = "Clock",
                        modifier = modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${hoursLogged.formatWithCommas()} / ${totalHours.formatWithCommas()} hours",
                        style = typography.bodySmall,
                        color = colorScheme.onSurfaceVariant
                    )
                }
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Progress",
                        style = typography.labelSmall,
                        color = colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$percent%",
                        style = typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ProgressBar(
                    progress = percent,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

fun Int.formatWithCommas(): String {
    return this.toString().reversed().chunked(3).joinToString(",").reversed()
}

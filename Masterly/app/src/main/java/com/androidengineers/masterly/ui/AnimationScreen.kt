package com.androidengineers.masterly.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.androidengineers.masterly.ui.theme.Indicator
import com.androidengineers.masterly.ui.theme.Purple500
import com.androidengineers.masterly.ui.theme.Teal200
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class AnimationDisplayListState(
    val isAnimateColorItem: Boolean = false,
    val isAnimateFloatItem: Boolean = false,
    val isAnimateDpItem: Boolean = false,
    val isAnimateIntItem: Boolean = true,
    val isAnimateRectItem: Boolean = false,
    val isAnimateSizeItem: Boolean = false
)

@Composable
fun AnimationDisplayScreen(modifier: Modifier = Modifier) {
    Box {
        AnimationDisplayList(modifier)
    }
}

@Composable
fun AnimationDisplayList(modifier: Modifier = Modifier) {
    LazyColumn(modifier=modifier,
        content = {
            item { SectionHeader("Basics — animate*AsState") }
            item {
                AnimateMultiple(modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            }
            item {
                AnimateDp(modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            }
            item {
                AnimateScale(modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            }
            item {
                AnimateRectangle(modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            }
            item {
                AnimateSize(modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            }
            item {
                AnimateColor(modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            }
            item {
                AnimateInt(modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            }
            item {
                AnimateColorWithSnap(modifier.padding(horizontal = 8.dp, vertical = 8.dp))
            }
            item { SectionHeader("Visibility & Content") }
            item { AnimatedVisibilityExample() }
            item { AnimatedContentCounterExample() }
            item { CrossfadeTabsExample() }

            item { SectionHeader("Infinite & Keyframe Animations") }
            item { InfinitePulseExample() }
            item { KeyframesBounceExample() }

            item { SectionHeader("Gestures + Animations") }
            item { DraggableCardWithSpringBackExample() }
            item { SwipeToRevealDeleteExample() }

            item { SectionHeader("Animatable & Sequence") }
            item { SequenceSplashAnimationExample() }

            item { SectionHeader("Canvas Animation") }
            item { LoadingArcCanvasExample() }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        })
}

@Composable
private fun SectionHeader(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Teal200
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            thickness = 1.dp,
            color = Teal200
        )
    }
}

@Composable
private fun AnimateMultiple(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(AnimationDisplayListState()) }

    val color by animateColorAsState(
        targetValue = if (state.isAnimateColorItem) Purple500 else Indicator,
    )
    val scale by animateFloatAsState(
        targetValue = if (state.isAnimateFloatItem) 1f else 2f,
    )
    val offset by animateDpAsState(
        targetValue = if (state.isAnimateDpItem) 130.dp else 10.dp,
        tween(1000)
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =color
        )
    ) {
        Column {
            Text(
                modifier = modifier
                    .scale(scale)
                    .absoluteOffset(x = offset)
                    .padding(top = 8.dp, start = 8.dp),
                text = "Akshay",
                style = TextStyle(
                    fontFamily = if (state.isAnimateFloatItem) FontFamily.Cursive else FontFamily.Monospace,
                ),
                fontWeight = FontWeight.Bold,
                color = Teal200
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal200),
                onClick = {
                    state = state.copy(
                        isAnimateColorItem = !state.isAnimateColorItem,
                        isAnimateFloatItem = !state.isAnimateFloatItem,
                        isAnimateDpItem = !state.isAnimateDpItem
                    )
                }) {
                Text(text = "Animate using Color/Dp/Float Value")
            }
        }
    }
}

@Composable
private fun AnimateColor(modifier: Modifier = Modifier) {

    var state by remember { mutableStateOf(AnimationDisplayListState()) }

    val color by animateColorAsState(
        targetValue = if (state.isAnimateColorItem) Purple500 else Indicator,
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =color
        )
    ) {
        Column {
            Text(
                modifier = modifier
                    .padding(top = 8.dp, start = 8.dp),
                text = "animateColorAsState \nAnimationSpec = spring",
                fontWeight = FontWeight.Bold,
                color = Teal200
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal200),
                onClick = { state = state.copy(isAnimateColorItem = !state.isAnimateColorItem) }) {
                Text(text = "Animate using Color Value")
            }
        }
    }
}

@Composable
private fun AnimateColorWithSnap(modifier: Modifier = Modifier) {

    var state by remember { mutableStateOf(AnimationDisplayListState()) }
    val color by animateColorAsState(
        targetValue = if (state.isAnimateColorItem) Purple500 else Indicator,
        animationSpec = spring(500f)
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =color
        )
    ) {
        Column {
            Text(
                modifier = modifier
                    .padding(top = 8.dp, start = 8.dp),
                text = "animateColorAsState \nAnimationSpec = snap(500)",
                fontWeight = FontWeight.Bold,
                color = Teal200
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal200),
                onClick = { state = state.copy(isAnimateColorItem = !state.isAnimateColorItem) }) {
                Text(text = "Animate using Color Value")
            }
        }
    }
}

@Composable
private fun AnimateScale(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(AnimationDisplayListState()) }

    val scale by animateFloatAsState(
        targetValue = if (state.isAnimateFloatItem) 1f else 2f,
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =Purple500
        )

    ) {
        Column {
            Text(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, start = 8.dp)
                    .scale(scale),
                text = "animateFloatAsState",
                fontWeight = FontWeight.Bold,
                color = Teal200
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal200),
                onClick = { state = state.copy(isAnimateFloatItem = !state.isAnimateFloatItem) }) {
                Text(text = "Animate using Float Value")
            }
        }
    }

}

@Composable
private fun AnimateDp(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(AnimationDisplayListState()) }

    val offset by animateDpAsState(
        targetValue = if (state.isAnimateDpItem) 120.dp else 0.dp,
        tween(200)
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =Purple500
        )
    ) {
        Column {
            Text(
                modifier = modifier
                    .absoluteOffset(x = offset)
                    .padding(top = 8.dp, start = 8.dp),
                text = "animateDpAsState \nAnimation Spec = tween(1000)",
                fontWeight = FontWeight.Bold,
                color = Teal200
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal200),
                onClick = { state = state.copy(isAnimateDpItem = !state.isAnimateDpItem) }) {
                Text(text = "Animate using Density Pixel Value")
            }
        }
    }

}

@Composable
private fun AnimateInt(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(AnimationDisplayListState()) }

    val number by animateIntAsState(
        targetValue = if (state.isAnimateIntItem) 0 else 20
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =Purple500
        )
    ) {
        Column {
            Text(
                modifier = Modifier
                    .absoluteOffset(y= number.dp)
                    .padding(top = 0.dp, start = 8.dp),
                text = "animateIntAsState",
                fontWeight = FontWeight.Bold,
                color = Teal200
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal200),
                onClick = { state = state.copy(isAnimateIntItem = !state.isAnimateIntItem) }) {
                Text(text = "Animate using Int Value")
            }
        }
    }

}

@Composable
private fun AnimateRectangle(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(AnimationDisplayListState()) }

    val rectangle by animateRectAsState(
        targetValue = if (state.isAnimateRectItem)
            Rect(210f, 10f, 300f, 80f) else
            Rect(10f, 10f, 130f, 80f),
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =Purple500
        )
    ) {
        Column {
            Text(
                modifier = modifier
                    .padding(top = 8.dp, start = 8.dp),
                text = "animateRectAsState",
                fontWeight = FontWeight.Bold,
                color = Teal200
            )
            Box(
                modifier = modifier
                    .offset(rectangle.left.dp, rectangle.top.dp)
                    .size(rectangle.width.dp, rectangle.height.dp)
                    .background(Indicator)
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal200),
                onClick = { state = state.copy(isAnimateRectItem = !state.isAnimateRectItem) }) {
                Text(text = "Animate using Rectangle Value")
            }
        }
    }

}

@Composable
private fun AnimateSize(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(AnimationDisplayListState()) }

    val rectangle by animateSizeAsState(
        targetValue = if (state.isAnimateSizeItem) Size(200f, 50f) else Size(100f, 10f),
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor =Purple500
        )
    ) {
        Column {
            Text(
                modifier = modifier
                    .padding(top = 8.dp, start = 8.dp),
                text = "animateSizeAsState",
                fontWeight = FontWeight.Bold,
                color = Teal200
            )
            Box(
                modifier = modifier
                    .size(rectangle.width.dp, rectangle.height.dp)
                    .background(Indicator)
            )
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal200),
                onClick = { state = state.copy(isAnimateSizeItem = !state.isAnimateSizeItem) }) {
                Text(text = "Animate using Size Value")
            }
        }
    }

}


@Composable
fun AnimatedVisibilityExample(modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "AnimatedVisibility",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Hide card" else "Show card")
        }

        Spacer(Modifier.height(12.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "I fade + expand in / out ✨",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

// 2) AnimatedContentCounterExample
@Composable
fun AnimatedContentCounterExample(modifier: Modifier = Modifier) {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "AnimatedContent (counter)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(Modifier.height(12.dp))

        AnimatedContent(
            targetState = count,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { it } + fadeIn() togetherWith
                            slideOutVertically { -it } + fadeOut()
                } else {
                    slideInVertically { -it } + fadeIn() togetherWith
                            slideOutVertically { it } + fadeOut()
                }.using(SizeTransform(clip = false))
            },
            label = "counter"
        ) { value ->
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }

        Spacer(Modifier.height(12.dp))

        Row {
            Button(onClick = { count-- }) {
                Text("-1",  color = Color.White)
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { count++ }) {
                Text("+1",  color = Color.White)
            }
        }
    }
}

// 3) CrossfadeTabsExample
@Composable
fun CrossfadeTabsExample(modifier: Modifier = Modifier) {
    val tabs = listOf("Home", "Stats", "Profile")
    var selectedTab by remember { mutableStateOf(tabs.first()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Crossfade (tabs)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Spacer(Modifier.height(8.dp))

        TabRow(selectedTabIndex = tabs.indexOf(selectedTab)) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == title,
                    onClick = { selectedTab = title },
                    text = { Text(title,  color = Color.Black) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Crossfade(
            targetState = selectedTab,
            label = "tabs-crossfade"
        ) { tab ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Current tab: $tab",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

// 4) InfinitePulseExample
@Composable
fun InfinitePulseExample(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 2.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse-scale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse-alpha"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Infinite pulse (rememberInfiniteTransition)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(80.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
                .background(MaterialTheme.colorScheme.tertiary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "LIVE",
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

// 5) KeyframesBounceExample
@Composable
fun KeyframesBounceExample(modifier: Modifier = Modifier) {
    val offsetY = remember { Animatable(0f) }
    var trigger by remember { mutableStateOf(0) }

    // Re-run animation whenever trigger changes
    LaunchedEffect(trigger) {
        offsetY.snapTo(0f)
        offsetY.animateTo(
            targetValue = 0f,
            animationSpec = keyframes {
                durationMillis = 600
                0f at 0
                -60f at 150     // up
                0f at 300       // down
                -30f at 450     // smaller up
                0f at 600       // settle
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Keyframes bounce",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .offset { IntOffset(0, offsetY.value.roundToInt()) }
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Tap to bounce",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                // just change trigger, LaunchedEffect handles the animation
                trigger++
            }
        ) {
            Text("Bounce again")
        }
    }
}

// 6) DraggableCardWithSpringBackExample
@Composable
fun DraggableCardWithSpringBackExample(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Draggable card (spring back)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Teal200
        )
        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = offset.value.x.roundToInt(),
                        y = offset.value.y.roundToInt()
                    )
                }
                .size(width = 220.dp, height = 120.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                offset.snapTo(
                                    Offset(
                                        offset.value.x + dragAmount.x,
                                        offset.value.y + dragAmount.y
                                    )
                                )
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                offset.animateTo(
                                    Offset.Zero,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Drag me around",
                color = Teal200
            )
        }
    }
}

// 7) SwipeToRevealDeleteExample
@Composable
fun SwipeToRevealDeleteExample(
    modifier: Modifier = Modifier,
    itemText: String = "Swipe to reveal delete"
) {
    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val maxOffsetPx = with(LocalDensity.current) { 120.dp.toPx() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(72.dp)
    ) {
        // Background "delete" row
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFB00020)),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                "Delete",
                color = Color.White,
                modifier = Modifier.padding(end = 24.dp),
                fontWeight = FontWeight.SemiBold,
            )
        }

        // Foreground content that can be swiped left
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset {
                    IntOffset(offsetX.value.roundToInt(), 0)
                }
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            scope.launch {
                                val shouldOpen =
                                    offsetX.value < -maxOffsetPx * 0.5f
                                val target =
                                    if (shouldOpen) -maxOffsetPx else 0f
                                offsetX.animateTo(
                                    target,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            val newX = (offsetX.value + dragAmount)
                                .coerceIn(-maxOffsetPx, 0f)
                            scope.launch {
                                offsetX.snapTo(newX)
                            }
                        }
                    )
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = itemText,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}

// 8) SequenceSplashAnimationExample
@Composable
fun SequenceSplashAnimationExample(modifier: Modifier = Modifier) {
    val circles = List(5) { remember { Animatable(0f) } }

    LaunchedEffect(Unit) {
        while (true) {
            circles.forEach { anim ->
                anim.snapTo(0f)
            }
            circles.forEach { anim ->
                anim.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(350, easing = FastOutSlowInEasing)
                )
                anim.animateTo(
                    targetValue = 0.3f,
                    animationSpec = tween(250)
                )
                anim.stop()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Sequence splash (sequential dots)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            circles.forEach { anim ->
                val scale = anim.value
                val alpha = 0.3f + (0.7f * scale)

                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .graphicsLayer {
                            scaleX = 0.7f + 0.3f * scale
                            scaleY = 0.7f + 0.3f * scale
                            this.alpha = alpha
                        }
                        .background(Teal200, CircleShape)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            "Splash-style loading sequence",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

// 9) LoadingArcCanvasExample
@Composable
fun LoadingArcCanvasExample(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "arc")
    val startAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "arc-rotation"
    )

    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 30f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "arc-sweep"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Loading arc (Canvas)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        Spacer(Modifier.height(16.dp))

        val strokeWidth = 6.dp

        Canvas(
            modifier = Modifier
                .size(64.dp)
        ) {
            val strokePx = strokeWidth.toPx()
            val diameter = size.minDimension - strokePx
            val topLeft = Offset(
                (size.width - diameter) / 2f,
                (size.height - diameter) / 2f
            )

            drawArc(
                color = Purple500,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }
    }
}
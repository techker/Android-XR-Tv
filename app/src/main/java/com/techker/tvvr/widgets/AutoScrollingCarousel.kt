package com.techker.tvvr.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.techker.tvvr.data.Movies
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * By replacing HorizontalUncontainedCarousel with AutoScrollingCarousel:
 * 	•	Gained full control over the layout and auto-scroll behavior.
 * 	•	Simplified debugging and integration with  existing Compose code.
 * 	•	Avoided dependency on potentially incomplete or restrictive APIs.
 * The original HorizontalUncontainedCarousel was likely a custom implementation or part of a library,
 * but its integration with the CarouselState caused type mismatches (e.g., LazyListState vs CarouselState).
 * The CarouselState API doesn't include methods like animateScrollToItem or activeItemIndex in some cases, causing usability issues.
 */
@Composable
fun AutoScrollingCarousel(
    items: List<Movies>,
    modifier: Modifier = Modifier,
    itemWidth: Dp = LocalConfiguration.current.screenWidthDp.dp - 32.dp,
    itemSpacing: Dp = 16.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    onItemClick: (Movies) -> Unit,
    itemContent: @Composable (Movies) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Track whether the carousel is active
    var isActive by remember { mutableStateOf(true) }
    // Observe lifecycle to manage activity
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            isActive = event == Lifecycle.Event.ON_RESUME
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    // Auto-scroll logic that restarts when `isActive` changes
    LaunchedEffect(isActive) {
        if (isActive && items.size > 1) {
            while (true) {
                delay(3000) // Auto-scroll every 3 seconds
                val nextIndex = (listState.firstVisibleItemIndex + 1) % items.size
                coroutineScope.launch {
                    listState.animateScrollToItem(nextIndex)
                }
            }
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        contentPadding = contentPadding
    ) {
        items(items.size) { index ->
            Box(
                modifier = Modifier
                    .width(itemWidth) // Make each item full-width
                    .fillMaxHeight()
            ) {
                itemContent(items[index])
            }
        }
    }
}
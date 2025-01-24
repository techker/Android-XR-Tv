package com.techker.tvvr.screens.epg

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.techker.tvvr.R
import com.techker.tvvr.data.MockData.channels
import com.techker.tvvr.data.TimeSlot
import com.techker.tvvr.screens.navigation.NavigationTopBar
import java.time.LocalTime

@SuppressLint("DefaultLocale")
@Composable
fun EPGScreen(navController: NavController) {
    // Get current hour
    val currentHour = remember {
        LocalTime.now().hour
    }
    val currentMinute = remember {
        LocalTime.now().minute
    }
    val currentTimeFloat = remember {
        currentHour + (currentMinute / 60f)
    }
    val clickedPrograms = remember { mutableStateOf<Set<String>>(setOf()) }

    val channels = remember {
        channels
    }

    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    // Calculate initial scroll position (current time)
    LaunchedEffect(Unit) {
        horizontalScrollState.scrollTo((currentHour * 100 * 2).toInt()) // 100dp per half hour
    }

    // Calculate the last program end time
    val lastProgramEndHour = remember(channels) {
        channels.maxOf { channel ->
            channel.programs.maxOf { program ->
                program.startTime + program.duration
            }
        }
    }

    // Modify time slots to show half hours
    val timeSlots = remember(lastProgramEndHour) {
        (0..(lastProgramEndHour * 2).toInt()).map { halfHour ->
            val hour = halfHour / 2f
            TimeSlot(
                time = String.format("%02d:%02d", hour.toInt(), (hour % 1 * 60).toInt()),
                startHour = hour
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        // Navigation Bar at the top
        NavigationTopBar(
            navController = navController,
            onAvatarClick = {
                navController.navigate("profile")
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
                .background(Color.Black)
        ) {
            // Fixed channel column with vertical scroll
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
            ) {
                // Fixed header space
                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .background(Color.Black)
                )

                // Scrollable channel names
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp)  // Same as header space
                        .verticalScroll(verticalScrollState)  // Use same scroll state as program grid
                        .background(Color.Black)
                        .border(
                            width = 1.dp,
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(0.dp)
                        )
                ) {
                    channels.forEach { channel ->
                        Box(
                            modifier = Modifier
                                .height(80.dp)
                                .fillMaxWidth()
                                .border(1.dp, Color.DarkGray),
                            contentAlignment = Alignment.Center
                        ) {
                            if(channel.logo.isNotEmpty()){
                                AsyncImage(
                                    model = channel.logo,
                                    contentDescription = "image",
                                    contentScale = ContentScale.Inside,
                                    modifier = Modifier.width(60.dp)
                                )
                            }else{
                                Text(
                                    text = channel.name,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Content area with fixed time header
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 100.dp) // Offset for channel column
            ) {
                // Fixed time header
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .horizontalScroll(horizontalScrollState)
                        .background(Color.Black)
                ) {
                    timeSlots.forEach { slot ->
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .fillMaxHeight()
                                .background(Color.Black)
                                .border(
                                    width = 1.dp,
                                    color = Color.DarkGray,
                                    shape = RoundedCornerShape(bottomStart = 2.dp, bottomEnd = 2.dp)
                                )
                        ) {
                            // Time text at the top
                            Text(
                                text = slot.time,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.TopCenter).padding(top = 4.dp)
                            )

                            // Current time indicator at the bottom
                            if (currentTimeFloat >= slot.startHour && currentTimeFloat < slot.startHour + 0.5f) {
                                val offsetPercentage = (currentMinute % 30) / 30f
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 2.dp)
                                        .offset(x = (offsetPercentage * 100).dp)
                                ) {
                                    // Vertical line
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(12.dp)
                                            .background(MaterialTheme.colorScheme.primary)
                                    )
                                    // Arrow
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropUp,
                                        contentDescription = "Current time",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .offset(y = (-6).dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // vertical line that spans the entire EPG
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                        .offset(x = (((currentMinute % 30) / 30f) * 100).dp)
                )

                // Scrollable program grid
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp) // Offset for time header
                ) {
                    val listState = rememberLazyListState()
                    LazyRow(
                        modifier = Modifier
                            .fillMaxSize()  //  fillMaxSize instead of fixed width
                            .scrollable(
                                orientation = Orientation.Horizontal,
                                state = rememberScrollableState { delta ->
                                    delta * 0.5f
                                }
                            ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        state = listState
                    ) {
                        items(
                            items = channels,
                            key = { it.id },
                            itemContent = { channel ->
                                Column(
                                    modifier = Modifier
                                        .height(80.dp)
                                ) {
                                    channel.programs.forEach { program ->
                                        val programEndTime = program.startTime + program.duration
                                        val isPastProgram = programEndTime < currentHour
                                        val isCurrentProgram =
                                            program.startTime <= currentHour && programEndTime > currentHour
                                        var isFocused by remember { mutableStateOf(false) }
                                        var isClicked by remember { mutableStateOf(false) }
                                        Box(
                                            modifier = Modifier
                                                .width(100.dp * (program.duration * 2))
                                                .fillMaxHeight()
                                                .padding(1.dp)
                                                .onFocusChanged {
                                                    isFocused = it.isFocused
                                                    if (!it.isFocused) {
                                                        isClicked = false
                                                    }
                                                }
                                                .border(
                                                    width = 1.dp,
                                                    color = if (isFocused) Color.White else Color.Transparent
                                                )
                                                .focusable()
                                                .background(
                                                    when {
                                                        program.title == "No Program" -> Color.DarkGray.copy(
                                                            alpha = 0.3f
                                                        )

                                                        isPastProgram -> Color.DarkGray.copy(alpha = 0.7f)
                                                        isCurrentProgram -> MaterialTheme.colorScheme.secondary
                                                        else -> MaterialTheme.colorScheme.primary
                                                    },
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .clickable {
                                                    isClicked = true
                                                    val programId =
                                                        "${channel.id}_${program.title}_${program.startTime}"
                                                    if (clickedPrograms.value.contains(programId)) {
                                                        // Second click - navigate to player
                                                        navController.navigate("player/${channel.id}/${program.startTime}")
                                                    } else {
                                                        // First click - show details
                                                        clickedPrograms.value += programId
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(4.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = if (isClicked) Arrangement.Start else Arrangement.SpaceBetween
                                            ) {
                                                if (isClicked) {
                                                    Image(
                                                        painterResource(R.drawable.default_card),
                                                        contentDescription = "",
                                                        contentScale = ContentScale.Fit,
                                                        modifier = Modifier
                                                            .width(60.dp)
                                                            .fillMaxHeight()
                                                    )

                                                    Spacer(modifier = Modifier.width(8.dp))

                                                    Text(
                                                        text = "Program Short Description",
                                                        color = Color.White,
                                                        textAlign = TextAlign.Start,
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 1,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                } else {
                                                    // Program title and time
                                                    Text(
                                                        text = if (program.title == "No Program") {
                                                            "No Program"
                                                        } else {
                                                            "${program.title} (${
                                                                String.format(
                                                                    "%02d:%02d",
                                                                    program.startTime.toInt(),
                                                                    (program.startTime % 1 * 60).toInt()
                                                                )
                                                            }-${
                                                                String.format(
                                                                    "%02d:%02d",
                                                                    (program.startTime + program.duration).toInt(),
                                                                    ((program.startTime + program.duration) % 1 * 60).toInt()
                                                                )
                                                            })"
                                                        },
                                                        color = when {
                                                            program.title == "No Program" -> Color.Gray.copy(
                                                                alpha = 0.7f
                                                            )

                                                            isPastProgram -> Color.Gray
                                                            else -> Color.White
                                                        },
                                                        textAlign = TextAlign.Start,
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 1,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(end = if (program.isRecording || isPastProgram) 8.dp else 0.dp)
                                                    )

                                                    // Icons in a separate Row
                                                    if (program.title != "No Program") {
                                                        Row(
                                                            horizontalArrangement = Arrangement.End,
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            modifier = Modifier.padding(start = 4.dp)
                                                        ) {
                                                            if (isPastProgram) {
                                                                Icon(
                                                                    imageVector = Icons.Filled.Refresh,
                                                                    contentDescription = "Restart program",
                                                                    modifier = Modifier.size(20.dp),
                                                                    tint = Color.White
                                                                )
                                                            }

                                                            if (program.isRecording) {
                                                                if (isPastProgram) {
                                                                    Spacer(modifier = Modifier.width(4.dp))
                                                                }
                                                                Icon(
                                                                    imageVector = Icons.Filled.FiberManualRecord,
                                                                    contentDescription = "Recording",
                                                                    modifier = Modifier.size(20.dp),
                                                                    tint = Color.Red.copy(alpha = 0.9f)
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
            }
        }
    }


@Preview(
    showBackground = true,
    widthDp = 1280,
    heightDp = 720
)
@Composable
fun EPGScreenPreview() {
    val navController = rememberNavController() // Create a mock NavController
    EPGScreen(navController)
} 
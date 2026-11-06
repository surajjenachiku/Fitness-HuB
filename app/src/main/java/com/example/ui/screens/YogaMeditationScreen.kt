package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.FitnessCatalog
import com.example.ui.FitnessViewModel
import com.example.ui.components.GoldButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.DarkDivider
import com.example.ui.theme.InfoBlue
import com.example.ui.theme.LuxuryBlack
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextLightGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun YogaMeditationScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Yoga Poses", "Breathing Gym", "Meditation")

    val breathingPhase by viewModel.breathingPhase.collectAsState()
    val breathingCount by viewModel.breathingCount.collectAsState()
    val isBreathingRunning by viewModel.isBreathingRunning.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LuxuryBlack)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.closeSubScreen() },
                modifier = Modifier.testTag("yoga_back_btn")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BrightGold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "YOGA & MINDFULNESS",
                    color = BrightGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Pranayama, asanas, recovery & mental fortitude",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = DarkCard,
            contentColor = BrightGold,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = BrightGold
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp,
                            color = if (selectedTab == index) BrightGold else TextMuted
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> YogaPosesContent()
            1 -> BreathingGymContent(
                phase = breathingPhase,
                count = breathingCount,
                isRunning = isBreathingRunning,
                onToggle = { viewModel.toggleBreathingSession() }
            )
            2 -> MeditationContent()
        }
    }
}

@Composable
private fun YogaPosesContent() {
    val poses = FitnessCatalog.yogaPoses

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            LuxurySectionHeader(
                title = "Ancient Indian Asanas",
                subtitle = "Kinetic alignment, spinal decompression & core mobility"
            )
        }

        items(poses) { pose ->
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(listOf(InfoBlue.copy(alpha = 0.4f), DarkDivider)),
                backgroundColor = DarkCardElevated
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(pose.englishName, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(pose.sanskritName, color = TextLightGray, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(InfoBlue.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(pose.difficulty, color = InfoBlue, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Benefits: ${pose.benefits}", color = TextLightGray, fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Step-by-Step Flow:", color = BrightGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    pose.steps.forEachIndexed { i, step ->
                        Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.Top) {
                            Text("${i + 1}.", color = BrightGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(step, color = TextWhite, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BreathingGymContent(
    phase: String,
    count: Int,
    isRunning: Boolean,
    onToggle: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Box Breathing (Sama Vritti 4-4-4-4)", color = BrightGold, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Calms autonomic nervous system & drops cortisol post-workout", color = TextMuted, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(36.dp))

        Box(
            modifier = Modifier
                .size(220.dp)
                .scale(if (isRunning) pulseScale else 1f)
                .clip(CircleShape)
                .background(Brush.radialGradient(listOf(InfoBlue.copy(alpha = 0.35f), DarkCardElevated)))
                .border(3.dp, if (isRunning) BrightGold else DarkDivider, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isRunning) "$count" else "4",
                    color = BrightGold,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = if (isRunning) phase else "Tap to Begin",
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        GoldButton(
            text = if (isRunning) "Pause Breathing Session" else "Begin Box Breathing",
            icon = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
            onClick = onToggle,
            modifier = Modifier.fillMaxWidth(0.8f),
            testTag = "toggle_breathing_btn"
        )
    }
}

@Composable
private fun MeditationContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            LuxurySectionHeader(
                title = "Guided Meditation & Neural Recovery",
                subtitle = "Mindset protocols practiced by elite athletes"
            )
        }

        item {
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Column {
                    Text("1. Pre-Workout Visualization (5 Mins)", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Sit upright in a quiet area. Close eyes and mentally walk through every repetition of your heavy compound lifts with perfect kinetic form. Primes motor unit recruitment and neuromuscular firing.",
                        color = TextLightGray,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        item {
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Column {
                    Text("2. Post-Workout Parasympathetic Reset (10 Mins)", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Lie flat on your back (Shavasana). Practice diaphragmatic nasal breathing. Focus mental attention sequentially on toes, calves, quadriceps, chest, and jaw, consciously releasing all muscular tension.",
                        color = TextLightGray,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

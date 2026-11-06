package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WorkoutProgram
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
import com.example.ui.theme.LuxuryRed
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.TextLightGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun WorkoutScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Home / Beginner", "Fat Loss", "Strength Training", "No-Equipment", "10-Min Fast Plan", "20-Min Plan", "30-Min Plan")
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredPrograms = remember(selectedCategory) {
        if (selectedCategory == "All") FitnessCatalog.workoutPrograms
        else FitnessCatalog.workoutPrograms.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LuxuryBlack)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "WORKOUT PROGRAMS",
                        color = BrightGold,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Scientifically periodized training protocols",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Yoga & Meditation Banner
        item {
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(listOf(InfoBlue, MetallicGold)),
                backgroundColor = DarkCardElevated,
                onClick = { viewModel.navigateToSubScreen("yoga_meditation") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(InfoBlue.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = null,
                            tint = InfoBlue,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Yoga, Meditation & Breathing", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("Surya Namaskar, Box Breathing (4-4-4-4) & Pranayama", color = TextMuted, fontSize = 11.sp)
                    }
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = BrightGold
                    )
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) BrightGold else DarkCard)
                            .border(1.dp, if (isSelected) BrightGold else DarkDivider, RoundedCornerShape(20.dp))
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("workout_cat_${cat.replace(" ", "_")}")
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) LuxuryBlack else TextWhite,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Workout Program Cards
        items(filteredPrograms, key = { it.id }) { program ->
            WorkoutProgramCard(
                program = program,
                onStart = { viewModel.startWorkout(program) }
            )
        }
    }
}

@Composable
fun WorkoutProgramCard(
    program: WorkoutProgram,
    onStart: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    LuxuryCard(
        borderBrush = Brush.horizontalGradient(listOf(MetallicGold.copy(alpha = 0.4f), DarkDivider)),
        backgroundColor = DarkCardElevated,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = program.title,
                        color = BrightGold,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = program.subtitle,
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(LuxuryRed.copy(alpha = 0.2f))
                        .border(1.dp, LuxuryRed, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = program.difficulty,
                        color = LuxuryRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Timer, contentDescription = null, tint = BrightGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${program.durationMin} Mins", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = LuxuryRed, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${program.calories} kcal", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = MetallicGold, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${program.exercises.size} Exercises", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            // Exercise items preview
            Spacer(modifier = Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                val displayList = if (expanded) program.exercises else program.exercises.take(2)
                displayList.forEachIndexed { idx, ex ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkCard)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${idx + 1}. ${ex.name}",
                            color = TextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${ex.sets} sets × ${ex.repsOrSecs}",
                            color = BrightGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                if (program.exercises.size > 2) {
                    Text(
                        text = if (expanded) "Show less ▲" else "+${program.exercises.size - 2} more exercises ▼",
                        color = MetallicGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                            .padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            GoldButton(
                text = "Start Workout Session",
                icon = Icons.Default.PlayArrow,
                onClick = onStart,
                modifier = Modifier.fillMaxWidth(),
                testTag = "start_workout_${program.id}"
            )
        }
    }
}

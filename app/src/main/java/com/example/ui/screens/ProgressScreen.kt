package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.FitnessViewModel
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxuryProgressBar
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.components.StatCard
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.DarkDivider
import com.example.ui.theme.GoldGradientEnd
import com.example.ui.theme.GoldGradientStart
import com.example.ui.theme.LuxuryBlack
import com.example.ui.theme.LuxuryRed
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextLightGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import java.util.Locale

@Composable
fun ProgressScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val progressLogs by viewModel.progressLogs.collectAsState()
    val latestProgress by viewModel.latestProgress.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    var showAddProgressDialog by remember { mutableStateOf(false) }

    val currentWeight = latestProgress?.weightKg ?: userProfile.weightKg
    val targetWeight = userProfile.targetWeightKg
    val height = latestProgress?.heightCm ?: userProfile.heightCm
    val bmi = latestProgress?.bmi ?: (currentWeight / ((height / 100f) * (height / 100f)))

    val totalWorkouts = progressLogs.sumOf { it.workoutsDone }
    val totalCalories = progressLogs.sumOf { it.caloriesBurned }

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
                        text = "PROGRESS TRACKING",
                        color = BrightGold,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Weight logs, BMI trajectory & physical milestones",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
                GoldButton(
                    text = "Log Entry",
                    icon = Icons.Default.Add,
                    onClick = { showAddProgressDialog = true },
                    testTag = "log_progress_btn"
                )
            }
        }

        // Summary Metric Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Current Weight",
                    value = "$currentWeight kg",
                    icon = Icons.Default.MonitorWeight,
                    accentColor = BrightGold,
                    subtitle = "Target: $targetWeight kg",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Body Mass Index",
                    value = String.format(Locale.US, "%.1f", bmi),
                    icon = Icons.Default.TrendingDown,
                    accentColor = SuccessGreen,
                    subtitle = "Healthy Zone",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Workouts Completed",
                    value = "$totalWorkouts Sessions",
                    icon = Icons.Default.FitnessCenter,
                    accentColor = MetallicGold,
                    subtitle = "${userProfile.streakDays} Day Active Streak",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Energy Expended",
                    value = "$totalCalories kcal",
                    icon = Icons.Default.LocalFireDepartment,
                    accentColor = LuxuryRed,
                    subtitle = "Stored in Room DB",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Goal Progress Card
        item {
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Goal: ${userProfile.fitnessGoal}", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = BrightGold, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    val diff = (76.2f - currentWeight).coerceAtLeast(0f)
                    val totalDiff = 76.2f - targetWeight
                    val progressRatio = if (totalDiff > 0) (diff / totalDiff).coerceIn(0f, 1f) else 0.6f
                    Text("Lost ${String.format(Locale.US, "%.1f", diff)} kg towards $targetWeight kg goal", color = TextLightGray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    LuxuryProgressBar(progress = progressRatio)
                }
            }
        }

        // Weight Trend Interactive Chart
        item {
            LuxurySectionHeader(
                title = "Weight Trend (Recent 7 Entries)",
                subtitle = "Weight fluctuations and gradual trend"
            )
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Column {
                    if (progressLogs.isNotEmpty()) {
                        WeightBarChart(logs = progressLogs.takeLast(7))
                    } else {
                        Text("No progress logs recorded yet.", color = TextMuted, fontSize = 12.sp)
                    }
                }
            }
        }

        // History Log List
        item {
            LuxurySectionHeader(
                title = "Historical Biometric Logs",
                subtitle = "Database entries recorded on device"
            )
        }

        items(progressLogs.reversed()) { log ->
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(log.dateStr, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(
                            text = "${log.weightKg} kg • BMI ${log.bmi} • ${log.minutesTrained} mins trained (${log.caloriesBurned} kcal)",
                            color = TextWhite,
                            fontSize = 12.sp
                        )
                        if (log.notes.isNotBlank()) {
                            Text(log.notes, color = TextMuted, fontSize = 11.sp)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SuccessGreen.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("${log.workoutsDone} Workout", color = SuccessGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    if (showAddProgressDialog) {
        AddProgressDialog(
            currentWeight = currentWeight,
            currentHeight = height,
            onDismiss = { showAddProgressDialog = false },
            onConfirm = { w, h, wDone, mins, cals, water, notes ->
                viewModel.logProgress(w, h, wDone, mins, cals, water, notes)
                showAddProgressDialog = false
            }
        )
    }
}

@Composable
fun WeightBarChart(logs: List<com.example.data.local.ProgressEntryEntity>) {
    val maxWeight = (logs.maxOfOrNull { it.weightKg } ?: 80f) + 1f
    val minWeight = (logs.minOfOrNull { it.weightKg } ?: 70f) - 1f
    val weightRange = (maxWeight - minWeight).coerceAtLeast(1f)

    Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val barSpacing = canvasWidth / logs.size
            val barWidth = barSpacing * 0.45f

            // Baseline grid lines
            drawLine(
                color = DarkDivider,
                start = Offset(0f, canvasHeight),
                end = Offset(canvasWidth, canvasHeight),
                strokeWidth = 2f
            )

            logs.forEachIndexed { index, entry ->
                val normalizedHeight = ((entry.weightKg - minWeight) / weightRange).coerceIn(0.15f, 1f)
                val barHeight = normalizedHeight * (canvasHeight - 24f)
                val x = (index * barSpacing) + (barSpacing - barWidth) / 2f
                val y = canvasHeight - barHeight

                drawRoundRect(
                    brush = Brush.verticalGradient(listOf(BrightGold, MetallicGold)),
                    topLeft = Offset(x, y),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(6f, 6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            logs.forEach { log ->
                val shortDate = log.dateStr.takeLast(5)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(shortDate, color = TextMuted, fontSize = 10.sp)
                    Text("${log.weightKg}", color = TextWhite, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AddProgressDialog(
    currentWeight: Float,
    currentHeight: Float,
    onDismiss: () -> Unit,
    onConfirm: (weightKg: Float, heightCm: Float, workoutsDone: Int, minutesTrained: Int, caloriesBurned: Int, waterMl: Int, notes: String) -> Unit
) {
    var weightStr by remember { mutableStateOf("$currentWeight") }
    var heightStr by remember { mutableStateOf("$currentHeight") }
    var workoutsStr by remember { mutableStateOf("1") }
    var minutesStr by remember { mutableStateOf("45") }
    var caloriesStr by remember { mutableStateOf("400") }
    var notesStr by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardElevated,
        title = { Text("Log Daily Biometrics", color = BrightGold, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = weightStr,
                        onValueChange = { weightStr = it },
                        label = { Text("Weight (kg)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f).testTag("log_weight_input")
                    )
                    OutlinedTextField(
                        value = heightStr,
                        onValueChange = { heightStr = it },
                        label = { Text("Height (cm)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = minutesStr,
                        onValueChange = { minutesStr = it },
                        label = { Text("Mins Trained", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = caloriesStr,
                        onValueChange = { caloriesStr = it },
                        label = { Text("Calories Burned", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = notesStr,
                    onValueChange = { notesStr = it },
                    label = { Text("Notes (e.g. Felt strong on deadlifts)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            GoldButton(
                text = "Save Entry",
                onClick = {
                    onConfirm(
                        weightStr.toFloatOrNull() ?: currentWeight,
                        heightStr.toFloatOrNull() ?: currentHeight,
                        workoutsStr.toIntOrNull() ?: 1,
                        minutesStr.toIntOrNull() ?: 45,
                        caloriesStr.toIntOrNull() ?: 400,
                        3000,
                        notesStr
                    )
                },
                testTag = "confirm_log_progress_btn"
            )
        },
        dismissButton = {
            GoldOutlinedButton(text = "Cancel", onClick = onDismiss)
        }
    )
}

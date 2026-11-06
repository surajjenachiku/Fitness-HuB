package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.FitnessViewModel
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxuryProgressBar
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.DarkDivider
import com.example.ui.theme.LuxuryBlack
import com.example.ui.theme.LuxuryRed
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextLightGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun WorkoutPlayerScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val workout by viewModel.currentWorkout.collectAsState()
    val exerciseIndex by viewModel.currentExerciseIndex.collectAsState()
    val timerSeconds by viewModel.workoutTimerSeconds.collectAsState()
    val isRunning by viewModel.isWorkoutTimerRunning.collectAsState()

    if (workout == null) {
        Box(
            modifier = modifier.fillMaxSize().background(LuxuryBlack),
            contentAlignment = Alignment.Center
        ) {
            Text("No active workout", color = TextWhite)
        }
        return
    }

    val currentEx = workout!!.exercises[exerciseIndex.coerceIn(0, workout!!.exercises.size - 1)]
    val progressRatio = (exerciseIndex + 1).toFloat() / workout!!.exercises.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LuxuryBlack)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.closeSubScreen() },
                modifier = Modifier.testTag("workout_player_back_btn")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Exit", tint = BrightGold)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(workout!!.title, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Exercise ${exerciseIndex + 1} of ${workout!!.exercises.size}", color = TextMuted, fontSize = 12.sp)
            }
            IconButton(
                onClick = { viewModel.resetWorkoutTimer(45) },
                modifier = Modifier.testTag("workout_reset_timer_btn")
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset Timer", tint = BrightGold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        LuxuryProgressBar(progress = progressRatio)
        Spacer(modifier = Modifier.height(20.dp))

        // Timer Circle Display
        Box(
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .background(Brush.radialGradient(listOf(DarkCardElevated, LuxuryBlack)))
                .border(3.dp, if (isRunning) BrightGold else DarkDivider, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${timerSeconds}s",
                    color = if (timerSeconds <= 5) LuxuryRed else BrightGold,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = if (isRunning) "Interval Running" else "Timer Paused",
                    color = if (isRunning) SuccessGreen else TextMuted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Timer Controls
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            GoldOutlinedButton(
                text = "Previous",
                icon = Icons.Default.SkipPrevious,
                onClick = { viewModel.previousExercise() },
                modifier = Modifier.width(130.dp),
                testTag = "prev_exercise_btn"
            )
            GoldButton(
                text = if (isRunning) "Pause" else "Start Timer",
                icon = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                onClick = { viewModel.toggleWorkoutTimer() },
                modifier = Modifier.width(140.dp),
                testTag = "toggle_timer_btn"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Current Exercise Details Card
        LuxuryCard(
            borderBrush = Brush.horizontalGradient(listOf(BrightGold, MetallicGold)),
            backgroundColor = DarkCardElevated,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(currentEx.name, color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                        Text("Target: ${currentEx.targetMuscle}", color = BrightGold, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkDivider)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("${currentEx.sets} sets × ${currentEx.repsOrSecs}", color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = BrightGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Execution Guide:", color = BrightGold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Text(currentEx.instructions, color = TextLightGray, fontSize = 13.sp, lineHeight = 19.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Coach's Tip:", color = SuccessGreen, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Text(currentEx.tips, color = TextLightGray, fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Next Exercise / Finish Button
        val isLast = exerciseIndex >= workout!!.exercises.size - 1
        GoldButton(
            text = if (isLast) "Finish & Save Session" else "Next: ${workout!!.exercises[exerciseIndex + 1].name}",
            icon = if (isLast) Icons.Default.CheckCircle else Icons.Default.SkipNext,
            onClick = { viewModel.nextExercise() },
            modifier = Modifier.fillMaxWidth(),
            testTag = "next_exercise_btn"
        )
    }
}

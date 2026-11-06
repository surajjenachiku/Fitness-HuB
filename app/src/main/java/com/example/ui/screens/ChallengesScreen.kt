package com.example.ui.screens

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.repository.FitnessCatalog
import com.example.ui.FitnessViewModel
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxuryProgressBar
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.theme.BrightGold
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
fun ChallengesScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    var challenges by remember { mutableStateOf(FitnessCatalog.fitnessChallenges) }

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
                modifier = Modifier.testTag("challenges_back_btn")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BrightGold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "COMMUNITY CHALLENGES",
                    color = BrightGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Compete, build habits & earn elite reward badges",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentPadding = PaddingValues(bottom = 60.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(challenges) { ch ->
                val progressRatio = (ch.progressDays.toFloat() / ch.durationDays).coerceIn(0f, 1f)

                LuxuryCard(
                    borderBrush = Brush.horizontalGradient(
                        if (ch.isJoined) listOf(BrightGold, LuxuryRed)
                        else listOf(MetallicGold.copy(alpha = 0.3f), DarkDivider)
                    ),
                    backgroundColor = DarkCardElevated
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(ch.title, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(ch.description, color = TextLightGray, fontSize = 12.sp)
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(BrightGold.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = BrightGold, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("+${ch.rewardPoints} Pts", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Group, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("${ch.participantsCount} Athletes Active", color = TextMuted, fontSize = 11.sp)
                            }
                            Text("Day ${ch.progressDays} of ${ch.durationDays}", color = if (ch.isJoined) SuccessGreen else TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        if (ch.isJoined) {
                            Spacer(modifier = Modifier.height(6.dp))
                            LuxuryProgressBar(progress = progressRatio)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        if (ch.isJoined) {
                            GoldOutlinedButton(
                                text = "Log Today's Milestone (Day ${ch.progressDays + 1})",
                                icon = Icons.Default.CheckCircle,
                                onClick = {
                                    challenges = challenges.map {
                                        if (it.id == ch.id) it.copy(progressDays = (it.progressDays + 1).coerceAtMost(it.durationDays)) else it
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                testTag = "log_milestone_${ch.id}"
                            )
                        } else {
                            GoldButton(
                                text = "Join Challenge",
                                onClick = {
                                    challenges = challenges.map {
                                        if (it.id == ch.id) it.copy(isJoined = true) else it
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                testTag = "join_challenge_${ch.id}"
                            )
                        }
                    }
                }
            }
        }
    }
}

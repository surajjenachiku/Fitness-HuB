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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
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
import com.example.data.model.GymDiscoveryItem
import com.example.data.repository.FitnessCatalog
import com.example.ui.FitnessViewModel
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.DarkDivider
import com.example.ui.theme.LuxuryBlack
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.TextLightGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun GymDiscoveryScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val gyms = FitnessCatalog.gymDiscoveryList
    var bookedGym by remember { mutableStateOf<GymDiscoveryItem?>(null) }

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
                modifier = Modifier.testTag("discovery_back_btn")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BrightGold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "DISCOVER ELITE GYMS",
                    color = BrightGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Verified health clubs, powerlifting gyms & luxury fitness centers",
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
            item {
                LuxurySectionHeader(
                    title = "Featured Gym Clubs Nearby",
                    subtitle = "Filter by amenities, Olympic barbells & certified trainers"
                )
            }

            items(gyms) { gym ->
                LuxuryCard(
                    borderBrush = Brush.horizontalGradient(listOf(MetallicGold.copy(alpha = 0.4f), DarkDivider)),
                    backgroundColor = DarkCardElevated
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(gym.name, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("${gym.cityArea} • ${gym.distanceKm} km away", color = TextMuted, fontSize = 12.sp)
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = BrightGold, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("${gym.rating}", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(" (${gym.reviewCount})", color = TextMuted, fontSize = 11.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Amenities & Equipment:", color = TextLightGray, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            gym.amenities.take(3).forEach { amenity ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(DarkDivider)
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(amenity, color = TextWhite, fontSize = 10.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Starting from", color = TextMuted, fontSize = 10.sp)
                                Text("₹${gym.monthlyPrice} / Month", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                            GoldButton(
                                text = "Book Free Trial",
                                onClick = { bookedGym = gym },
                                modifier = Modifier.height(38.dp),
                                testTag = "book_trial_${gym.id}"
                            )
                        }
                    }
                }
            }
        }
    }

    if (bookedGym != null) {
        AlertDialog(
            onDismissRequest = { bookedGym = null },
            containerColor = DarkCardElevated,
            title = { Text("Complimentary Trial Pass Booked!", color = BrightGold, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Facility: ${bookedGym!!.name}", color = TextWhite, fontWeight = FontWeight.Bold)
                    Text("Location: ${bookedGym!!.cityArea}", color = TextLightGray, fontSize = 13.sp)
                    Text("Hours: ${bookedGym!!.openHours}", color = TextLightGray, fontSize = 13.sp)
                    Text("Helpline: ${bookedGym!!.phone}", color = BrightGold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Your VIP 1-Day Guest Pass has been generated in your account. Present this pass at the reception desk for instant biometric entry.",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                GoldButton(text = "Done", onClick = { bookedGym = null })
            }
        )
    }
}

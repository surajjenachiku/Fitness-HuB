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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.data.repository.FitnessCatalog
import com.example.ui.FitnessViewModel
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxuryProgressBar
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.components.StatCard
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.DarkDivider
import com.example.ui.theme.GoldGradientEnd
import com.example.ui.theme.GoldGradientStart
import com.example.ui.theme.InfoBlue
import com.example.ui.theme.LuxuryBlack
import com.example.ui.theme.LuxuryRed
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.RedGradientEnd
import com.example.ui.theme.RedGradientStart
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextLightGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val activeMemberCount by viewModel.activeMemberCount.collectAsState()
    val totalRevenue by viewModel.totalRevenue.collectAsState()
    val totalPendingFees by viewModel.totalPendingFees.collectAsState()
    val presentToday by viewModel.presentCountToday.collectAsState()
    val waterIntake by viewModel.waterIntakeMl.collectAsState()
    val todayCalories by viewModel.todayCalories.collectAsState()
    val todayProtein by viewModel.todayProtein.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LuxuryBlack)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
    ) {
        // App Bar & Status
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(MetallicGold, DarkDivider)))
                            .border(1.dp, BrightGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = "Fitness Hub",
                            tint = LuxuryBlack,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "FITNESS HUB",
                            color = BrightGold,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (userProfile.isOnlineMode) SuccessGreen else MetallicGold)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (userProfile.isOnlineMode) "Online Cloud Mode" else "Offline Demo Mode",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Row {
                    IconButton(
                        onClick = { viewModel.navigateToSubScreen("challenges") },
                        modifier = Modifier.testTag("challenges_icon_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "Challenges",
                            tint = BrightGold
                        )
                    }
                    IconButton(
                        onClick = { viewModel.navigateToSubScreen("monetization") },
                        modifier = Modifier.testTag("vip_icon_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "VIP Plans",
                            tint = LuxuryRed
                        )
                    }
                }
            }
        }

        // Role Switcher Bar
        item {
            Text(
                text = "ACTIVE ROLE & VIEWPORT",
                color = TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(UserRole.values()) { role ->
                    val isSelected = userProfile.role == role
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isSelected) Brush.horizontalGradient(listOf(MetallicGold, BrightGold))
                                else Brush.horizontalGradient(listOf(DarkCardElevated, DarkCard))
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) BrightGold else DarkDivider,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { viewModel.setUserRole(role) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("role_chip_${role.name.lowercase(Locale.ROOT)}")
                    ) {
                        Text(
                            text = role.displayName,
                            color = if (isSelected) LuxuryBlack else TextWhite,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Hero Motivation & Greeting Card
        item {
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(listOf(MetallicGold, LuxuryRed)),
                backgroundColor = DarkCardElevated,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Welcome back, ${userProfile.name}!",
                                color = TextWhite,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "Role: ${userProfile.role.displayName} • ${userProfile.gymName}",
                                color = MetallicGold,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(LuxuryRed.copy(alpha = 0.2f))
                                .border(1.dp, LuxuryRed, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = LuxuryRed,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${userProfile.streakDays} Day Streak",
                                    color = TextWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "\"Discipline is the bridge between elite ambition and undisputed accomplishment.\"",
                        color = TextLightGray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }

        // Dynamic Role-Based Hub Sections
        when (userProfile.role) {
            UserRole.GYM_OWNER, UserRole.SUPER_ADMIN -> {
                item {
                    LuxurySectionHeader(
                        title = "Gym Owner Command Center",
                        subtitle = "Real-time metrics, revenue, and active subscriptions",
                        actionText = "Full Console",
                        onActionClick = { viewModel.navigateToSubScreen("gym_management") }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatCard(
                            title = "Active Members",
                            value = "$activeMemberCount",
                            icon = Icons.Default.Group,
                            accentColor = SuccessGreen,
                            subtitle = "$presentToday checked in today",
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "Revenue Collected",
                            value = "₹${totalRevenue.toInt()}",
                            icon = Icons.Default.AccountBalanceWallet,
                            accentColor = BrightGold,
                            subtitle = "₹${totalPendingFees.toInt()} Pending",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GoldButton(
                            text = "Manage Members",
                            icon = Icons.Default.Group,
                            onClick = { viewModel.navigateToSubScreen("gym_management") },
                            modifier = Modifier.weight(1f),
                            testTag = "home_manage_members_btn"
                        )
                        GoldOutlinedButton(
                            text = "Gym Reports",
                            icon = Icons.Default.Calculate,
                            onClick = { viewModel.navigateToSubScreen("gym_management") },
                            modifier = Modifier.weight(1f),
                            testTag = "home_reports_btn"
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            UserRole.TRAINER -> {
                item {
                    LuxurySectionHeader(
                        title = "Trainer Coaching Portal",
                        subtitle = "Client rosters, workout assignments, and scheduled classes"
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatCard(
                            title = "Assigned Clients",
                            value = "24 Athletes",
                            icon = Icons.Default.Person,
                            accentColor = BrightGold,
                            subtitle = "4 sessions today",
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "Client Compliance",
                            value = "94%",
                            icon = Icons.Default.CheckCircle,
                            accentColor = SuccessGreen,
                            subtitle = "Target achieved",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    GoldButton(
                        text = "Assign Workout & Diet Plans",
                        icon = Icons.Default.FitnessCenter,
                        onClick = { viewModel.selectTab("workout") },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "trainer_assign_workout_btn"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            UserRole.MEMBER -> {
                item {
                    LuxurySectionHeader(
                        title = "Olympia Gold Membership",
                        subtitle = "Valid until 15 Jan 2027 • Coach: Vikram Rathore"
                    )
                    LuxuryCard(
                        borderBrush = Brush.horizontalGradient(listOf(MetallicGold, DarkDivider)),
                        backgroundColor = DarkCardElevated,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Annual VIP Plan", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("ACTIVE", color = SuccessGreen, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Monthly Attendance: 92% (23/25 days attended)", color = TextLightGray, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            LuxuryProgressBar(progress = 0.92f)
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                GoldOutlinedButton(
                                    text = "Book Session",
                                    onClick = { viewModel.navigateToSubScreen("gym_discovery") },
                                    modifier = Modifier.weight(1f)
                                )
                                GoldButton(
                                    text = "Check In (QR)",
                                    onClick = { viewModel.markAttendance(1, userProfile.name) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            UserRole.NORMAL_USER -> {
                item {
                    LuxurySectionHeader(
                        title = "Today's Fitness Target",
                        subtitle = "Calorie budget, protein intake, and hydration"
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatCard(
                            title = "Calories Consumed",
                            value = "$todayCalories kcal",
                            icon = Icons.Default.LocalFireDepartment,
                            accentColor = LuxuryRed,
                            subtitle = "Target: 2200 kcal",
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "Protein Intake",
                            value = "${todayProtein.toInt()}g",
                            icon = Icons.Default.Restaurant,
                            accentColor = BrightGold,
                            subtitle = "Target: 140g",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    // Water Tracker Quick Card
                    LuxuryCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.WaterDrop, contentDescription = null, tint = InfoBlue, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Hydration Tracker", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                }
                                Text("$waterIntake ml / 3500 ml logged", color = TextMuted, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                LuxuryProgressBar(progress = waterIntake / 3500f)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            GoldButton(
                                text = "+250ml",
                                onClick = { viewModel.addWater(250) },
                                testTag = "add_water_quick_btn"
                            )
                        }
                    }
                }
            }
        }

        // Quick Feature Cards (Calculators, Indian Diet, Workouts, Challenges)
        item {
            LuxurySectionHeader(
                title = "Elite Training & Tools",
                subtitle = "Scientific fitness utilities and nutrition programs"
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Calculator Hub Card
                LuxuryCard(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateToSubScreen("calculators") },
                    backgroundColor = DarkCardElevated
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(BrightGold.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Calculate, contentDescription = null, tint = BrightGold, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Fitness Calculators", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("BMI, Calorie, Protein & Water", color = TextMuted, fontSize = 11.sp)
                    }
                }

                // Indian Diet Plan Card
                LuxuryCard(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.selectTab("diet") },
                    backgroundColor = DarkCardElevated
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SuccessGreen.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Restaurant, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Indian Diet Plans", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Veg & Non-Veg Macro Menus", color = TextMuted, fontSize = 11.sp)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Guided Workouts Card
                LuxuryCard(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.selectTab("workout") },
                    backgroundColor = DarkCardElevated
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(LuxuryRed.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = LuxuryRed, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Workout Programs", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Home, Fat Loss, 10/20/30m", color = TextMuted, fontSize = 11.sp)
                    }
                }

                // Yoga & Wellness Card
                LuxuryCard(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateToSubScreen("yoga_meditation") },
                    backgroundColor = DarkCardElevated
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(InfoBlue.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SelfImprovement, contentDescription = null, tint = InfoBlue, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Yoga & Breathing", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Pranayama & 4-7-8 Timer", color = TextMuted, fontSize = 11.sp)
                    }
                }
            }
        }

        // Daily Recommended Workout Preview
        item {
            LuxurySectionHeader(
                title = "Today's Featured Workout",
                subtitle = "High Intensity Fat Shredder (25 Min)",
                actionText = "All Workouts",
                onActionClick = { viewModel.selectTab("workout") }
            )
            val featured = FitnessCatalog.workoutPrograms[1]
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(listOf(LuxuryRed, MetallicGold)),
                backgroundColor = DarkCardElevated,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(featured.title, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("${featured.durationMin} mins • ${featured.calories} kcal • ${featured.difficulty}", color = TextMuted, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Burpees, Mountain Climbers, Jumping Lunges, Plank Taps", color = TextLightGray, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    GoldButton(
                        text = "Start",
                        icon = Icons.Default.PlayArrow,
                        onClick = { viewModel.startWorkout(featured) },
                        testTag = "start_featured_workout_btn"
                    )
                }
            }
        }

        // Gym Discovery Banner
        item {
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(listOf(MetallicGold, DarkDivider)),
                backgroundColor = DarkCard,
                onClick = { viewModel.navigateToSubScreen("gym_discovery") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(MetallicGold.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = BrightGold, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Find Nearby Premium Gyms", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("Explore facilities, certified trainers, and membership pricing", color = TextMuted, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

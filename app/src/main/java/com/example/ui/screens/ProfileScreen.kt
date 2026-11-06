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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.FitnessViewModel
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxurySectionHeader
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
fun ProfileScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val userProfile by viewModel.userProfile.collectAsState()
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showFirebaseInfoDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LuxuryBlack)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "USER ACCOUNT & SETTINGS",
                color = BrightGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Text(
                text = "Role permissions, local Room database & cloud sync",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        // Profile Avatar Card
        item {
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(listOf(MetallicGold, DarkDivider)),
                backgroundColor = DarkCardElevated
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(BrightGold, DarkDivider)))
                                .border(2.dp, BrightGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userProfile.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.joinToString(""),
                                color = LuxuryBlack,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(userProfile.name, color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(userProfile.email, color = TextMuted, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(BrightGold.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(userProfile.role.displayName, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }
                        GoldOutlinedButton(
                            text = "Edit",
                            icon = Icons.Default.Edit,
                            onClick = { showEditProfileDialog = true },
                            modifier = Modifier.height(38.dp),
                            testTag = "edit_profile_btn"
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Weight: ${userProfile.weightKg} kg", color = TextLightGray, fontSize = 12.sp)
                        Text("Height: ${userProfile.heightCm} cm", color = TextLightGray, fontSize = 12.sp)
                        Text("Goal: ${userProfile.targetWeightKg} kg", color = BrightGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Database & Authentication Mode Toggle
        item {
            LuxurySectionHeader(
                title = "Database & Auth Engine",
                subtitle = "Switch between Offline Demo Mode and Production Firebase"
            )
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(
                                imageVector = if (userProfile.isOnlineMode) Icons.Default.CloudDone else Icons.Default.CloudOff,
                                contentDescription = null,
                                tint = if (userProfile.isOnlineMode) SuccessGreen else MetallicGold,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (userProfile.isOnlineMode) "Online Production Mode" else "Offline Demo Mode",
                                    color = TextWhite,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (userProfile.isOnlineMode) "Syncing to Firebase Firestore" else "Saving securely in local Room Database",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                        Switch(
                            checked = userProfile.isOnlineMode,
                            onCheckedChange = { viewModel.toggleOnlineMode(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = LuxuryBlack,
                                checkedTrackColor = SuccessGreen,
                                uncheckedThumbColor = MetallicGold,
                                uncheckedTrackColor = DarkDivider
                            ),
                            modifier = Modifier.testTag("mode_switch")
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    GoldOutlinedButton(
                        text = "Firebase Setup & Connection Guide",
                        icon = Icons.Default.Info,
                        onClick = { showFirebaseInfoDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "firebase_instructions_btn"
                    )
                }
            }
        }

        // Role Selector Section
        item {
            LuxurySectionHeader(
                title = "Switch Role Experience",
                subtitle = "Test dashboard layouts for each user tier"
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                UserRole.values().forEach { role ->
                    val isCurrent = userProfile.role == role
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isCurrent) BrightGold.copy(alpha = 0.15f) else DarkCardElevated)
                            .border(1.dp, if (isCurrent) BrightGold else DarkDivider, RoundedCornerShape(12.dp))
                            .clickable { viewModel.setUserRole(role) }
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(role.displayName, color = if (isCurrent) BrightGold else TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(
                                text = when (role) {
                                    UserRole.GYM_OWNER -> "Full control of member rosters, attendance, income & expenses"
                                    UserRole.TRAINER -> "Assigned client schedules, routine management & coaching"
                                    UserRole.MEMBER -> "Gym validity, coach assignments, gym check-in & workouts"
                                    UserRole.NORMAL_USER -> "Independent home workouts, diet tracking & body metrics"
                                    UserRole.SUPER_ADMIN -> "Platform ecosystem oversight & gym verification"
                                },
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                        if (isCurrent) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(BrightGold)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("ACTIVE", color = LuxuryBlack, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                    }
                }
            }
        }

        // Quick Links (Monetization & Discovery)
        item {
            LuxurySectionHeader(title = "App Features & Subscriptions")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GoldOutlinedButton(
                    text = "Subscription Plans",
                    icon = Icons.Default.Star,
                    onClick = { viewModel.navigateToSubScreen("monetization") },
                    modifier = Modifier.weight(1f),
                    testTag = "profile_vip_plans_btn"
                )
                GoldOutlinedButton(
                    text = "Discover Gyms",
                    icon = Icons.Default.Person,
                    onClick = { viewModel.navigateToSubScreen("gym_discovery") },
                    modifier = Modifier.weight(1f),
                    testTag = "profile_discovery_btn"
                )
            }
        }
    }

    if (showEditProfileDialog) {
        EditProfileDialog(
            profile = userProfile,
            onDismiss = { showEditProfileDialog = false },
            onConfirm = { name, email, phone, weight, height, target, goal ->
                viewModel.updateUserProfile(name, email, phone, weight, height, target, goal)
                showEditProfileDialog = false
            }
        )
    }

    if (showFirebaseInfoDialog) {
        AlertDialog(
            onDismissRequest = { showFirebaseInfoDialog = false },
            containerColor = DarkCardElevated,
            title = { Text("Connecting Your Firebase Backend", color = BrightGold, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "1. Create a project at console.firebase.google.com.\n" +
                                "2. Register your Android app package name:\ncom.aistudio.fitnesshub.vzkrqp\n" +
                                "3. Download your google-services.json file and place it in the /app folder.\n" +
                                "4. Enable Email/Password or Google Sign-In in Firebase Authentication.\n" +
                                "5. Enable Cloud Firestore in production mode with secure role-based rules.\n" +
                                "6. In offline mode, the app runs completely standalone on local Room Database.",
                        color = TextLightGray,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                GoldButton(text = "Got It", onClick = { showFirebaseInfoDialog = false })
            }
        )
    }
}

@Composable
fun EditProfileDialog(
    profile: com.example.data.model.UserProfile,
    onDismiss: () -> Unit,
    onConfirm: (name: String, email: String, phone: String, weight: Float, height: Float, target: Float, goal: String) -> Unit
) {
    var name by remember { mutableStateOf(profile.name) }
    var email by remember { mutableStateOf(profile.email) }
    var phone by remember { mutableStateOf(profile.phone) }
    var weightStr by remember { mutableStateOf("${profile.weightKg}") }
    var heightStr by remember { mutableStateOf("${profile.heightCm}") }
    var targetStr by remember { mutableStateOf("${profile.targetWeightKg}") }
    var goal by remember { mutableStateOf(profile.fitnessGoal) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCardElevated,
        title = { Text("Edit User Profile", color = BrightGold, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth().testTag("edit_name_input")
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = weightStr,
                        onValueChange = { weightStr = it },
                        label = { Text("Weight (kg)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = heightStr,
                        onValueChange = { heightStr = it },
                        label = { Text("Height (cm)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = targetStr,
                    onValueChange = { targetStr = it },
                    label = { Text("Target Weight (kg)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = goal,
                    onValueChange = { goal = it },
                    label = { Text("Fitness Goal", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            GoldButton(
                text = "Save Profile",
                onClick = {
                    onConfirm(
                        name,
                        email,
                        phone,
                        weightStr.toFloatOrNull() ?: profile.weightKg,
                        heightStr.toFloatOrNull() ?: profile.heightCm,
                        targetStr.toFloatOrNull() ?: profile.targetWeightKg,
                        goal
                    )
                },
                testTag = "save_profile_btn"
            )
        },
        dismissButton = {
            GoldOutlinedButton(text = "Cancel", onClick = onDismiss)
        }
    )
}

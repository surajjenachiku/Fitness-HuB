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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.FitnessViewModel
import com.example.ui.components.GoldButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.StatCard
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.DarkDivider
import com.example.ui.theme.InfoBlue
import com.example.ui.theme.LuxuryBlack
import com.example.ui.theme.LuxuryRed
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextLightGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WarningOrange
import java.util.Locale

@Composable
fun CalculatorsScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val calcTabs = listOf("BMI", "Calorie (TDEE)", "Protein", "Water", "Ideal Weight")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LuxuryBlack)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.closeSubScreen() },
                modifier = Modifier.testTag("calc_back_btn")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BrightGold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "FITNESS CALCULATORS",
                    color = BrightGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Precision biometrics & energy expenditure calculations",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }

        // Subtabs
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
            calcTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 11.sp,
                            color = if (selectedTab == index) BrightGold else TextMuted
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> BmiCalculatorView()
            1 -> CalorieCalculatorView()
            2 -> ProteinCalculatorView()
            3 -> WaterCalculatorView()
            4 -> IdealWeightCalculatorView()
        }
    }
}

@Composable
fun BmiCalculatorView() {
    var weightStr by remember { mutableStateOf("74") }
    var heightStr by remember { mutableStateOf("178") }
    var bmiResult by remember { mutableStateOf<Float?>(23.4f) }

    val weight = weightStr.toFloatOrNull() ?: 0f
    val height = heightStr.toFloatOrNull() ?: 0f

    val category = when {
        bmiResult == null -> ""
        bmiResult!! < 18.5f -> "Underweight"
        bmiResult!! < 24.9f -> "Normal & Healthy"
        bmiResult!! < 29.9f -> "Overweight"
        else -> "Obese Class"
    }

    val categoryColor = when {
        bmiResult == null -> BrightGold
        bmiResult!! < 18.5f -> WarningOrange
        bmiResult!! < 24.9f -> SuccessGreen
        bmiResult!! < 29.9f -> WarningOrange
        else -> LuxuryRed
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Body Mass Index (BMI) Calculator", color = BrightGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Assesses whether your weight is in a healthy proportion to height.", color = TextMuted, fontSize = 12.sp)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = weightStr,
                    onValueChange = { weightStr = it },
                    label = { Text("Weight (kg)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.weight(1f).testTag("bmi_weight_input")
                )
                OutlinedTextField(
                    value = heightStr,
                    onValueChange = { heightStr = it },
                    label = { Text("Height (cm)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.weight(1f).testTag("bmi_height_input")
                )
            }
        }

        item {
            GoldButton(
                text = "Calculate BMI",
                icon = Icons.Default.Calculate,
                onClick = {
                    if (height > 0f && weight > 0f) {
                        val hM = height / 100f
                        bmiResult = weight / (hM * hM)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                testTag = "calc_bmi_btn"
            )
        }

        if (bmiResult != null) {
            item {
                LuxuryCard(
                    borderBrush = Brush.horizontalGradient(listOf(categoryColor, DarkDivider)),
                    backgroundColor = DarkCardElevated
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text("Calculated Body Mass Index", color = TextMuted, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = String.format(Locale.US, "%.1f", bmiResult),
                            color = BrightGold,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(categoryColor.copy(alpha = 0.2f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(category, color = categoryColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = when (category) {
                                "Normal & Healthy" -> "Optimal body composition! Maintain balanced resistance training and sufficient dietary protein."
                                "Overweight" -> "Consider a modest 300-500 kcal deficit paired with high protein and daily step targets."
                                "Underweight" -> "Target a calorie surplus with nutrient-dense foods (nuts, paneer, eggs, oats, ghee) and strength training."
                                else -> "Structured lifestyle overhaul: resistance training, progressive walking, and structured calorie moderation."
                            },
                            color = TextLightGray,
                            fontSize = 12.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalorieCalculatorView() {
    var ageStr by remember { mutableStateOf("26") }
    var isMale by remember { mutableStateOf(true) }
    var weightStr by remember { mutableStateOf("74") }
    var heightStr by remember { mutableStateOf("178") }
    var activityMultiplier by remember { mutableStateOf(1.55f) } // Moderate

    val weight = weightStr.toFloatOrNull() ?: 70f
    val height = heightStr.toFloatOrNull() ?: 175f
    val age = ageStr.toIntOrNull() ?: 25

    // Mifflin - St Jeor BMR
    val bmr = if (isMale) {
        (10f * weight) + (6.25f * height) - (5f * age) + 5f
    } else {
        (10f * weight) + (6.25f * height) - (5f * age) - 161f
    }
    val tdee = (bmr * activityMultiplier).toInt()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Calorie & TDEE Calculator", color = BrightGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Total Daily Energy Expenditure based on Mifflin-St Jeor formula", color = TextMuted, fontSize = 12.sp)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isMale) BrightGold else DarkCard)
                        .clickable { isMale = true }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Male", color = if (isMale) LuxuryBlack else TextWhite, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (!isMale) BrightGold else DarkCard)
                        .clickable { isMale = false }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Female", color = if (!isMale) LuxuryBlack else TextWhite, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = ageStr,
                    onValueChange = { ageStr = it },
                    label = { Text("Age", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.weight(1f)
                )
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
        }

        item {
            Text("Activity Level:", color = TextLightGray, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            val levels = listOf(
                "Sedentary (Desk Job)" to 1.2f,
                "Light (1-2 days/wk)" to 1.375f,
                "Moderate (3-5 days/wk)" to 1.55f,
                "Very Active (6-7 days/wk)" to 1.725f
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                levels.forEach { (label, mult) ->
                    val selected = activityMultiplier == mult
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selected) BrightGold.copy(alpha = 0.2f) else DarkCard)
                            .border(1.dp, if (selected) BrightGold else DarkDivider, RoundedCornerShape(8.dp))
                            .clickable { activityMultiplier = mult }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(label, color = if (selected) BrightGold else TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        item {
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Energy Targets Breakdown", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Base Metabolic Rate (BMR):", color = TextMuted, fontSize = 12.sp)
                        Text("${bmr.toInt()} kcal", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Maintenance Calories (TDEE):", color = TextWhite, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text("$tdee kcal", color = BrightGold, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Fat Loss (Mild Deficit -400kcal):", color = SuccessGreen, fontSize = 13.sp)
                        Text("${tdee - 400} kcal", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Muscle Gain (Lean Surplus +300kcal):", color = InfoBlue, fontSize = 13.sp)
                        Text("${tdee + 300} kcal", color = InfoBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ProteinCalculatorView() {
    var weightStr by remember { mutableStateOf("74") }
    var goalMultiplier by remember { mutableStateOf(2.0f) } // g per kg

    val weight = weightStr.toFloatOrNull() ?: 70f
    val totalProtein = (weight * goalMultiplier).toInt()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Daily Protein Requirement Calculator", color = BrightGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Based on lean body mass preservation and muscle protein synthesis", color = TextMuted, fontSize = 12.sp)
        }

        item {
            OutlinedTextField(
                value = weightStr,
                onValueChange = { weightStr = it },
                label = { Text("Body Weight (kg)", color = TextMuted) },
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                modifier = Modifier.fillMaxWidth().testTag("protein_weight_input")
            )
        }

        item {
            Text("Fitness Ambition:", color = TextLightGray, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            val goals = listOf(
                "Maintenance / General Health (1.2g/kg)" to 1.2f,
                "Fat Loss & Muscle Retention (1.8g/kg)" to 1.8f,
                "Hypertrophy & Muscle Gain (2.0g/kg)" to 2.0f,
                "Heavy Athlete / Powerlifting (2.2g/kg)" to 2.2f
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                goals.forEach { (label, mult) ->
                    val selected = goalMultiplier == mult
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selected) BrightGold.copy(alpha = 0.2f) else DarkCard)
                            .border(1.dp, if (selected) BrightGold else DarkDivider, RoundedCornerShape(8.dp))
                            .clickable { goalMultiplier = mult }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(label, color = if (selected) BrightGold else TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        item {
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(listOf(BrightGold, MetallicGold)),
                backgroundColor = DarkCardElevated
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Daily Protein Goal", color = TextMuted, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${totalProtein}g / Day",
                        color = BrightGold,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text("Divide into 4 meals of ~${totalProtein / 4}g protein each", color = TextLightGray, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun WaterCalculatorView() {
    var weightStr by remember { mutableStateOf("74") }
    var workoutMinsStr by remember { mutableStateOf("45") }
    var isHotClimate by remember { mutableStateOf(true) }

    val weight = weightStr.toFloatOrNull() ?: 70f
    val workoutMins = workoutMinsStr.toFloatOrNull() ?: 30f

    // Base: 35ml per kg + 500ml per 30m exercise + 500ml for hot weather
    val baseMl = weight * 35f
    val exerciseMl = (workoutMins / 30f) * 500f
    val climateMl = if (isHotClimate) 500f else 0f
    val totalLiters = (baseMl + exerciseMl + climateMl) / 1000f

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Optimal Water Intake Calculator", color = BrightGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Adjusts for body mass, workout intensity, and climate", color = TextMuted, fontSize = 12.sp)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = weightStr,
                    onValueChange = { weightStr = it },
                    label = { Text("Weight (kg)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = workoutMinsStr,
                    onValueChange = { workoutMinsStr = it },
                    label = { Text("Training (min/day)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(DarkCard)
                    .clickable { isHotClimate = !isHotClimate }
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Hot / Humid Tropical Climate", color = TextWhite, fontSize = 13.sp)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isHotClimate) BrightGold else DarkDivider)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(if (isHotClimate) "YES (+500ml)" else "NO", color = if (isHotClimate) LuxuryBlack else TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Recommended Daily Water", color = TextMuted, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = String.format(Locale.US, "%.1f Liters", totalLiters),
                        color = InfoBlue,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text("Approximately ${(totalLiters * 4).toInt()} standard glasses (250ml each)", color = TextLightGray, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun IdealWeightCalculatorView() {
    var heightStr by remember { mutableStateOf("178") }
    var isMale by remember { mutableStateOf(true) }

    val heightCm = heightStr.toFloatOrNull() ?: 175f
    val heightInches = heightCm / 2.54f
    val inchesOver5Feet = (heightInches - 60f).coerceAtLeast(0f)

    // Devine formula
    val devineKg = if (isMale) 50f + (2.3f * inchesOver5Feet) else 45.5f + (2.3f * inchesOver5Feet)
    // Robinson formula
    val robinsonKg = if (isMale) 52f + (1.9f * inchesOver5Feet) else 49f + (1.7f * inchesOver5Feet)
    // Miller formula
    val millerKg = if (isMale) 56.2f + (1.41f * inchesOver5Feet) else 53.1f + (1.36f * inchesOver5Feet)

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Ideal Weight Reference Calculator", color = BrightGold, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Clinical references using Devine, Robinson & Miller standard algorithms", color = TextMuted, fontSize = 12.sp)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isMale) BrightGold else DarkCard)
                        .clickable { isMale = true }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Male", color = if (isMale) LuxuryBlack else TextWhite, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (!isMale) BrightGold else DarkCard)
                        .clickable { isMale = false }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Female", color = if (!isMale) LuxuryBlack else TextWhite, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            OutlinedTextField(
                value = heightStr,
                onValueChange = { heightStr = it },
                label = { Text("Height (cm)", color = TextMuted) },
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                modifier = Modifier.fillMaxWidth().testTag("ideal_weight_height_input")
            )
        }

        item {
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Clinical Reference Ranges", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Devine Formula (Standard):", color = TextLightGray, fontSize = 13.sp)
                        Text(String.format(Locale.US, "%.1f kg", devineKg), color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Robinson Formula:", color = TextLightGray, fontSize = 13.sp)
                        Text(String.format(Locale.US, "%.1f kg", robinsonKg), color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Miller Formula:", color = TextLightGray, fontSize = 13.sp)
                        Text(String.format(Locale.US, "%.1f kg", millerKg), color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Healthy Athletic Target: ${String.format(Locale.US, "%.1f - %.1f kg", devineKg - 4f, devineKg + 5f)}",
                        color = SuccessGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

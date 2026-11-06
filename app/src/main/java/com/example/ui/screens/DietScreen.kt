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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.collectAsState
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
import com.example.data.local.MealEntryEntity
import com.example.data.model.IndianDietPlan
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
import com.example.ui.theme.InfoBlue
import com.example.ui.theme.LuxuryBlack
import com.example.ui.theme.LuxuryRed
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextLightGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun DietScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val dietTabs = listOf("Indian Diets", "Protein Guide", "Water Tracker", "Meal Log")

    val waterIntake by viewModel.waterIntakeMl.collectAsState()
    val mealsToday by viewModel.mealsToday.collectAsState()
    val todayCalories by viewModel.todayCalories.collectAsState()
    val todayProtein by viewModel.todayProtein.collectAsState()

    var showAddMealDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LuxuryBlack)
    ) {
        // Screen Header
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = "NUTRITION & DIET CENTER",
                color = BrightGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Text(
                text = "Authentic Indian nutrition, macros & hydration balance",
                color = TextMuted,
                fontSize = 12.sp
            )
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
            dietTabs.forEachIndexed { index, title ->
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
            0 -> IndianDietPlansContent()
            1 -> ProteinGuideContent()
            2 -> WaterTrackerContent(
                waterIntake = waterIntake,
                onAddWater = { viewModel.addWater(it) },
                onReset = { viewModel.resetWater() }
            )
            3 -> MealLogContent(
                meals = mealsToday,
                todayCalories = todayCalories,
                todayProtein = todayProtein,
                onAddMealClick = { showAddMealDialog = true },
                onDeleteMeal = { viewModel.deleteMeal(it) }
            )
        }
    }

    if (showAddMealDialog) {
        AddMealDialog(
            onDismiss = { showAddMealDialog = false },
            onConfirm = { type, food, cals, prot, carbs, fat ->
                viewModel.addMeal(type, food, cals, prot, carbs, fat)
                showAddMealDialog = false
            }
        )
    }
}

@Composable
private fun IndianDietPlansContent() {
    val plans = FitnessCatalog.indianDietPlans
    var activePlanIndex by remember { mutableIntStateOf(0) }
    val activePlan = plans[activePlanIndex]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                plans.forEachIndexed { index, plan ->
                    val isSelected = activePlanIndex == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) BrightGold else DarkCard)
                            .border(1.dp, if (isSelected) BrightGold else DarkDivider, RoundedCornerShape(12.dp))
                            .clickable { activePlanIndex = index }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (plan.isVegetarian) "Pure Vegetarian Plan" else "Non-Vegetarian Plan",
                            color = if (isSelected) LuxuryBlack else TextWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        item {
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(listOf(MetallicGold, DarkDivider)),
                backgroundColor = DarkCardElevated
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(activePlan.title, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (activePlan.isVegetarian) SuccessGreen.copy(alpha = 0.2f) else LuxuryRed.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (activePlan.isVegetarian) "100% VEG" else "NON-VEG",
                                color = if (activePlan.isVegetarian) SuccessGreen else LuxuryRed,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(activePlan.description, color = TextLightGray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text("Target: ${activePlan.dailyCalories} kcal", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Protein: ${activePlan.targetProteinG}g Daily", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        item {
            MealTimingCard(title = "1. Power Breakfast", time = "07:30 AM - 08:30 AM", items = activePlan.breakfast)
        }
        item {
            MealTimingCard(title = "2. Mid-Morning Vitality", time = "11:00 AM", items = activePlan.midMorningSnack)
        }
        item {
            MealTimingCard(title = "3. Anabolic Indian Lunch", time = "01:30 PM - 02:30 PM", items = activePlan.lunch)
        }
        item {
            MealTimingCard(title = "4. Pre/Post-Workout Snack", time = "05:30 PM", items = activePlan.eveningSnack)
        }
        item {
            MealTimingCard(title = "5. Lean Recovery Dinner", time = "08:30 PM - 09:30 PM", items = activePlan.dinner)
        }
    }
}

@Composable
private fun MealTimingCard(title: String, time: String, items: List<String>) {
    LuxuryCard(backgroundColor = DarkCardElevated) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(time, color = TextMuted, fontSize = 11.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            items.forEach { food ->
                Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(food, color = TextWhite, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun ProteinGuideContent() {
    val foods = FitnessCatalog.indianProteinGuide

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            LuxurySectionHeader(
                title = "Indian Food Protein Index",
                subtitle = "Accurate macros per portion to hit your daily requirement"
            )
        }

        items(foods) { food ->
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (food.isVeg) SuccessGreen else LuxuryRed)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(food.name, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Text("${food.hindiName} • Portion: ${food.portion}", color = TextMuted, fontSize = 12.sp)
                        Text("${food.calories} kcal • Carbs: ${food.carbsGrams}g • Fat: ${food.fatGrams}g", color = TextLightGray, fontSize = 11.sp)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(BrightGold.copy(alpha = 0.15f))
                            .border(1.dp, BrightGold, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${food.proteinGrams}g", color = BrightGold, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                            Text("PROTEIN", color = TextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WaterTrackerContent(
    waterIntake: Int,
    onAddWater: (Int) -> Unit,
    onReset: () -> Unit
) {
    val dailyGoal = 3500
    val progress = (waterIntake.toFloat() / dailyGoal).coerceIn(0f, 1f)
    val glasses = waterIntake / 250

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LuxurySectionHeader(
            title = "Hydration Tracker",
            subtitle = "Optimal cellular performance & recovery"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Large circular progress visual
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(DarkCardElevated)
                .border(4.dp, Brush.sweepGradient(listOf(InfoBlue, BrightGold, InfoBlue)), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.WaterDrop, contentDescription = null, tint = InfoBlue, modifier = Modifier.size(36.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Text("$waterIntake ml", color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                Text("Target: $dailyGoal ml ($glasses glasses)", color = TextMuted, fontSize = 12.sp)
                Text("${(progress * 100).toInt()}% Achieved", color = InfoBlue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Quick Log Additions:", color = TextLightGray, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            GoldButton(
                text = "+250 ml (Glass)",
                onClick = { onAddWater(250) },
                modifier = Modifier.weight(1f),
                testTag = "add_250_btn"
            )
            GoldButton(
                text = "+500 ml (Bottle)",
                onClick = { onAddWater(500) },
                modifier = Modifier.weight(1f),
                testTag = "add_500_btn"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        GoldOutlinedButton(
            text = "Reset Water Log",
            icon = Icons.Default.Refresh,
            onClick = onReset,
            modifier = Modifier.fillMaxWidth(),
            testTag = "reset_water_btn"
        )
    }
}

@Composable
private fun MealLogContent(
    meals: List<MealEntryEntity>,
    todayCalories: Int,
    todayProtein: Float,
    onAddMealClick: () -> Unit,
    onDeleteMeal: (MealEntryEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Calories Ingested",
                    value = "$todayCalories kcal",
                    icon = Icons.Default.LocalFireDepartment,
                    accentColor = LuxuryRed,
                    subtitle = "Daily budget: 2200 kcal",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Protein Total",
                    value = "${todayProtein.toInt()}g",
                    icon = Icons.Default.Restaurant,
                    accentColor = BrightGold,
                    subtitle = "Target: 140g",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            GoldButton(
                text = "Log Meal Entry",
                icon = Icons.Default.Add,
                onClick = onAddMealClick,
                modifier = Modifier.fillMaxWidth(),
                testTag = "log_meal_btn"
            )
        }

        item {
            LuxurySectionHeader(
                title = "Today's Logged Meals",
                subtitle = "Breakfast, Lunch, Snacks & Dinner"
            )
        }

        items(meals) { meal ->
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(meal.mealType, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("• ${meal.foodTitle}", color = TextWhite, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        }
                        Text("${meal.calories} kcal • Protein: ${meal.proteinG}g • Carbs: ${meal.carbsG}g • Fat: ${meal.fatG}g", color = TextMuted, fontSize = 11.sp)
                    }
                    IconButton(
                        onClick = { onDeleteMeal(meal) },
                        modifier = Modifier.testTag("delete_meal_${meal.id}")
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = LuxuryRed)
                    }
                }
            }
        }
    }
}

@Composable
fun AddMealDialog(
    onDismiss: () -> Unit,
    onConfirm: (mealType: String, foodTitle: String, calories: Int, proteinG: Float, carbsG: Float, fatG: Float) -> Unit
) {
    var mealType by remember { mutableStateOf("Breakfast") }
    var foodTitle by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("350") }
    var protein by remember { mutableStateOf("25") }
    var carbs by remember { mutableStateOf("40") }
    var fat by remember { mutableStateOf("10") }

    val types = listOf("Breakfast", "Lunch", "Snack", "Dinner")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCard,
        title = { Text("Log Food Item", color = BrightGold, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Meal Category:", color = TextLightGray, fontSize = 12.sp)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(types) { t ->
                        val selected = mealType == t
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selected) BrightGold else DarkDivider)
                                .clickable { mealType = t }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(t, color = if (selected) LuxuryBlack else TextWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                OutlinedTextField(
                    value = foodTitle,
                    onValueChange = { foodTitle = it },
                    label = { Text("Food Name (e.g. 2 Paneer Rotis)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth().testTag("meal_name_field")
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = calories,
                        onValueChange = { calories = it },
                        label = { Text("Calories", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = protein,
                        onValueChange = { protein = it },
                        label = { Text("Protein (g)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = carbs,
                        onValueChange = { carbs = it },
                        label = { Text("Carbs (g)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = fat,
                        onValueChange = { fat = it },
                        label = { Text("Fat (g)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            GoldButton(
                text = "Save Meal",
                onClick = {
                    if (foodTitle.isNotBlank()) {
                        onConfirm(
                            mealType,
                            foodTitle,
                            calories.toIntOrNull() ?: 0,
                            protein.toFloatOrNull() ?: 0f,
                            carbs.toFloatOrNull() ?: 0f,
                            fat.toFloatOrNull() ?: 0f
                        )
                    }
                },
                testTag = "confirm_meal_btn"
            )
        },
        dismissButton = {
            GoldOutlinedButton(text = "Cancel", onClick = onDismiss)
        }
    )
}

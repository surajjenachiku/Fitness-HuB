package com.example.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.FitnessViewModel
import com.example.ui.screens.CalculatorsScreen
import com.example.ui.screens.ChallengesScreen
import com.example.ui.screens.DietScreen
import com.example.ui.screens.GymDiscoveryScreen
import com.example.ui.screens.GymManagementScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MonetizationScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.WorkoutPlayerScreen
import com.example.ui.screens.WorkoutScreen
import com.example.ui.screens.YogaMeditationScreen
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkCard
import com.example.ui.theme.DarkDivider
import com.example.ui.theme.LuxuryBlack
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

data class NavItem(
    val id: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun FitnessHubApp(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val activeSubScreen by viewModel.activeSubScreen.collectAsState()

    // Handle back button for sub-screens
    BackHandler(enabled = activeSubScreen != null || selectedTab != "home") {
        if (activeSubScreen != null) {
            viewModel.closeSubScreen()
        } else if (selectedTab != "home") {
            viewModel.selectTab("home")
        }
    }

    val navItems = listOf(
        NavItem("home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
        NavItem("workout", "Workout", Icons.Filled.FitnessCenter, Icons.Outlined.FitnessCenter),
        NavItem("diet", "Diet", Icons.Filled.Restaurant, Icons.Outlined.Restaurant),
        NavItem("progress", "Progress", Icons.Filled.TrendingUp, Icons.Outlined.TrendingUp),
        NavItem("profile", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (activeSubScreen == null) {
                NavigationBar(
                    containerColor = LuxuryBlack,
                    tonalElevation = 8.dp,
                    windowInsets = WindowInsets.navigationBars,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            brush = Brush.horizontalGradient(listOf(DarkDivider, MetallicGold.copy(alpha = 0.35f), DarkDivider)),
                            shape = RectangleShape
                        )
                ) {
                    navItems.forEach { item ->
                        val isSelected = selectedTab == item.id
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.selectTab(item.id) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = LuxuryBlack,
                                selectedTextColor = BrightGold,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted,
                                indicatorColor = BrightGold
                            ),
                            modifier = Modifier.testTag("nav_item_${item.id}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (activeSubScreen) {
                "gym_management" -> GymManagementScreen(viewModel = viewModel)
                "calculators" -> CalculatorsScreen(viewModel = viewModel)
                "workout_player" -> WorkoutPlayerScreen(viewModel = viewModel)
                "yoga_meditation" -> YogaMeditationScreen(viewModel = viewModel)
                "monetization" -> MonetizationScreen(viewModel = viewModel)
                "gym_discovery" -> GymDiscoveryScreen(viewModel = viewModel)
                "challenges" -> ChallengesScreen(viewModel = viewModel)
                else -> {
                    when (selectedTab) {
                        "home" -> HomeScreen(viewModel = viewModel)
                        "workout" -> WorkoutScreen(viewModel = viewModel)
                        "diet" -> DietScreen(viewModel = viewModel)
                        "progress" -> ProgressScreen(viewModel = viewModel)
                        "profile" -> ProfileScreen(viewModel = viewModel)
                        else -> HomeScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

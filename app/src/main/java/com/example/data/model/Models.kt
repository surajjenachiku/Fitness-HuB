package com.example.data.model

enum class UserRole(val displayName: String, val badgeColorHex: Long) {
    GYM_OWNER("Gym Owner", 0xFFD4AF37),
    TRAINER("Fitness Trainer", 0xFFFFD700),
    MEMBER("Gym Member", 0xFF20C878),
    NORMAL_USER("Fitness Enthusiast", 0xFF388AF6),
    SUPER_ADMIN("Super Admin", 0xFFB5121B)
}

enum class MembershipPlan(val title: String, val durationMonths: Int, val standardPrice: Int) {
    MONTHLY("Monthly Plan", 1, 1500),
    QUARTERLY("Quarterly (3 Months)", 3, 3800),
    HALF_YEARLY("Half-Yearly (6 Months)", 6, 6800),
    YEARLY("Annual VIP (12 Months)", 12, 11500)
}

enum class FeeStatus(val label: String) {
    PAID("Paid in Full"),
    PENDING("Payment Pending"),
    OVERDUE("Payment Overdue")
}

data class UserProfile(
    val id: String = "u_default",
    val name: String = "Suraj Kumar",
    val email: String = "surajkumarjena88@gmail.com",
    val phone: String = "+91 98765 43210",
    val role: UserRole = UserRole.NORMAL_USER,
    val gymName: String = "Olympia Gold Elite Gym",
    val weightKg: Float = 74.5f,
    val heightCm: Float = 178f,
    val targetWeightKg: Float = 70.0f,
    val fitnessGoal: String = "Muscle Building & Lean Shred",
    val streakDays: Int = 14,
    val points: Int = 1850,
    val isOnlineMode: Boolean = false
)

data class Exercise(
    val id: String,
    val name: String,
    val sets: Int,
    val repsOrSecs: String,
    val targetMuscle: String,
    val calorieBurnEst: Int,
    val instructions: String,
    val tips: String
)

data class WorkoutProgram(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String, // Home, Beginner, Fat Loss, Strength, No Equipment, 10-Min, 20-Min, 30-Min
    val durationMin: Int,
    val difficulty: String, // Beginner, Intermediate, Advanced
    val calories: Int,
    val exercises: List<Exercise>
)

data class FoodItem(
    val name: String,
    val hindiName: String,
    val portion: String,
    val calories: Int,
    val proteinGrams: Float,
    val carbsGrams: Float,
    val fatGrams: Float,
    val isVeg: Boolean,
    val category: String // Dal, Dairy, Grain, Poultry, Snack
)

data class IndianDietPlan(
    val id: String,
    val title: String,
    val description: String,
    val isVegetarian: Boolean,
    val dailyCalories: Int,
    val targetProteinG: Int,
    val breakfast: List<String>,
    val midMorningSnack: List<String>,
    val lunch: List<String>,
    val eveningSnack: List<String>,
    val dinner: List<String>
)

data class YogaPose(
    val id: String,
    val englishName: String,
    val sanskritName: String,
    val durationSec: Int,
    val difficulty: String,
    val benefits: String,
    val steps: List<String>
)

data class GymDiscoveryItem(
    val id: String,
    val name: String,
    val cityArea: String,
    val rating: Float,
    val reviewCount: Int,
    val monthlyPrice: Int,
    val distanceKm: Float,
    val amenities: List<String>,
    val openHours: String,
    val phone: String
)

data class FitnessChallenge(
    val id: String,
    val title: String,
    val description: String,
    val durationDays: Int,
    val participantsCount: Int,
    val progressDays: Int,
    val isJoined: Boolean,
    val rewardPoints: Int
)

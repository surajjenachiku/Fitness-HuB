package com.example.data.repository

import com.example.data.local.AttendanceEntity
import com.example.data.local.ExpenseEntity
import com.example.data.local.FitnessHubDatabase
import com.example.data.local.GymMemberEntity
import com.example.data.local.GymTrainerEntity
import com.example.data.local.MealEntryEntity
import com.example.data.local.ProgressEntryEntity
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FitnessRepository(private val database: FitnessHubDatabase) {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _waterIntakeMl = MutableStateFlow(2250)
    val waterIntakeMl: StateFlow<Int> = _waterIntakeMl.asStateFlow()

    fun getTodayDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    // --- Role & Profile Management ---
    fun setUserRole(role: UserRole) {
        _userProfile.value = _userProfile.value.copy(role = role)
    }

    fun updateUserProfile(
        name: String,
        email: String,
        phone: String,
        weightKg: Float,
        heightCm: Float,
        targetWeightKg: Float,
        fitnessGoal: String
    ) {
        _userProfile.value = _userProfile.value.copy(
            name = name,
            email = email,
            phone = phone,
            weightKg = weightKg,
            heightCm = heightCm,
            targetWeightKg = targetWeightKg,
            fitnessGoal = fitnessGoal
        )
    }

    fun toggleOnlineMode(enabled: Boolean) {
        _userProfile.value = _userProfile.value.copy(isOnlineMode = enabled)
    }

    fun addWater(amountMl: Int) {
        _waterIntakeMl.value = (_waterIntakeMl.value + amountMl).coerceIn(0, 8000)
    }

    fun resetWater() {
        _waterIntakeMl.value = 0
    }

    // --- Gym Members ---
    val allMembers: Flow<List<GymMemberEntity>> = database.gymMemberDao().getAllMembers()

    fun searchMembers(query: String): Flow<List<GymMemberEntity>> {
        return database.gymMemberDao().searchMembers(query)
    }

    val pendingFeeMembers: Flow<List<GymMemberEntity>> = database.gymMemberDao().getPendingFeeMembers()
    val activeMemberCount: Flow<Int> = database.gymMemberDao().getActiveMemberCount()
    val totalRevenue: Flow<Double?> = database.gymMemberDao().getTotalCollectedRevenue()
    val totalPendingFees: Flow<Double?> = database.gymMemberDao().getTotalPendingAmount()

    suspend fun insertMember(member: GymMemberEntity): Long {
        return database.gymMemberDao().insertMember(member)
    }

    suspend fun updateMember(member: GymMemberEntity) {
        database.gymMemberDao().updateMember(member)
    }

    suspend fun deleteMember(memberId: Long) {
        database.gymMemberDao().deleteMemberById(memberId)
    }

    // --- Trainers ---
    val allTrainers: Flow<List<GymTrainerEntity>> = database.gymTrainerDao().getAllTrainers()

    suspend fun insertTrainer(trainer: GymTrainerEntity): Long {
        return database.gymTrainerDao().insertTrainer(trainer)
    }

    // --- Attendance ---
    fun getAttendanceForToday(): Flow<List<AttendanceEntity>> {
        return database.attendanceDao().getAttendanceForDate(getTodayDate())
    }

    fun getPresentCountToday(): Flow<Int> {
        return database.attendanceDao().getPresentCountForDate(getTodayDate())
    }

    suspend fun recordAttendance(memberId: Long, memberName: String, status: String) {
        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val record = AttendanceEntity(
            memberId = memberId,
            memberName = memberName,
            dateStr = getTodayDate(),
            checkInTime = currentTime,
            status = status
        )
        database.attendanceDao().insertAttendance(record)
    }

    // --- Expenses ---
    val allExpenses: Flow<List<ExpenseEntity>> = database.expenseDao().getAllExpenses()
    val totalExpenseAmount: Flow<Double?> = database.expenseDao().getTotalExpenseAmount()

    suspend fun insertExpense(expense: ExpenseEntity): Long {
        return database.expenseDao().insertExpense(expense)
    }

    // --- Progress Logs ---
    val allProgress: Flow<List<ProgressEntryEntity>> = database.progressDao().getAllProgress()
    val latestProgress: Flow<ProgressEntryEntity?> = database.progressDao().getLatestProgress()

    suspend fun logProgress(weightKg: Float, heightCm: Float, workoutsDone: Int, minutesTrained: Int, caloriesBurned: Int, waterMl: Int, notes: String) {
        val bmi = (weightKg / ((heightCm / 100f) * (heightCm / 100f)))
        val entry = ProgressEntryEntity(
            dateStr = getTodayDate(),
            weightKg = weightKg,
            heightCm = heightCm,
            bmi = String.format(Locale.US, "%.1f", bmi).toFloat(),
            workoutsDone = workoutsDone,
            minutesTrained = minutesTrained,
            caloriesBurned = caloriesBurned,
            waterIntakeMl = waterMl,
            notes = notes
        )
        database.progressDao().insertProgress(entry)
    }

    // --- Meals ---
    fun getMealsToday(): Flow<List<MealEntryEntity>> {
        return database.mealDao().getMealsForDate(getTodayDate())
    }

    fun getTodayCalories(): Flow<Int?> {
        return database.mealDao().getTotalCaloriesForDate(getTodayDate())
    }

    fun getTodayProtein(): Flow<Float?> {
        return database.mealDao().getTotalProteinForDate(getTodayDate())
    }

    suspend fun insertMeal(meal: MealEntryEntity): Long {
        return database.mealDao().insertMeal(meal)
    }

    suspend fun deleteMeal(meal: MealEntryEntity) {
        database.mealDao().deleteMeal(meal)
    }
}

package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AttendanceEntity
import com.example.data.local.ExpenseEntity
import com.example.data.local.FitnessHubDatabase
import com.example.data.local.GymMemberEntity
import com.example.data.local.GymTrainerEntity
import com.example.data.local.MealEntryEntity
import com.example.data.local.ProgressEntryEntity
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import com.example.data.model.WorkoutProgram
import com.example.data.repository.FitnessCatalog
import com.example.data.repository.FitnessRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class FitnessViewModel(application: Application) : AndroidViewModel(application) {

    private val database = FitnessHubDatabase.getDatabase(application)
    private val repository = FitnessRepository(database)

    // User Profile & Role
    val userProfile: StateFlow<UserProfile> = repository.userProfile
    val waterIntakeMl: StateFlow<Int> = repository.waterIntakeMl

    // Navigation and sub-navigation state
    private val _selectedTab = MutableStateFlow("home")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    private val _activeSubScreen = MutableStateFlow<String?>(null) // e.g. "gym_management", "calculators", "workout_player", "yoga_meditation", "monetization", "gym_discovery", "challenges"
    val activeSubScreen: StateFlow<String?> = _activeSubScreen.asStateFlow()

    // Search query for members
    private val _memberSearchQuery = MutableStateFlow("")
    val memberSearchQuery: StateFlow<String> = _memberSearchQuery.asStateFlow()

    // Data streams from Room
    val members: StateFlow<List<GymMemberEntity>> = _memberSearchQuery.combine(repository.allMembers) { query, list ->
        if (query.isBlank()) list else list.filter {
            it.name.contains(query, ignoreCase = true) || it.phone.contains(query) || it.planType.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pendingFeeMembers: StateFlow<List<GymMemberEntity>> = repository.pendingFeeMembers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeMemberCount: StateFlow<Int> = repository.activeMemberCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalRevenue: StateFlow<Double> = repository.totalRevenue.combine(repository.allMembers) { rev, _ ->
        rev ?: 0.0
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalPendingFees: StateFlow<Double> = repository.totalPendingFees
        .combine(repository.allMembers) { pending, _ -> pending ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val trainers: StateFlow<List<GymTrainerEntity>> = repository.allTrainers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val attendanceToday: StateFlow<List<AttendanceEntity>> = repository.getAttendanceForToday()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val presentCountToday: StateFlow<Int> = repository.getPresentCountToday()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val expenses: StateFlow<List<ExpenseEntity>> = repository.allExpenses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalExpenses: StateFlow<Double> = repository.totalExpenseAmount
        .combine(repository.allExpenses) { exp, _ -> exp ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val progressLogs: StateFlow<List<ProgressEntryEntity>> = repository.allProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val latestProgress: StateFlow<ProgressEntryEntity?> = repository.latestProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val mealsToday: StateFlow<List<MealEntryEntity>> = repository.getMealsToday()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayCalories: StateFlow<Int> = repository.getTodayCalories()
        .combine(repository.getMealsToday()) { cal, _ -> cal ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val todayProtein: StateFlow<Float> = repository.getTodayProtein()
        .combine(repository.getMealsToday()) { prot, _ -> prot ?: 0f }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    // Active Workout Player State
    private val _currentWorkout = MutableStateFlow<WorkoutProgram?>(null)
    val currentWorkout: StateFlow<WorkoutProgram?> = _currentWorkout.asStateFlow()

    private val _currentExerciseIndex = MutableStateFlow(0)
    val currentExerciseIndex: StateFlow<Int> = _currentExerciseIndex.asStateFlow()

    private val _workoutTimerSeconds = MutableStateFlow(45)
    val workoutTimerSeconds: StateFlow<Int> = _workoutTimerSeconds.asStateFlow()

    private val _isWorkoutTimerRunning = MutableStateFlow(false)
    val isWorkoutTimerRunning: StateFlow<Boolean> = _isWorkoutTimerRunning.asStateFlow()

    private var workoutTimerJob: Job? = null

    // Meditation / Breathing State
    private val _breathingPhase = MutableStateFlow("Inhale")
    val breathingPhase: StateFlow<String> = _breathingPhase.asStateFlow()

    private val _breathingCount = MutableStateFlow(4)
    val breathingCount: StateFlow<Int> = _breathingCount.asStateFlow()

    private val _isBreathingRunning = MutableStateFlow(false)
    val isBreathingRunning: StateFlow<Boolean> = _isBreathingRunning.asStateFlow()

    private var breathingJob: Job? = null

    fun selectTab(tab: String) {
        _selectedTab.value = tab
        _activeSubScreen.value = null
    }

    fun navigateToSubScreen(screen: String) {
        _activeSubScreen.value = screen
    }

    fun closeSubScreen() {
        _activeSubScreen.value = null
    }

    fun setMemberSearchQuery(query: String) {
        _memberSearchQuery.value = query
    }

    fun setUserRole(role: UserRole) {
        repository.setUserRole(role)
    }

    fun toggleOnlineMode(enabled: Boolean) {
        repository.toggleOnlineMode(enabled)
    }

    fun updateUserProfile(
        name: String,
        email: String,
        phone: String,
        weightKg: Float,
        heightCm: Float,
        targetWeightKg: Float,
        goal: String
    ) {
        repository.updateUserProfile(name, email, phone, weightKg, heightCm, targetWeightKg, goal)
    }

    fun addWater(amountMl: Int) {
        repository.addWater(amountMl)
    }

    fun resetWater() {
        repository.resetWater()
    }

    // Member Management
    fun addNewMember(
        name: String,
        phone: String,
        email: String,
        planType: String,
        totalFee: Double,
        pendingFee: Double,
        trainerName: String
    ) {
        viewModelScope.launch {
            val today = repository.getTodayDate()
            val expiry = when (planType) {
                "Quarterly (3 Months)" -> "2027-02-05"
                "Half-Yearly (6 Months)" -> "2027-05-05"
                "Annual VIP (12 Months)" -> "2027-11-05"
                else -> "2026-12-05"
            }
            val feeStatus = if (pendingFee <= 0.0) "Paid" else "Pending"
            val member = GymMemberEntity(
                name = name,
                phone = phone,
                email = email,
                planType = planType,
                startDate = today,
                expiryDate = expiry,
                feeStatus = feeStatus,
                totalFee = totalFee,
                pendingFee = pendingFee,
                trainerName = trainerName,
                attendancePercent = 100,
                isActive = true
            )
            repository.insertMember(member)
        }
    }

    fun deleteMember(memberId: Long) {
        viewModelScope.launch {
            repository.deleteMember(memberId)
        }
    }

    fun markMemberFeePaid(member: GymMemberEntity) {
        viewModelScope.launch {
            repository.updateMember(member.copy(feeStatus = "Paid", pendingFee = 0.0))
        }
    }

    fun markAttendance(memberId: Long, memberName: String, status: String = "Present") {
        viewModelScope.launch {
            repository.recordAttendance(memberId, memberName, status)
        }
    }

    fun addExpense(title: String, category: String, amount: Double, notes: String) {
        viewModelScope.launch {
            val expense = ExpenseEntity(
                title = title,
                category = category,
                amount = amount,
                dateStr = repository.getTodayDate(),
                notes = notes
            )
            repository.insertExpense(expense)
        }
    }

    fun logProgress(weightKg: Float, heightCm: Float, workoutsDone: Int, minutesTrained: Int, caloriesBurned: Int, waterMl: Int, notes: String) {
        viewModelScope.launch {
            repository.logProgress(weightKg, heightCm, workoutsDone, minutesTrained, caloriesBurned, waterMl, notes)
        }
    }

    fun addMeal(mealType: String, foodTitle: String, calories: Int, proteinG: Float, carbsG: Float, fatG: Float) {
        viewModelScope.launch {
            val meal = MealEntryEntity(
                dateStr = repository.getTodayDate(),
                mealType = mealType,
                foodTitle = foodTitle,
                calories = calories,
                proteinG = proteinG,
                carbsG = carbsG,
                fatG = fatG
            )
            repository.insertMeal(meal)
        }
    }

    fun deleteMeal(meal: MealEntryEntity) {
        viewModelScope.launch {
            repository.deleteMeal(meal)
        }
    }

    // Workout Player Controls
    fun startWorkout(program: WorkoutProgram) {
        _currentWorkout.value = program
        _currentExerciseIndex.value = 0
        _workoutTimerSeconds.value = 45
        _isWorkoutTimerRunning.value = false
        navigateToSubScreen("workout_player")
    }

    fun toggleWorkoutTimer() {
        if (_isWorkoutTimerRunning.value) {
            workoutTimerJob?.cancel()
            _isWorkoutTimerRunning.value = false
        } else {
            _isWorkoutTimerRunning.value = true
            workoutTimerJob = viewModelScope.launch {
                while (_workoutTimerSeconds.value > 0) {
                    delay(1000)
                    _workoutTimerSeconds.value -= 1
                }
                _isWorkoutTimerRunning.value = false
            }
        }
    }

    fun resetWorkoutTimer(seconds: Int = 45) {
        workoutTimerJob?.cancel()
        _isWorkoutTimerRunning.value = false
        _workoutTimerSeconds.value = seconds
    }

    fun nextExercise() {
        val workout = _currentWorkout.value ?: return
        if (_currentExerciseIndex.value < workout.exercises.size - 1) {
            _currentExerciseIndex.value += 1
            resetWorkoutTimer(45)
        } else {
            // Finish workout & log
            viewModelScope.launch {
                logProgress(
                    weightKg = userProfile.value.weightKg,
                    heightCm = userProfile.value.heightCm,
                    workoutsDone = 1,
                    minutesTrained = workout.durationMin,
                    caloriesBurned = workout.calories,
                    waterMl = waterIntakeMl.value,
                    notes = "Completed ${workout.title}"
                )
            }
            closeSubScreen()
        }
    }

    fun previousExercise() {
        if (_currentExerciseIndex.value > 0) {
            _currentExerciseIndex.value -= 1
            resetWorkoutTimer(45)
        }
    }

    // Breathing / Meditation Session (Box Breathing 4-4-4-4)
    fun toggleBreathingSession() {
        if (_isBreathingRunning.value) {
            breathingJob?.cancel()
            _isBreathingRunning.value = false
            _breathingPhase.value = "Paused"
        } else {
            _isBreathingRunning.value = true
            breathingJob = viewModelScope.launch {
                val phases = listOf("Inhale (4s)", "Hold (4s)", "Exhale (4s)", "Hold (4s)")
                while (true) {
                    for (phase in phases) {
                        _breathingPhase.value = phase
                        for (i in 4 downTo 1) {
                            _breathingCount.value = i
                            delay(1000)
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        workoutTimerJob?.cancel()
        breathingJob?.cancel()
    }
}

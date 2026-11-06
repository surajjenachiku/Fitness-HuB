package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gym_members")
data class GymMemberEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val phone: String,
    val email: String,
    val planType: String, // Monthly, Quarterly, Half-Yearly, Yearly
    val startDate: String,
    val expiryDate: String,
    val feeStatus: String, // Paid, Pending, Overdue
    val totalFee: Double,
    val pendingFee: Double,
    val trainerName: String,
    val attendancePercent: Int,
    val isActive: Boolean = true
)

@Entity(tableName = "gym_trainers")
data class GymTrainerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val specialization: String,
    val phone: String,
    val email: String,
    val experienceYears: Int,
    val rating: Float,
    val memberCount: Int,
    val shiftTime: String
)

@Entity(tableName = "gym_attendance")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val memberId: Long,
    val memberName: String,
    val dateStr: String, // YYYY-MM-DD
    val checkInTime: String,
    val status: String // Present, Absent
)

@Entity(tableName = "gym_expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String, // Rent, Equipment, Maintenance, Salary, Electricity/Water, Supplements
    val amount: Double,
    val dateStr: String,
    val notes: String = ""
)

@Entity(tableName = "user_progress_logs")
data class ProgressEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateStr: String, // YYYY-MM-DD
    val weightKg: Float,
    val heightCm: Float,
    val bmi: Float,
    val workoutsDone: Int,
    val minutesTrained: Int,
    val caloriesBurned: Int,
    val waterIntakeMl: Int,
    val notes: String = ""
)

@Entity(tableName = "user_meal_logs")
data class MealEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateStr: String,
    val mealType: String, // Breakfast, Lunch, Snack, Dinner
    val foodTitle: String,
    val calories: Int,
    val proteinG: Float,
    val carbsG: Float,
    val fatG: Float
)

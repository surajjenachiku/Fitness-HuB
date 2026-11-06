package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        GymMemberEntity::class,
        GymTrainerEntity::class,
        AttendanceEntity::class,
        ExpenseEntity::class,
        ProgressEntryEntity::class,
        MealEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FitnessHubDatabase : RoomDatabase() {

    abstract fun gymMemberDao(): GymMemberDao
    abstract fun gymTrainerDao(): GymTrainerDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun progressDao(): ProgressDao
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: FitnessHubDatabase? = null

        fun getDatabase(context: Context): FitnessHubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitnessHubDatabase::class.java,
                    "fitness_hub.db"
                )
                    .addCallback(DatabasePrepopulateCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabasePrepopulateCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    prepopulateDatabase(database)
                }
            }
        }

        private suspend fun prepopulateDatabase(db: FitnessHubDatabase) {
            // Seed Trainers
            val trainers = listOf(
                GymTrainerEntity(
                    name = "Vikram Rathore",
                    specialization = "Strength & Bodybuilding",
                    phone = "+91 98201 11223",
                    email = "vikram.rathore@fitnesshub.com",
                    experienceYears = 8,
                    rating = 4.9f,
                    memberCount = 28,
                    shiftTime = "06:00 AM - 02:00 PM"
                ),
                GymTrainerEntity(
                    name = "Priya Sharma",
                    specialization = "HIIT & Fat Loss Specialist",
                    phone = "+91 98334 44556",
                    email = "priya.sharma@fitnesshub.com",
                    experienceYears = 6,
                    rating = 4.8f,
                    memberCount = 24,
                    shiftTime = "02:00 PM - 10:00 PM"
                ),
                GymTrainerEntity(
                    name = "Rohan Verma",
                    specialization = "CrossFit & Athletic Conditioning",
                    phone = "+91 98771 88990",
                    email = "rohan.verma@fitnesshub.com",
                    experienceYears = 5,
                    rating = 4.7f,
                    memberCount = 20,
                    shiftTime = "06:00 AM - 02:00 PM"
                ),
                GymTrainerEntity(
                    name = "Ananya Sen",
                    specialization = "Yoga, Mobility & Core",
                    phone = "+91 98112 33445",
                    email = "ananya.sen@fitnesshub.com",
                    experienceYears = 7,
                    rating = 5.0f,
                    memberCount = 18,
                    shiftTime = "07:00 AM - 12:00 PM"
                )
            )
            db.gymTrainerDao().insertAll(trainers)

            // Seed Members
            val members = listOf(
                GymMemberEntity(
                    name = "Aarav Patel",
                    phone = "+91 98920 12345",
                    email = "aarav.patel@gmail.com",
                    planType = "Annual VIP (12 Months)",
                    startDate = "2026-01-15",
                    expiryDate = "2027-01-15",
                    feeStatus = "Paid",
                    totalFee = 11500.0,
                    pendingFee = 0.0,
                    trainerName = "Vikram Rathore",
                    attendancePercent = 92,
                    isActive = true
                ),
                GymMemberEntity(
                    name = "Meera Nair",
                    phone = "+91 98211 54321",
                    email = "meera.nair@gmail.com",
                    planType = "Quarterly (3 Months)",
                    startDate = "2026-09-01",
                    expiryDate = "2026-12-01",
                    feeStatus = "Paid",
                    totalFee = 3800.0,
                    pendingFee = 0.0,
                    trainerName = "Priya Sharma",
                    attendancePercent = 88,
                    isActive = true
                ),
                GymMemberEntity(
                    name = "Kabir Deshmukh",
                    phone = "+91 98199 87654",
                    email = "kabir.d@gmail.com",
                    planType = "Monthly Plan",
                    startDate = "2026-10-10",
                    expiryDate = "2026-11-10",
                    feeStatus = "Pending",
                    totalFee = 1500.0,
                    pendingFee = 1500.0,
                    trainerName = "Rohan Verma",
                    attendancePercent = 75,
                    isActive = true
                ),
                GymMemberEntity(
                    name = "Sneha Kulkarni",
                    phone = "+91 98765 11223",
                    email = "sneha.k@gmail.com",
                    planType = "Half-Yearly (6 Months)",
                    startDate = "2026-05-15",
                    expiryDate = "2026-11-15",
                    feeStatus = "Overdue",
                    totalFee = 6800.0,
                    pendingFee = 2000.0,
                    trainerName = "Priya Sharma",
                    attendancePercent = 64,
                    isActive = true
                ),
                GymMemberEntity(
                    name = "Devansh Malhotra",
                    phone = "+91 99200 44332",
                    email = "dev.malhotra@gmail.com",
                    planType = "Annual VIP (12 Months)",
                    startDate = "2026-03-01",
                    expiryDate = "2027-03-01",
                    feeStatus = "Paid",
                    totalFee = 11500.0,
                    pendingFee = 0.0,
                    trainerName = "Vikram Rathore",
                    attendancePercent = 95,
                    isActive = true
                ),
                GymMemberEntity(
                    name = "Pooja Reddy",
                    phone = "+91 98450 67890",
                    email = "pooja.reddy@gmail.com",
                    planType = "Monthly Plan",
                    startDate = "2026-10-25",
                    expiryDate = "2026-11-25",
                    feeStatus = "Paid",
                    totalFee = 1500.0,
                    pendingFee = 0.0,
                    trainerName = "Ananya Sen",
                    attendancePercent = 85,
                    isActive = true
                )
            )
            db.gymMemberDao().insertAll(members)

            // Seed Attendance for Today
            val today = "2026-11-05"
            val attendanceList = listOf(
                AttendanceEntity(memberId = 1, memberName = "Aarav Patel", dateStr = today, checkInTime = "06:45 AM", status = "Present"),
                AttendanceEntity(memberId = 2, memberName = "Meera Nair", dateStr = today, checkInTime = "07:15 AM", status = "Present"),
                AttendanceEntity(memberId = 5, memberName = "Devansh Malhotra", dateStr = today, checkInTime = "08:00 AM", status = "Present"),
                AttendanceEntity(memberId = 6, memberName = "Pooja Reddy", dateStr = today, checkInTime = "08:30 AM", status = "Present")
            )
            db.attendanceDao().insertAll(attendanceList)

            // Seed Expenses
            val expenses = listOf(
                ExpenseEntity(title = "Gym Facility Rent", category = "Rent", amount = 45000.0, dateStr = "2026-11-01", notes = "Commercial space lease"),
                ExpenseEntity(title = "Olympic Barbells & Bumper Plates", category = "Equipment", amount = 18500.0, dateStr = "2026-11-02", notes = "Added 4 sets for deadlift platforms"),
                ExpenseEntity(title = "Treadmill Motor Servicing", category = "Maintenance", amount = 3200.0, dateStr = "2026-11-03", notes = "Bi-monthly motor oiling"),
                ExpenseEntity(title = "Electricity & Air Conditioning", category = "Utilities", amount = 12400.0, dateStr = "2026-11-04", notes = "HVAC & Lighting bill")
            )
            db.expenseDao().insertAll(expenses)

            // Seed Progress Entries
            val progressList = listOf(
                ProgressEntryEntity(dateStr = "2026-10-30", weightKg = 76.2f, heightCm = 178f, bmi = 24.0f, workoutsDone = 1, minutesTrained = 45, caloriesBurned = 380, waterIntakeMl = 3000),
                ProgressEntryEntity(dateStr = "2026-10-31", weightKg = 75.8f, heightCm = 178f, bmi = 23.9f, workoutsDone = 1, minutesTrained = 50, caloriesBurned = 420, waterIntakeMl = 3200),
                ProgressEntryEntity(dateStr = "2026-11-01", weightKg = 75.5f, heightCm = 178f, bmi = 23.8f, workoutsDone = 1, minutesTrained = 40, caloriesBurned = 350, waterIntakeMl = 2800),
                ProgressEntryEntity(dateStr = "2026-11-02", weightKg = 75.2f, heightCm = 178f, bmi = 23.7f, workoutsDone = 2, minutesTrained = 65, caloriesBurned = 510, waterIntakeMl = 3500),
                ProgressEntryEntity(dateStr = "2026-11-03", weightKg = 75.0f, heightCm = 178f, bmi = 23.6f, workoutsDone = 1, minutesTrained = 45, caloriesBurned = 400, waterIntakeMl = 3000),
                ProgressEntryEntity(dateStr = "2026-11-04", weightKg = 74.8f, heightCm = 178f, bmi = 23.6f, workoutsDone = 1, minutesTrained = 55, caloriesBurned = 460, waterIntakeMl = 3250),
                ProgressEntryEntity(dateStr = "2026-11-05", weightKg = 74.5f, heightCm = 178f, bmi = 23.5f, workoutsDone = 1, minutesTrained = 60, caloriesBurned = 490, waterIntakeMl = 3500)
            )
            db.progressDao().insertAll(progressList)

            // Seed Meals for Today
            val meals = listOf(
                MealEntryEntity(dateStr = today, mealType = "Breakfast", foodTitle = "Oatmeal with Almonds & Whey Protein", calories = 420, proteinG = 32f, carbsG = 48f, fatG = 9f),
                MealEntryEntity(dateStr = today, mealType = "Lunch", foodTitle = "Brown Rice + Paneer Bhurji + Mixed Salad", calories = 550, proteinG = 28f, carbsG = 62f, fatG = 16f),
                MealEntryEntity(dateStr = today, mealType = "Snack", foodTitle = "Roasted Chana & Green Tea", calories = 180, proteinG = 10f, carbsG = 24f, fatG = 4f)
            )
            db.mealDao().insertAll(meals)
        }
    }
}

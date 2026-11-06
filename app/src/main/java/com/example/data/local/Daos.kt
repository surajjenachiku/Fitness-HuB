package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GymMemberDao {
    @Query("SELECT * FROM gym_members ORDER BY id DESC")
    fun getAllMembers(): Flow<List<GymMemberEntity>>

    @Query("SELECT * FROM gym_members WHERE name LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%'")
    fun searchMembers(query: String): Flow<List<GymMemberEntity>>

    @Query("SELECT * FROM gym_members WHERE feeStatus = 'Pending' OR feeStatus = 'Overdue'")
    fun getPendingFeeMembers(): Flow<List<GymMemberEntity>>

    @Query("SELECT COUNT(*) FROM gym_members WHERE isActive = 1")
    fun getActiveMemberCount(): Flow<Int>

    @Query("SELECT SUM(totalFee - pendingFee) FROM gym_members")
    fun getTotalCollectedRevenue(): Flow<Double?>

    @Query("SELECT SUM(pendingFee) FROM gym_members")
    fun getTotalPendingAmount(): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: GymMemberEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(members: List<GymMemberEntity>)

    @Update
    suspend fun updateMember(member: GymMemberEntity)

    @Delete
    suspend fun deleteMember(member: GymMemberEntity)

    @Query("DELETE FROM gym_members WHERE id = :memberId")
    suspend fun deleteMemberById(memberId: Long)
}

@Dao
interface GymTrainerDao {
    @Query("SELECT * FROM gym_trainers ORDER BY rating DESC")
    fun getAllTrainers(): Flow<List<GymTrainerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainer(trainer: GymTrainerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trainers: List<GymTrainerEntity>)

    @Update
    suspend fun updateTrainer(trainer: GymTrainerEntity)

    @Delete
    suspend fun deleteTrainer(trainer: GymTrainerEntity)
}

@Dao
interface AttendanceDao {
    @Query("SELECT * FROM gym_attendance WHERE dateStr = :dateStr ORDER BY id DESC")
    fun getAttendanceForDate(dateStr: String): Flow<List<AttendanceEntity>>

    @Query("SELECT COUNT(*) FROM gym_attendance WHERE dateStr = :dateStr AND status = 'Present'")
    fun getPresentCountForDate(dateStr: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<AttendanceEntity>)
}

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM gym_expenses ORDER BY id DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT SUM(amount) FROM gym_expenses")
    fun getTotalExpenseAmount(): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expenses: List<ExpenseEntity>)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
}

@Dao
interface ProgressDao {
    @Query("SELECT * FROM user_progress_logs ORDER BY dateStr ASC")
    fun getAllProgress(): Flow<List<ProgressEntryEntity>>

    @Query("SELECT * FROM user_progress_logs ORDER BY id DESC LIMIT 1")
    fun getLatestProgress(): Flow<ProgressEntryEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(entry: ProgressEntryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<ProgressEntryEntity>)
}

@Dao
interface MealDao {
    @Query("SELECT * FROM user_meal_logs WHERE dateStr = :dateStr ORDER BY id DESC")
    fun getMealsForDate(dateStr: String): Flow<List<MealEntryEntity>>

    @Query("SELECT SUM(calories) FROM user_meal_logs WHERE dateStr = :dateStr")
    fun getTotalCaloriesForDate(dateStr: String): Flow<Int?>

    @Query("SELECT SUM(proteinG) FROM user_meal_logs WHERE dateStr = :dateStr")
    fun getTotalProteinForDate(dateStr: String): Flow<Float?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(meals: List<MealEntryEntity>)

    @Delete
    suspend fun deleteMeal(meal: MealEntryEntity)
}

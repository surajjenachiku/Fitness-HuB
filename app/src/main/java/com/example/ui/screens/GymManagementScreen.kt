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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.GymMemberEntity
import com.example.ui.FitnessViewModel
import com.example.ui.components.GoldButton
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.components.StatCard
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
import com.example.ui.theme.WarningOrange

@Composable
fun GymManagementScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableIntStateOf(0) }
    val sectionTabs = listOf("Members", "Attendance", "Finances", "Trainers")

    val members by viewModel.members.collectAsState()
    val searchQuery by viewModel.memberSearchQuery.collectAsState()
    val activeCount by viewModel.activeMemberCount.collectAsState()
    val totalRevenue by viewModel.totalRevenue.collectAsState()
    val totalPending by viewModel.totalPendingFees.collectAsState()
    val trainers by viewModel.trainers.collectAsState()
    val attendanceToday by viewModel.attendanceToday.collectAsState()
    val expenses by viewModel.expenses.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()

    var showAddMemberDialog by remember { mutableStateOf(false) }
    var showAddExpenseDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LuxuryBlack)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.closeSubScreen() },
                modifier = Modifier.testTag("gym_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = BrightGold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "GYM OWNER CONSOLE",
                    color = BrightGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Olympia Gold Elite Fitness Management",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }

        // Section Tabs
        TabRow(
            selectedTabIndex = selectedSection,
            containerColor = DarkCard,
            contentColor = BrightGold,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedSection]),
                    color = BrightGold
                )
            }
        ) {
            sectionTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedSection == index,
                    onClick = { selectedSection = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedSection == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            color = if (selectedSection == index) BrightGold else TextMuted
                        )
                    }
                )
            }
        }

        // Tab Content
        when (selectedSection) {
            0 -> MembersTabContent(
                members = members,
                searchQuery = searchQuery,
                onSearchChange = { viewModel.setMemberSearchQuery(it) },
                onAddClick = { showAddMemberDialog = true },
                onDelete = { viewModel.deleteMember(it) },
                onMarkPaid = { viewModel.markMemberFeePaid(it) },
                onMarkAttendance = { id, name -> viewModel.markAttendance(id, name) }
            )
            1 -> AttendanceTabContent(
                attendanceList = attendanceToday,
                activeMemberCount = activeCount,
                onQuickCheckIn = { id, name -> viewModel.markAttendance(id, name) }
            )
            2 -> FinancesTabContent(
                totalRevenue = totalRevenue,
                totalPending = totalPending,
                expenses = expenses,
                totalExpenses = totalExpenses,
                onAddExpenseClick = { showAddExpenseDialog = true }
            )
            3 -> TrainersTabContent(
                trainers = trainers
            )
        }
    }

    // Add Member Dialog
    if (showAddMemberDialog) {
        AddMemberDialog(
            trainers = trainers.map { it.name },
            onDismiss = { showAddMemberDialog = false },
            onConfirm = { name, phone, email, plan, fee, pending, trainer ->
                viewModel.addNewMember(name, phone, email, plan, fee, pending, trainer)
                showAddMemberDialog = false
            }
        )
    }

    // Add Expense Dialog
    if (showAddExpenseDialog) {
        AddExpenseDialog(
            onDismiss = { showAddExpenseDialog = false },
            onConfirm = { title, category, amount, notes ->
                viewModel.addExpense(title, category, amount, notes)
                showAddExpenseDialog = false
            }
        )
    }
}

@Composable
private fun MembersTabContent(
    members: List<GymMemberEntity>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onDelete: (Long) -> Unit,
    onMarkPaid: (GymMemberEntity) -> Unit,
    onMarkAttendance: (Long, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Search & Add Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = { Text("Search by name, phone...", color = TextMuted) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = BrightGold) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = BrightGold,
                        unfocusedBorderColor = DarkDivider,
                        focusedContainerColor = DarkCard,
                        unfocusedContainerColor = DarkCard
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("member_search_input")
                )
                Spacer(modifier = Modifier.width(8.dp))
                GoldButton(
                    text = "Add",
                    icon = Icons.Default.Add,
                    onClick = onAddClick,
                    testTag = "add_member_dialog_button"
                )
            }
        }

        items(members, key = { it.id }) { member ->
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(
                    listOf(
                        if (member.feeStatus == "Paid") SuccessGreen.copy(alpha = 0.4f) else LuxuryRed.copy(alpha = 0.4f),
                        DarkDivider
                    )
                ),
                backgroundColor = DarkCardElevated
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(member.name, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("${member.phone} • ${member.email}", color = TextMuted, fontSize = 12.sp)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    when (member.feeStatus) {
                                        "Paid" -> SuccessGreen.copy(alpha = 0.2f)
                                        "Pending" -> WarningOrange.copy(alpha = 0.2f)
                                        else -> LuxuryRed.copy(alpha = 0.2f)
                                    }
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = member.feeStatus,
                                color = when (member.feeStatus) {
                                    "Paid" -> SuccessGreen
                                    "Pending" -> WarningOrange
                                    else -> LuxuryRed
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Plan: ${member.planType}", color = BrightGold, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text("Expiry: ${member.expiryDate}", color = TextLightGray, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Trainer: ${member.trainerName}", color = TextMuted, fontSize = 12.sp)
                        Text("Attendance: ${member.attendancePercent}%", color = SuccessGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    if (member.pendingFee > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Pending Dues: ₹${member.pendingFee.toInt()}",
                            color = LuxuryRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (member.pendingFee > 0) {
                            GoldOutlinedButton(
                                text = "Mark Paid",
                                onClick = { onMarkPaid(member) },
                                modifier = Modifier.height(36.dp),
                                testTag = "mark_paid_${member.id}"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Button(
                            onClick = { onMarkAttendance(member.id, member.name) },
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen, contentColor = LuxuryBlack),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .height(36.dp)
                                .testTag("checkin_${member.id}")
                        ) {
                            Icon(Icons.Default.HowToReg, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Check-In", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { onDelete(member.id) },
                            modifier = Modifier.testTag("delete_member_${member.id}")
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = LuxuryRed)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AttendanceTabContent(
    attendanceList: List<com.example.data.local.AttendanceEntity>,
    activeMemberCount: Int,
    onQuickCheckIn: (Long, String) -> Unit
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
                    title = "Checked-in Today",
                    value = "${attendanceList.size}",
                    icon = Icons.Default.HowToReg,
                    accentColor = SuccessGreen,
                    subtitle = "Out of $activeMemberCount members",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Turnout Ratio",
                    value = "${if (activeMemberCount > 0) ((attendanceList.size.toFloat() / activeMemberCount) * 100).toInt() else 0}%",
                    icon = Icons.Default.Group,
                    accentColor = BrightGold,
                    subtitle = "Healthy gym traffic",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            LuxurySectionHeader(
                title = "Today's Live Attendance Stream",
                subtitle = "Real-time automated check-ins"
            )
        }

        items(attendanceList) { item ->
            LuxuryCard(
                borderBrush = Brush.horizontalGradient(listOf(SuccessGreen.copy(alpha = 0.3f), DarkDivider)),
                backgroundColor = DarkCardElevated
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(SuccessGreen.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(item.memberName, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("Member ID: #${item.memberId}", color = TextMuted, fontSize = 11.sp)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(item.checkInTime, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(item.status, color = SuccessGreen, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun FinancesTabContent(
    totalRevenue: Double,
    totalPending: Double,
    expenses: List<com.example.data.local.ExpenseEntity>,
    totalExpenses: Double,
    onAddExpenseClick: () -> Unit
) {
    val netIncome = totalRevenue - totalExpenses

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
                    title = "Total Collected",
                    value = "₹${totalRevenue.toInt()}",
                    icon = Icons.Default.AttachMoney,
                    accentColor = SuccessGreen,
                    subtitle = "₹${totalPending.toInt()} Pending",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Gym Expenses",
                    value = "₹${totalExpenses.toInt()}",
                    icon = Icons.Default.Receipt,
                    accentColor = LuxuryRed,
                    subtitle = "Net: ₹${netIncome.toInt()}",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            GoldButton(
                text = "Log Gym Expense",
                icon = Icons.Default.Add,
                onClick = onAddExpenseClick,
                modifier = Modifier.fillMaxWidth(),
                testTag = "log_expense_button"
            )
        }

        item {
            LuxurySectionHeader(
                title = "Expense Ledger",
                subtitle = "Operational costs, lease, equipment & payroll"
            )
        }

        items(expenses) { exp ->
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(exp.title, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("${exp.category} • ${exp.dateStr}", color = TextMuted, fontSize = 12.sp)
                        if (exp.notes.isNotBlank()) {
                            Text(exp.notes, color = TextLightGray, fontSize = 11.sp)
                        }
                    }
                    Text(
                        text = "-₹${exp.amount.toInt()}",
                        color = LuxuryRed,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
private fun TrainersTabContent(
    trainers: List<com.example.data.local.GymTrainerEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            LuxurySectionHeader(
                title = "Certified Training Staff",
                subtitle = "Manage coaching assignments, shift times and athlete reviews"
            )
        }

        items(trainers) { trainer ->
            LuxuryCard(backgroundColor = DarkCardElevated) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(trainer.name, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(trainer.specialization, color = BrightGold, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = BrightGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${trainer.rating}", color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Shift: ${trainer.shiftTime}", color = TextLightGray, fontSize = 12.sp)
                    Text("Athletes Coached: ${trainer.memberCount} • Experience: ${trainer.experienceYears} Years", color = TextMuted, fontSize = 12.sp)
                    Text("Contact: ${trainer.phone} • ${trainer.email}", color = TextMuted, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun AddMemberDialog(
    trainers: List<String>,
    onDismiss: () -> Unit,
    onConfirm: (name: String, phone: String, email: String, plan: String, fee: Double, pending: Double, trainer: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var plan by remember { mutableStateOf("Monthly Plan") }
    var totalFee by remember { mutableStateOf("1500") }
    var pendingFee by remember { mutableStateOf("0") }
    var selectedTrainer by remember { mutableStateOf(trainers.firstOrNull() ?: "Vikram Rathore") }

    val planOptions = listOf("Monthly Plan", "Quarterly (3 Months)", "Half-Yearly (6 Months)", "Annual VIP (12 Months)")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCard,
        title = { Text("Register New Gym Member", color = BrightGold, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth().testTag("add_name_field")
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth().testTag("add_phone_field")
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Select Membership Plan:", color = TextLightGray, fontSize = 12.sp)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(planOptions) { p ->
                        val selected = plan == p
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selected) BrightGold else DarkDivider)
                                .clickable {
                                    plan = p
                                    totalFee = when (p) {
                                        "Quarterly (3 Months)" -> "3800"
                                        "Half-Yearly (6 Months)" -> "6800"
                                        "Annual VIP (12 Months)" -> "11500"
                                        else -> "1500"
                                    }
                                }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(p, color = if (selected) LuxuryBlack else TextWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = totalFee,
                        onValueChange = { totalFee = it },
                        label = { Text("Total Fee (₹)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = pendingFee,
                        onValueChange = { pendingFee = it },
                        label = { Text("Pending Due (₹)", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            GoldButton(
                text = "Register",
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(
                            name,
                            phone,
                            if (email.isBlank()) "$name@fitnesshub.com" else email,
                            plan,
                            totalFee.toDoubleOrNull() ?: 1500.0,
                            pendingFee.toDoubleOrNull() ?: 0.0,
                            selectedTrainer
                        )
                    }
                },
                testTag = "confirm_add_member_btn"
            )
        },
        dismissButton = {
            GoldOutlinedButton(text = "Cancel", onClick = onDismiss)
        }
    )
}

@Composable
fun AddExpenseDialog(
    onDismiss: () -> Unit,
    onConfirm: (title: String, category: String, amount: Double, notes: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Rent") }
    var amount by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val categories = listOf("Rent", "Equipment", "Maintenance", "Salary", "Utilities", "Supplements")

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkCard,
        title = { Text("Log Gym Expense", color = BrightGold, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Expense Title", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth().testTag("expense_title_field")
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount (₹)", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth().testTag("expense_amount_field")
                )
                Text("Category:", color = TextLightGray, fontSize = 12.sp)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { cat ->
                        val selected = category == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selected) LuxuryRed else DarkDivider)
                                .clickable { category = cat }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(cat, color = TextWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes / Vendor Info", color = TextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextWhite, unfocusedTextColor = TextWhite, focusedBorderColor = BrightGold),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            GoldButton(
                text = "Save Expense",
                onClick = {
                    if (title.isNotBlank() && amount.isNotBlank()) {
                        onConfirm(title, category, amount.toDoubleOrNull() ?: 0.0, notes)
                    }
                },
                testTag = "confirm_save_expense_btn"
            )
        },
        dismissButton = {
            GoldOutlinedButton(text = "Cancel", onClick = onDismiss)
        }
    )
}

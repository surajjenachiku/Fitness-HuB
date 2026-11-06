package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.ui.components.GoldOutlinedButton
import com.example.ui.components.LuxuryCard
import com.example.ui.components.LuxurySectionHeader
import com.example.ui.theme.BrightGold
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.DarkDivider
import com.example.ui.theme.LuxuryBlack
import com.example.ui.theme.LuxuryRed
import com.example.ui.theme.MetallicGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextLightGray
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

data class PricingPlan(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val billingCycle: String,
    val isPopular: Boolean,
    val features: List<String>
)

@Composable
fun MonetizationScreen(
    viewModel: FitnessViewModel,
    modifier: Modifier = Modifier
) {
    var showPaymentInfoDialog by remember { mutableStateOf(false) }
    var selectedPlanTitle by remember { mutableStateOf("") }

    val plans = listOf(
        PricingPlan(
            id = "plan_free",
            title = "Free User Plan",
            subtitle = "Essential workout routines & basic tracking",
            price = "₹0",
            billingCycle = "Free Lifetime",
            isPopular = false,
            features = listOf(
                "Access to foundational bodyweight home workouts",
                "Basic BMI & Calorie calculators",
                "Water tracker with quick log buttons",
                "Offline local device data storage"
            )
        ),
        PricingPlan(
            id = "plan_user_vip",
            title = "Premium User Plan (VIP)",
            subtitle = "Complete access to all routines, Indian diets & advanced analytics",
            price = "₹299",
            billingCycle = "Billed Monthly",
            isPopular = true,
            features = listOf(
                "All 10, 20 & 30-min fat shredder & strength workouts",
                "Comprehensive Indian Veg & Non-Veg diet plans",
                "Full Protein Guide with 100g Indian food indexes",
                "Yoga Asanas & animated Box Breathing expander",
                "Weight progression trend charts & BMI history"
            )
        ),
        PricingPlan(
            id = "plan_trainer",
            title = "Trainer Pro Plan",
            subtitle = "For freelance coaches & gym fitness trainers",
            price = "₹799",
            billingCycle = "Billed Monthly",
            isPopular = false,
            features = listOf(
                "Manage up to 50 active athletes",
                "Custom workout assignment splits",
                "Client attendance & compliance tracking",
                "Public trainer profile on Gym Discovery"
            )
        ),
        PricingPlan(
            id = "plan_owner_monthly",
            title = "Gym Owner Monthly Plan",
            subtitle = "Complete cloud & offline club management suite",
            price = "₹1,999",
            billingCycle = "Billed Monthly",
            isPopular = false,
            features = listOf(
                "Unlimited gym member registry & search",
                "Automatic fee collection & pending dues reminders",
                "Real-time attendance check-in streams",
                "Expense ledger, rent, payroll & maintenance logs",
                "Daily revenue & monthly profit reports"
            )
        ),
        PricingPlan(
            id = "plan_owner_yearly",
            title = "Gym Owner Enterprise (Yearly)",
            subtitle = "Save 25% with multi-branch management & priority support",
            price = "₹17,999",
            billingCycle = "Billed Annually (Save ₹6,000)",
            isPopular = true,
            features = listOf(
                "Everything in Gym Owner Monthly Plan",
                "Multi-branch gym access & franchise reports",
                "Dedicated database backup & priority 24/7 support",
                "Automated member WhatsApp expiry notifications",
                "Custom branded club portal"
            )
        )
    )

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
                modifier = Modifier.testTag("monetization_back_btn")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BrightGold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "MEMBERSHIP TIERS & PRICING",
                    color = BrightGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Transparent pricing for athletes, trainers & gym owners",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentPadding = PaddingValues(bottom = 60.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(plans) { plan ->
                LuxuryCard(
                    borderBrush = Brush.horizontalGradient(
                        if (plan.isPopular) listOf(BrightGold, LuxuryRed)
                        else listOf(MetallicGold.copy(alpha = 0.3f), DarkDivider)
                    ),
                    backgroundColor = DarkCardElevated
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(plan.title, color = BrightGold, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                                Text(plan.subtitle, color = TextMuted, fontSize = 12.sp)
                            }
                            if (plan.isPopular) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(LuxuryRed)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text("MOST POPULAR", color = TextWhite, fontWeight = FontWeight.ExtraBold, fontSize = 10.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(plan.price, color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("/ ${plan.billingCycle}", color = TextMuted, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        plan.features.forEach { feat ->
                            Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(feat, color = TextLightGray, fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        GoldButton(
                            text = if (plan.price == "₹0") "Current Free Tier" else "Subscribe to ${plan.title}",
                            onClick = {
                                selectedPlanTitle = plan.title
                                showPaymentInfoDialog = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "subscribe_btn_${plan.id}"
                        )
                    }
                }
            }
        }
    }

    if (showPaymentInfoDialog) {
        AlertDialog(
            onDismissRequest = { showPaymentInfoDialog = false },
            containerColor = DarkCardElevated,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = BrightGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Payment Gateway Status", color = BrightGold, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Selected Plan: $selectedPlanTitle", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Payments are currently disabled in this deployment until a real payment gateway (such as Razorpay, Stripe, or Google Play Billing) is connected with production merchant credentials.",
                        color = TextLightGray,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "In Offline Demo Mode, all premium features, workouts, and gym owner tools remain 100% accessible for evaluation.",
                        color = SuccessGreen,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                GoldButton(text = "Understood", onClick = { showPaymentInfoDialog = false })
            }
        )
    }
}

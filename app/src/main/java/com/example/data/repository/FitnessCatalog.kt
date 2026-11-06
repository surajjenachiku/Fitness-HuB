package com.example.data.repository

import com.example.data.model.Exercise
import com.example.data.model.FitnessChallenge
import com.example.data.model.FoodItem
import com.example.data.model.GymDiscoveryItem
import com.example.data.model.IndianDietPlan
import com.example.data.model.WorkoutProgram
import com.example.data.model.YogaPose

object FitnessCatalog {

    val workoutPrograms: List<WorkoutProgram> = listOf(
        WorkoutProgram(
            id = "w_home_beg",
            title = "Zero Equipment Home Starter",
            subtitle = "Kickstart your fitness with foundational bodyweight movements",
            category = "Home / Beginner",
            durationMin = 20,
            difficulty = "Beginner",
            calories = 160,
            exercises = listOf(
                Exercise("e1", "Bodyweight Squats", 3, "15 reps", "Quadriceps & Glutes", 45, "Keep chest high, hips back, and knees tracking in line with toes.", "Push through heels."),
                Exercise("e2", "Incline Push-ups (Hands on Sofa/Table)", 3, "12 reps", "Chest & Triceps", 40, "Maintain straight line from head to heels, lower chest to edge.", "Engage your core."),
                Exercise("e3", "Glute Bridges", 3, "15 reps", "Glutes & Hamstrings", 35, "Lie on back with knees bent, squeeze glutes at the top hold 2s.", "Don't hyperextend lower back."),
                Exercise("e4", "Standing High Knees", 3, "40 secs", "Cardio & Core", 40, "Drive knees upwards rhythmically while pumping opposite arms.", "Land softly on balls of feet.")
            )
        ),
        WorkoutProgram(
            id = "w_fat_loss",
            title = "High Intensity Fat Shredder",
            subtitle = "Maximum calorie furnace combining explosive cardio & plyometrics",
            category = "Fat Loss",
            durationMin = 25,
            difficulty = "Intermediate",
            calories = 290,
            exercises = listOf(
                Exercise("fl1", "Burpees with Jump", 4, "12 reps", "Full Body & Cardio", 80, "Drop into squat, kick feet back to plank, return and jump explode.", "Keep pace continuous."),
                Exercise("fl2", "Mountain Climbers", 4, "45 secs", "Core & Shoulders", 60, "High plank posture, alternate driving knees towards chest rapidly.", "Keep hips down."),
                Exercise("fl3", "Jumping Lunges", 3, "16 reps (8/side)", "Quads, Glutes & Calves", 75, "Lunge down then power jump into the air switching lead legs.", "Control landing shock."),
                Exercise("fl4", "Plank Shoulder Taps", 3, "20 taps", "Core & Anti-Rotation", 45, "Hold rigid pushup position, alternate tapping opposite shoulder.", "Minimize hip sway.")
            )
        ),
        WorkoutProgram(
            id = "w_strength",
            title = "Upper Body & Core Power",
            subtitle = "Hypertrophy and strength builder for shoulders, chest & back",
            category = "Strength Training",
            durationMin = 30,
            difficulty = "Intermediate",
            calories = 240,
            exercises = listOf(
                Exercise("st1", "Standard Push-ups", 4, "15 reps", "Pectorals, Anterior Deltoids", 60, "Full range of motion, elbows at 45 degrees, lock out at top.", "Keep pelvis tucked."),
                Exercise("st2", "Diamond Push-ups", 3, "10 reps", "Triceps & Inner Chest", 50, "Index fingers and thumbs forming a triangle under sternum.", "Tuck elbows close to ribs."),
                Exercise("st3", "Pike Push-ups", 3, "12 reps", "Shoulders & Upper Chest", 55, "Hips high in downward dog shape, lower crown towards floor.", "Builds overhead strength."),
                Exercise("st4", "Hollow Body Hold", 3, "45 secs", "Deep Transverse Abdominis", 45, "Lower back pressed firm against floor, arms extended overhead.", "Breathe through diaphragm.")
            )
        ),
        WorkoutProgram(
            id = "w_no_equip",
            title = "Total Body No-Equipment Burn",
            subtitle = "Functional calisthenics workout you can perform anywhere",
            category = "No-Equipment",
            durationMin = 20,
            difficulty = "All Levels",
            calories = 210,
            exercises = listOf(
                Exercise("ne1", "Air Squats with 3s Pause", 4, "15 reps", "Legs & Core", 60, "Descend into deep squat, hold for 3 seconds before ascending.", "Builds isometric strength."),
                Exercise("ne2", "Reverse Lunges", 3, "12 reps/leg", "Quads & Stabilizers", 50, "Step backward, lower back knee until hover 1 inch off floor.", "Preserves knee health."),
                Exercise("ne3", "Bicycle Crunches", 3, "30 reps", "Obliques & Rectus Abdominis", 45, "Slow and controlled elbow-to-opposite-knee twists.", "Never pull behind neck."),
                Exercise("ne4", "Superman Arch Holds", 3, "15 reps", "Erector Spinae & Upper Back", 40, "Prone position, lift chest and thighs simultaneously.", "Squeeze shoulder blades.")
            )
        ),
        WorkoutProgram(
            id = "w_10min",
            title = "10-Minute Morning Ignition",
            subtitle = "Fast metabolic booster to energize your body before work",
            category = "10-Min Fast Plan",
            durationMin = 10,
            difficulty = "Beginner",
            calories = 110,
            exercises = listOf(
                Exercise("m10_1", "Jumping Jacks", 2, "60 secs", "Full Body Warmup", 30, "Wide arm arcs with crisp sync rhythm.", "Light on feet."),
                Exercise("m10_2", "Bodyweight Squat to Calf Raise", 2, "20 reps", "Lower Body", 35, "Squat deep, rise onto toes squeezing calves at top.", "Smooth tempo."),
                Exercise("m10_3", "Forearm Plank Hold", 2, "60 secs", "Core Stability", 25, "Elbows under shoulders, glutes clenched tight.", "Don't hold breath."),
                Exercise("m10_4", "Shadow Boxing Combos", 2, "60 secs", "Cardio & Coordination", 20, "Jab, cross, hook and slip combinations.", "Stay loose.")
            )
        ),
        WorkoutProgram(
            id = "w_20min",
            title = "20-Minute Core & Cardio Fusion",
            subtitle = "Balanced session sculpting abs while keeping heart rate elevated",
            category = "20-Min Plan",
            durationMin = 20,
            difficulty = "Intermediate",
            calories = 220,
            exercises = listOf(
                Exercise("m20_1", "Speed Skaters", 3, "45 secs", "Lateral Agility & Glutes", 60, "Leap laterally landing on outer foot, sweeping trailing leg.", "Soft knee bend."),
                Exercise("m20_2", "Russian Twists", 3, "30 reps", "Rotational Core", 50, "V-sit position, rotate shoulders side-to-side.", "Keep feet elevated."),
                Exercise("m20_3", "Bear Crawl Hold", 3, "45 secs", "Full Kinetic Chain", 55, "Hands and knees on all fours, lift knees 2 inches off floor.", "Flat neutral spine."),
                Exercise("m20_4", "Jump Squats", 3, "15 reps", "Explosive Power", 55, "Deep squat exploding directly into full vertical jump.", "Dampen landing.")
            )
        ),
        WorkoutProgram(
            id = "w_30min",
            title = "30-Minute Athlete Conditioning",
            subtitle = "Comprehensive full-body conditioning for peak endurance",
            category = "30-Min Plan",
            durationMin = 30,
            difficulty = "Advanced",
            calories = 340,
            exercises = listOf(
                Exercise("m30_1", "Walking Lunges with Torso Twist", 4, "20 paces", "Legs, Hip Flexors, Core", 80, "Lunge forward, rotate torso toward front knee.", "Maintain balance."),
                Exercise("m30_2", "Declined Feet-Elevated Push-ups", 4, "15 reps", "Upper Chest & Deltoids", 85, "Feet placed up on bed/bench, strict pushup form.", "Intense chest stimulus."),
                Exercise("m30_3", "Tuck Jumps", 4, "12 reps", "Maximal Plyometrics", 90, "Jump and snap knees directly up into chest.", "Full power each rep."),
                Exercise("m30_4", "Side Plank with Hip Dips", 3, "12 reps/side", "Lateral Core & Obliques", 50, "Dip hip toward floor and drive high above neutral.", "Keep shoulders stacked.")
            )
        )
    )

    val indianDietPlans: List<IndianDietPlan> = listOf(
        IndianDietPlan(
            id = "diet_veg_muscle",
            title = "Pure Vegetarian Muscle & Shred Plan",
            description = "High protein plant and dairy nutrition designed specifically for Indian vegetarian fitness enthusiasts looking to build lean muscle.",
            isVegetarian = true,
            dailyCalories = 2150,
            targetProteinG = 135,
            breakfast = listOf(
                "Masala Rolled Oats (60g) cooked in 250ml toned milk",
                "1 scoop Whey Protein Isolate or Plant Protein",
                "10 Soaked Almonds + 2 Walnuts + 1 tbsp Chia seeds"
            ),
            midMorningSnack = listOf(
                "Sprouted Moong & Kala Chana Chaat (1 bowl) with lemon, onion & tomato",
                "1 glass Tender Coconut Water or Buttermilk (Chaas)"
            ),
            lunch = listOf(
                "Paneer Bhurji / Grilled Paneer (150g) made in cold-pressed mustard oil",
                "2 Multigrain / Jowar Rotis",
                "Thick Tadka Dal (1 large katori) + Fresh Cucumber & Carrot Salad"
            ),
            eveningSnack = listOf(
                "Roasted Soya Chunks or Roasted Makhana (50g)",
                "Black Coffee or Ginger Green Tea without sugar"
            ),
            dinner = listOf(
                "Soyabean Wadi Curry or Tofu Stir-Fry (100g dry weight)",
                "1 Multigrain Roti or 1 cup Brown Rice",
                "Mixed Vegetable Sabzi (Palak/Methi/Bhindi) + 1 cup Curd (Dahi)"
            )
        ),
        IndianDietPlan(
            id = "diet_non_veg",
            title = "High-Protein Non-Vegetarian Athletic Plan",
            description = "Optimized macro distribution utilizing farm-fresh eggs, grilled chicken breast, and fish paired with complex Indian grains.",
            isVegetarian = false,
            dailyCalories = 2300,
            targetProteinG = 160,
            breakfast = listOf(
                "4 Whole Boiled Eggs (or 2 whole + 3 whites) with black pepper",
                "2 Slices Whole Wheat Toast with peanut butter (1 tbsp)",
                "1 Banana or Apple + Green Tea"
            ),
            midMorningSnack = listOf(
                "1 Glass Sattu Drink (30g roasted gram powder with lemon, jeera & mint)",
                "Handful of roasted peanuts (25g)"
            ),
            lunch = listOf(
                "Indian Spiced Grilled Chicken Breast (200g) or Fish Curry in light gravy",
                "1.5 cups Cooked Brown Rice or 2 Wheat Phulkas",
                "Green Salad (Beetroot, Onion, Tomato, Lemon) + Mint Chutney"
            ),
            eveningSnack = listOf(
                "3 Boiled Egg Whites sprinkled with chaat masala",
                "Black Coffee pre-workout + 1 Medjool Date"
            ),
            dinner = listOf(
                "Chicken Tikka / Keema (150g lean)",
                "1 bowl Yellow Moong Dal + Steamed broccoli and beans",
                "1 Multigrain Roti"
            )
        )
    )

    val indianProteinGuide: List<FoodItem> = listOf(
        FoodItem("Soya Chunks (Dry)", "सोया बड़ी", "100g", 345, 52.0f, 33.0f, 0.5f, true, "Soya"),
        FoodItem("Whey Protein Powder", "व्हे प्रोटीन", "1 scoop (32g)", 125, 25.0f, 2.0f, 1.5f, true, "Supplement"),
        FoodItem("Low-Fat Paneer", "पनीर", "100g", 210, 20.0f, 3.5f, 14.0f, true, "Dairy"),
        FoodItem("Chicken Breast (Boneless)", "चिकन ब्रेस्ट", "100g", 165, 31.0f, 0.0f, 3.6f, false, "Poultry"),
        FoodItem("Whole Boiled Egg", "उबला अंडा", "1 large (50g)", 78, 6.3f, 0.6f, 5.3f, false, "Eggs"),
        FoodItem("Egg Whites", "अंडे की सफेदी", "3 egg whites", 51, 10.8f, 0.7f, 0.2f, false, "Eggs"),
        FoodItem("Roasted Sattu", "भुना चना सत्तू", "100g", 385, 23.0f, 62.0f, 5.0f, true, "Grains"),
        FoodItem("Moong Sprouts", "अंकुरित मूंग", "100g", 105, 7.5f, 18.0f, 0.8f, true, "Legumes"),
        FoodItem("Greek Yogurt / Hung Curd", "चक्का दही", "100g", 95, 10.0f, 4.0f, 4.0f, true, "Dairy"),
        FoodItem("Roasted Peanuts (Moongfali)", "मूंगफली", "50g", 285, 13.0f, 8.0f, 24.0f, true, "Nuts"),
        FoodItem("Kala Chana (Boiled)", "काला चना", "100g", 164, 8.9f, 27.0f, 2.6f, true, "Legumes"),
        FoodItem("Yellow Moong Dal (Boiled)", "मूंग दाल", "1 bowl (150g)", 170, 11.5f, 28.0f, 1.2f, true, "Dal")
    )

    val yogaPoses: List<YogaPose> = listOf(
        YogaPose(
            id = "y_surya",
            englishName = "Sun Salutation Flow",
            sanskritName = "Surya Namaskar (12 Vinyasas)",
            durationSec = 300,
            difficulty = "Beginner to All",
            benefits = "Full body kinetic activation, stimulates cardiovascular circulation, spine flex, and gut metabolism.",
            steps = listOf(
                "Pranamasana (Prayer Pose) with centered breath.",
                "Hastauttanasana (Raised Arms Pose), inhale and arch backwards.",
                "Hastapadasana (Standing Forward Bend), exhale and touch toes.",
                "Ashwa Sanchalanasana (Equestrian Pose), right leg lunged back.",
                "Dandasana (Plank Pose) holding a solid straight spine line.",
                "Ashtanga Namaskara (Eight Limbed Salute) to Bhujangasana."
            )
        ),
        YogaPose(
            id = "y_cobra",
            englishName = "Cobra Pose",
            sanskritName = "Bhujangasana",
            durationSec = 60,
            difficulty = "Beginner",
            benefits = "Relieves stiffness in lower spine, opens lung cavity, strengthens core lumbar muscles.",
            steps = listOf(
                "Lie face down on yoga mat with palms placed beside shoulders.",
                "Press pubic bone into mat, inhale and gently elevate chest up.",
                "Roll shoulder blades back and down away from ears.",
                "Keep a micro-bend in elbows and hold for 5 slow deep breaths."
            )
        ),
        YogaPose(
            id = "y_warrior",
            englishName = "Warrior II Pose",
            sanskritName = "Virabhadrasana II",
            durationSec = 90,
            difficulty = "Intermediate",
            benefits = "Fosters mental grit, builds quadriceps and groin stamina, strengthens shoulder girdle.",
            steps = listOf(
                "Step feet wide apart, turn front foot 90 degrees outward.",
                "Bend front knee to right angle directly above front ankle.",
                "Extend arms parallel to floor, gaze serenely over front middle finger.",
                "Maintain rooted pelvic stability and hold for 30s each side."
            )
        )
    )

    val gymDiscoveryList: List<GymDiscoveryItem> = listOf(
        GymDiscoveryItem(
            id = "gym_1",
            name = "Olympia Gold Elite Fitness Club",
            cityArea = "Indiranagar, 100ft Road",
            rating = 4.9f,
            reviewCount = 428,
            monthlyPrice = 2200,
            distanceKm = 1.2f,
            amenities = listOf("Olympic Free Weights", "CrossFit Rig", "Sauna & Steam", "Certified Nutritionist", "Locker & Showers"),
            openHours = "05:30 AM - 11:00 PM",
            phone = "+91 80 4123 9900"
        ),
        GymDiscoveryItem(
            id = "gym_2",
            name = "Iron Temple Powerlifting & Gym",
            cityArea = "Koramangala 4th Block",
            rating = 4.8f,
            reviewCount = 315,
            monthlyPrice = 1800,
            distanceKm = 2.8f,
            amenities = listOf("Eleiko Competition Barbells", "Deadlift Platforms", "Chalk Allowed", "Air Conditioning"),
            openHours = "06:00 AM - 10:30 PM",
            phone = "+91 80 4455 6677"
        ),
        GymDiscoveryItem(
            id = "gym_3",
            name = "Aura Wellness, Yoga & Strength",
            cityArea = "HSR Layout Sector 2",
            rating = 4.7f,
            reviewCount = 192,
            monthlyPrice = 1950,
            distanceKm = 3.5f,
            amenities = listOf("Hatha & Vinyasa Studio", "Cardio Deck", "Physiotherapy Room", "Valet Parking"),
            openHours = "06:00 AM - 09:30 PM",
            phone = "+91 80 2233 4455"
        )
    )

    val fitnessChallenges: List<FitnessChallenge> = listOf(
        FitnessChallenge("ch_1", "30-Day Shred Challenge", "Burn body fat and achieve a visible core taper with daily HIIT assignments.", 30, 1420, 14, true, 500),
        FitnessChallenge("ch_2", "10,000 Steps Daily Discipline", "Hit the golden daily step count to boost non-exercise activity thermogenesis.", 21, 2850, 9, true, 350),
        FitnessChallenge("ch_3", "Hydration Hero (3.5L Daily)", "Consistent cellular hydration to elevate recovery and skin vitality.", 14, 980, 11, true, 200),
        FitnessChallenge("ch_4", "Century Pushup Milestone", "Gradually build up to 100 consecutive pushups with structured rest pauses.", 30, 740, 4, false, 400)
    )
}

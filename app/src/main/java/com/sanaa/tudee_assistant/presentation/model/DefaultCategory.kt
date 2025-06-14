package com.sanaa.tudee_assistant.presentation.model

import com.sanaa.tudee_assistant.R

enum class DefaultCategory(
    title: String,
    iconResource: Int
) {
    EDUCATION("Education", R.drawable.education_cat),
    SHOPPING("Shopping", R.drawable.shopping_cat),
    MEDICAL("Medical", R.drawable.medical_cat),
    GYM("Gym", R.drawable.gym_cat),
    ENTERTAINMENT("Entertainment", R.drawable.entertainment_cat),
    EVENT("Event", R.drawable.event_cat),
    WORK("Work", R.drawable.work_cat),
    BUDGETING("Budgeting", R.drawable.budgeting_cat),
    SELF_CARE("Self-care", R.drawable.self_care_cat),
    ADORATION("Adoration", R.drawable.adoration_cat),
    FIXING_BUGS("Fixing bugs", R.drawable.fixing_bugs_cat),
    CLEANING("Cleaning", R.drawable.cleaning_cat),
    TRAVELING("Traveling", R.drawable.traveling_cat),
    AGRICULTURE("Agriculture", R.drawable.agriculture_cat),
    CODING("Coding", R.drawable.coding_cat),
    COOKING("Cooking", R.drawable.cooking_cat),
    FAMILY_FRIEND("Family & friend", R.drawable.family_friend_cat)
}

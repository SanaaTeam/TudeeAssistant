package com.sanaa.tudee_assistant.data.utils

import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto

fun getDefaultCategories(): List<CategoryLocalDto> {
    return listOf(
        CategoryLocalDto(
            name = "Adoration",
            imagePath = "file:///android_asset/categories/adoration.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Agriculture",
            imagePath = "file:///android_asset/categories/agriculture.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Budgeting",
            imagePath = "file:///android_asset/categories/budgeting.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Cleaning",
            imagePath = "file:///android_asset/categories/cleaning.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Coding",
            imagePath = "file:///android_asset/categories/coding.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Cooking",
            imagePath = "file:///android_asset/categories/cooking.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Education",
            imagePath = "file:///android_asset/categories/education.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Entertainment",
            imagePath = "file:///android_asset/categories/entertainment.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Event",
            imagePath = "file:///android_asset/categories/event.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Family & Friends",
            imagePath = "file:///android_asset/categories/family-friend.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Fixing Bugs",
            imagePath = "file:///android_asset/categories/fixing-bugs.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Gym",
            imagePath = "file:///android_asset/categories/gym.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Medical",
            imagePath = "file:///android_asset/categories/medical.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Self Care",
            imagePath = "file:///android_asset/categories/self-care.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Shopping",
            imagePath = "file:///android_asset/categories/shopping.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Traveling",
            imagePath = "file:///android_asset/categories/traveling.png",
            isDefault = true
        ),
        CategoryLocalDto(
            name = "Work",
            imagePath = "file:///android_asset/categories/work.png",
            isDefault = true
        )
    )
}
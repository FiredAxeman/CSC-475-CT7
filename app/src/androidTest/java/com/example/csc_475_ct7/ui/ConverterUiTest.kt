package com.example.csc_475_ct7.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.csc_475_ct7.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ConverterUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testConversionFlow() {
        // Find input field and enter value
        // Using onNode(hasText("Input Value")) or similar since onNodeWithLabel failed
        composeTestRule.onNodeWithText("Input Value").performTextInput("100")

        // Check if result is displayed (Celsius to Fahrenheit: 100 -> 212)
        composeTestRule.onNodeWithText("212 °F", substring = true).assertExists()
    }

    @Test
    fun testCategorySwitch() {
        // Click on Length category
        composeTestRule.onNodeWithText("Length").performClick()

        // Verify we are in Length conversion (Meter to Kilometer)
        composeTestRule.onNodeWithText("Meter").assertExists()
        composeTestRule.onNodeWithText("Kilometer").assertExists()
    }
}

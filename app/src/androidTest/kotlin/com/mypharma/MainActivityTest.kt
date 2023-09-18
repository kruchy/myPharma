package com.mypharma


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.j256.ormlite.dao.Dao
import com.kruchy.mypharma.R
import com.mypharma.database.DatabaseHelper
import com.mypharma.model.Drug
import com.mypharma.model.Reminder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testBottomNavigationViewInteraction() {


        onView(withId(R.id.remindersRecyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_add_reminders)).perform(click())
        onView(withId(R.id.autoCompleteTextView)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_calendar)).check(matches(isDisplayed()))
    }
}

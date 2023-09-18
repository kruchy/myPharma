package com.mypharma

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kruchy.mypharma.R
import com.kruchy.mypharma.databinding.ActivityMainBinding
import com.mypharma.database.DatabaseHelper
import com.mypharma.database.IDatabaseHelper
import com.mypharma.model.DrugView
import com.mypharma.ui.notifications.CalendarFragment
import com.mypharma.ui.reminder.AddReminderBottomSheet
import com.mypharma.ui.reminder.RemindersFragment
import com.mypharma.ui.reminder.RemindersViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: IDatabaseHelper
    private lateinit var binding: ActivityMainBinding
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper(this@MainActivity)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.apply {
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_reminders))
            setupActionBarWithNavController(navController, appBarConfiguration)
            setupWithNavController(navController)

            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_add_reminders -> {
                        val bottomSheet = AddReminderBottomSheet()
                        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                        true
                    }

                    R.id.navigation_reminders -> {
                        replaceFragment(RemindersFragment())
                        true
                    }

                    R.id.navigation_calendar -> {
                        replaceFragment(CalendarFragment())
                        true
                    }

                    else -> false
                }
            }
        }
    }

    fun setDatabaseHelper(helper: IDatabaseHelper) {
        this.dbHelper = helper
    }

    fun getDatabaseHelper(): IDatabaseHelper {
        if (!::dbHelper.isInitialized) {
            dbHelper = DatabaseHelper(this@MainActivity)
        }
        return dbHelper
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_main, fragment)
            setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
    }
}

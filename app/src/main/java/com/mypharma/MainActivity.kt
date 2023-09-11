package com.mypharma

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kruchy.mypharma.R
import com.kruchy.mypharma.databinding.ActivityMainBinding
import com.mypharma.registry.DatabaseHelper
import com.mypharma.ui.reminder.AddReminderBottomSheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var TAG: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = this.localClassName;
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_reminders,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val dbExists = checkDatabaseExists()

        if (!dbExists) {
            Log.d(TAG, "onCreate: Creating database")
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val dbHelper = DatabaseHelper(
                        this@MainActivity,
                        this@MainActivity.assets.open("Registry.csv")
                    )
                    val db = dbHelper.writableDatabase
                }
                Log.d(TAG, "onCreate: Database created")
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_add_reminders -> {
                    val bottomSheet = AddReminderBottomSheet()
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                    true
                }

                else -> false
            }
        }
    }

    private fun checkDatabaseExists(): Boolean {
        val dbFile = applicationContext.getDatabasePath(DatabaseHelper.DATABASE_NAME)
        return dbFile.exists()
    }
}
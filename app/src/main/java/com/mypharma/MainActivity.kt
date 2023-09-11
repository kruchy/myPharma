package com.mypharma

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kruchy.mypharma.R
import com.kruchy.mypharma.databinding.ActivityMainBinding
import com.mypharma.registry.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var TAG = this.localClassName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home,))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val dbExists = checkDatabaseExists()

        if (!dbExists) {
            Log.d(TAG, "onCreate: Creating database")
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val dbHelper = DatabaseHelper(this@MainActivity)
                    val db = dbHelper.writableDatabase
                }
                Log.d(TAG, "onCreate: Database created")
            }
        }
    }
    private fun checkDatabaseExists(): Boolean {
        val dbFile = applicationContext.getDatabasePath(DatabaseHelper.DATABASE_NAME)
        return dbFile.exists()
    }
}
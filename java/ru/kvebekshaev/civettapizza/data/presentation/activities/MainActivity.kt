package ru.kvebekshaev.civettapizza.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.kvebekshaev.civettapizza.R
import ru.kvebekshaev.civettapizza.databinding.ActivityMainBinding
import ru.kvebekshaev.civettapizza.presentation.Interfaces.NavigationVisibilityController

class MainActivity : AppCompatActivity(), NavigationVisibilityController {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.main) as NavHostFragment
        bottomNav = binding.bottomNavigation
        navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainClientFragment,
                R.id.mapFragment,
                R.id.accountFragment,
                R.id.owlcoinFragment,
                R.id.orderClientFragment
            )
        )
        setContentView(view)
        window.statusBarColor = ContextCompat.getColor(this, R.color.second_accent)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
    }

    override fun setNavigationVisibility(isVisible: Boolean) {
        bottomNav.visibility = if(isVisible) View.VISIBLE else View.GONE
    }
}
package llc.amplitudo.amplitudo_akademija.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import llc.amplitudo.amplitudo_akademija.R
import llc.amplitudo.amplitudo_akademija.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("Activity is being created...")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        /**
         * Changes color of status bar.
         */
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.purple)
        initNavigation()
    }

    override fun onStart() {
        super.onStart()
        Timber.d("Activity is starting...")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("Activity is resuming...")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("Activity is being paused...")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("Activity is stopping...")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d("Activity is restarting...")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("Activity is being destroyed...")
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = binding.bottomNavigation
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val addTaskFragmentId = R.id.addTaskFragment
            if (destination.id == addTaskFragmentId) binding.bottomBar.isInvisible = true
            else binding.bottomBar.isVisible = true
        }
        bottomNavigationView.setupWithNavController(navController)
    }


}


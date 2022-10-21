package de.heimbrodt.takeabike

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.ui.navigateUp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import de.heimbrodt.takeabike.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var b: Location



    companion object {
        public lateinit var fusedLocationClient: FusedLocationProviderClient
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //========================================================================
        if(!TakeABike.fLCinitialized) {

        }
        MainActivity.fusedLocationClient = getFusedLocationProviderClient(this)
        TakeABike.fLCinitialized = true
        Toast.makeText(this, TakeABike.fLCinitialized.toString(), 10)
        //Log.d(TakeABike.fLCinitialized.toString(), TakeABike.fLCinitialized.toString())
        binding.maxSpeedTextView.text = "0.0"
        TakeABike.setTABBinding(binding)
        binding.buttonProfile.setOnClickListener {
            val x = ProfileActivity()
            x.setContext(this)
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        //========================================================================
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
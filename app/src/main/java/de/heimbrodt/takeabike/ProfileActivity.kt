package de.heimbrodt.takeabike

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import de.heimbrodt.tabplayer.TABPlayer
import de.heimbrodt.takeabike.databinding.ActivityMainBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var main: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val mainButton = findViewById<Button>(resources.getIdentifier("mainButton", "id", packageName))
        mainButton.text = TakeABike.p.getPlayer().exp.toString()
        mainButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    fun setContext(main: MainActivity) {
        this.main = main
    }
}
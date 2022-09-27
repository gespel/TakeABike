package de.heimbrodt.takeabike

import android.app.Application
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import de.heimbrodt.takeabike.databinding.ActivityMainBinding
import java.net.Socket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


public class TakeABike : Application() {
    private lateinit var a: Location
    override fun onCreate() {
        super.onCreate()
        TakeABike.startMainHandler()
        TakeABike.startNetworkHandler()
    }
    companion object {
        var fLCinitialized = false
        var distance = 0.0
        var maxSpeed = 0.0f
        var p: TABPlayer = TABPlayer()
        lateinit var binding: ActivityMainBinding
        lateinit var a: Location

        public fun setTABBinding(binding: ActivityMainBinding) {
            this.binding = binding
            p.setTABBinding(Companion.binding)
        }

        public fun startNetworkHandler() {
            val networkHandlerThread = HandlerThread("NT")
            networkHandlerThread.start()
            var networkHandler = Handler(networkHandlerThread.looper)
            networkHandler.post(object : Runnable {
                override fun run() {
                    try {
                        val client = Socket("192.168.2.34", 9999)
                        client.outputStream.write("Hello from the client!".toByteArray())
                        client.getInputStream().read()
                        client.close()
                    }
                    catch (e: java.lang.Exception) {
                        //Toast.makeText(null, "Bla", 3)
                    }
                    networkHandler.postDelayed(this, 5000)
                }
            })
        }

        public fun startMainHandler() {

            val mainHandlerThread = HandlerThread("HT")
            mainHandlerThread.start()
            var mainHandler = Handler(mainHandlerThread.looper)
            var x: Int = 0

            if(fLCinitialized) {
                MainActivity.fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        a = location
                    }

                }
            }

            mainHandler.post(object : Runnable {
                override fun run() {
                    if(fLCinitialized) {
                        MainActivity.fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
                            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                            override fun isCancellationRequested() = false
                        })
                            .addOnSuccessListener { location: Location? ->
                                if (location == null)
                                else {
                                    if(::a.isInitialized) {
                                        if(location.distanceTo(a) > maxSpeed) {
                                            maxSpeed = location.distanceTo(a)
                                            binding.maxSpeedTextView.text = "Max Speed: " + maxSpeed + " m/s"
                                        }
                                        binding.msTextView.text = "Speed: " + location.distanceTo(a).toString() +" m/s"
                                        binding.latitudeTextView.text = "Latitude: " + location.latitude.toString()
                                        binding.longitudeTextView.text = "Logitude: " + location.longitude.toString()
                                        binding.timeTextView.text = "Time: " + LocalDateTime.now().format(
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                        )
                                        distance += location.distanceTo(a)
                                        TakeABike.p.tickExp(location.distanceTo(a))
                                        binding.distanceTextView.text = "Distance: $distance m"
                                        binding.counterTextView.text = x.toString()
                                        x++
                                    }
                                    a = location
                                }
                            }

                    }
                    mainHandler.postDelayed(this, 1000)
                }
            })
        }
    }

}
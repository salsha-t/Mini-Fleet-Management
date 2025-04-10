package com.portfolio.minifleetmanagement

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.commit
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.portfolio.minifleetmanagement.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var allLatLng = ArrayList<LatLng>()
    private var boundsBuilder = LatLngBounds.Builder()

    // Handler dan Runnable untuk simulasi lokasi dengan hardcoded
    private val hardcodedCoordinates = listOf(
        LatLng(-6.175392, 106.827153),
        LatLng(-6.177000, 106.830000),
        LatLng(-6.179000, 106.833000),
        LatLng(-6.182000, 106.836000),
        LatLng(-6.185000, 106.840000),
        LatLng(-6.189000, 106.845000),
        LatLng(-6.193000, 106.850000),
        LatLng(-6.198000, 106.855000),
        LatLng(-6.203000, 106.860000),
        LatLng(-6.210000, 106.865000),
        LatLng(-6.220000, 106.870000),
        LatLng(-6.235000, 106.880000),
        LatLng(-6.250000, 106.885000),
        LatLng(-6.278044, 106.886741),
        LatLng(-6.301870220205886, 106.89065766600079)
    )

    private var hardcodedIndex = 0
    private val simulationHandler = Handler(Looper.getMainLooper())
    private lateinit var simulationRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        supportFragmentManager.commit {
            add(R.id.dashboard, DashboardFragment(), DashboardFragment::class.java.simpleName)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getFirstLocationHardcoded()
        startSimulatedLocationUpdates()

    }

    // Fungsi ini menampilkan titik hardcoded pertama
    private fun getFirstLocationHardcoded() {
        val startLocation = hardcodedCoordinates.first()
        showMarker(startLocation)
    }

    // Fungsi ini menampilkan titik hardcoded terakhir
    private fun getLastLocationHardcoded() {
        val lastLocation = hardcodedCoordinates.last()
        showMarker(lastLocation)
    }

    private fun showMarker(latLng: LatLng) {
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
        )
    }

    private fun startSimulatedLocationUpdates() {
        simulationRunnable = object : Runnable {
            override fun run() {
                // Ambil koordinat simulasi saat ini
                val simulatedLatLng = hardcodedCoordinates[hardcodedIndex]

                // Tambahkan titik ke polyline dan bounds
                allLatLng.add(simulatedLatLng)
                mMap.addPolyline(
                    PolylineOptions()
                        .color(Color.CYAN)
                        .width(10f)
                        .addAll(allLatLng)
                )
                boundsBuilder.include(simulatedLatLng)
                val bounds: LatLngBounds = boundsBuilder.build()
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 64))

                // Periksa apakah masih bisa mendapatkan titik berikutnya
                if (hardcodedIndex < hardcodedCoordinates.size - 1) {
                    hardcodedIndex++
                    simulationHandler.postDelayed(this, 5000)
                } else {
                    // Jika sudah mencapai titik terakhir, hentikan update
                    stopSimulatedLocationUpdates()
                    getLastLocationHardcoded()
                }
            }
        }
        simulationHandler.post(simulationRunnable)
    }

    private fun stopSimulatedLocationUpdates() {
        simulationHandler.removeCallbacks(simulationRunnable)
    }
}
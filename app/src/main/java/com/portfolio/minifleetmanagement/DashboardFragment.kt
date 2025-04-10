package com.portfolio.minifleetmanagement

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.fragment.app.viewModels
import com.portfolio.minifleetmanagement.databinding.FragmentDashboardBinding
import android.Manifest

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var index = 0

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(requireContext(), "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Restore index jika tersedia
        index = savedInstanceState?.getInt("INDEX_KEY") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setupViewModel()

        runnable = object : Runnable {
            override fun run() {

                if (index < 5) {
                    // Lakukan pemanggilan API
                    viewModel.getSpeed()
                    viewModel.getDoorStatus()
                    viewModel.getEngineStatus()
                    index++

                    // Jadwalkan pemanggilan selanjutnya setelah 15 detik
                    handler.postDelayed(this, 15000)
                }
            }
        }
        handler.post(runnable) // Panggilan awal
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("INDEX_KEY", index)
    }

    private fun setupViewModel(){
        viewModel.speedData.observe(viewLifecycleOwner){ data ->
            binding.tvSpeed.text = getString(R.string.speed_value, data.value)
        }

        viewModel.doorStatusData.observe(viewLifecycleOwner){ data ->
            binding.tvDoor.text = getString(R.string.door_value, data.value)
        }

        viewModel.engineStatusData.observe(viewLifecycleOwner){ data ->
            binding.tvEngine.text = getString(R.string.engine_value, data.value)
        }

        viewModel.alertEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { alertMessage ->
                showAlert(alertMessage)
                sendNotification(alertMessage)
            }
        }
    }

    private fun sendNotification(message: String) {
        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("Warning")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }


    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Warning")
        builder.setMessage(message)

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        builder.setCancelable(true)
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
        _binding = null
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "Fleet channel"
    }
}
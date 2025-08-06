package com.test.mytest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.mytest.databinding.ActivityMainBinding
import com.test.mytest.viewmodel.ModuleViewModel
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ModuleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvModules.layoutManager = LinearLayoutManager(this)
        viewModel.modules.observe(this)  { moduleList ->
            binding.rvModules.adapter = ModulesAdapter(moduleList) { module ->
                val message = viewModel.checkModuleAccess(module)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.coolingMessage.observe(this) { message ->
            if (message.isNotEmpty()) {
                binding.tvCoolingMessage.visibility = View.VISIBLE
                binding.tvCoolingMessage.text = message
            } else {
                binding.tvCoolingMessage.visibility = View.GONE
            }
        }
    }
}

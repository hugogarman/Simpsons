package com.example.simpsons.features.simpsons.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpsons.databinding.ActivitySimpsonsListBinding
import com.example.simpsons.features.simpsons.data.SimpsonsDataRepository
import com.example.simpsons.features.simpsons.data.remote.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.domain.GetSimpsonsUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class SimpsonsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpsonsListBinding
    private lateinit var viewModel: SimpsonsListViewModel
    private lateinit var adapter: SimpsonsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpsonsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupViewModel()
        observeUiState()
        viewModel.loadSimpsons()
    }

    private fun setupViews() {
        adapter = SimpsonsAdapter { simpson ->
            // Intent simplificado sin constantes
            val intent = Intent(this, SimpsonDetailActivity::class.java)
            intent.putExtra("SIMPSON_NAME", simpson.name)
            intent.putExtra("SIMPSON_AGE", simpson.age)
            intent.putExtra("SIMPSON_OCCUPATION", simpson.occupation)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        val httpClient = HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }

        val remoteDataSource = SimpsonsApiRemoteDataSource(httpClient)
        val repository = SimpsonsDataRepository(remoteDataSource)
        val useCase = GetSimpsonsUseCase(repository)

        viewModel = SimpsonsListViewModel(useCase)
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is SimpsonsUiState.Success -> {
                        adapter.updateSimpsons(state.simpsons)
                    }

                    is SimpsonsUiState.Failure -> {  // ← Cambié "Failure" por "Error"
                        Toast.makeText(
                            this@SimpsonsListActivity,
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is SimpsonsUiState.Loading -> {
                        // Aquí podrías mostrar un ProgressBar si lo tuvieras
                        // binding.progressBar.visibility = View.VISIBLE
                    }

                    is SimpsonsUiState.Idle -> {
                        // Estado inicial, no hacer nada
                    }
                }
            }
        }
    }
}
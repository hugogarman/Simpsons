package com.example.simpsons.features.simpsons.presentation

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.simpsons.R
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
    private lateinit var viewModel: SimpsonsListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTitle: TextView
    private lateinit var adapter: SimpsonsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simpsons_list)

        setupViews()
        setupViewModel()
        observeUiState()
        viewModel.loadSimpsons()
    }

    private fun setupViews() {
        recyclerView = findViewById(R.id.recyclerView)
        tvTitle = findViewById(R.id.tvTitle)

        adapter = SimpsonsAdapter { simpson ->
            Toast.makeText(this, "Elegiste a ${simpson.name}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        // Configurar HttpClient de Ktor
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

                    is SimpsonsUiState.Failure -> {
                        Toast.makeText(
                            this@SimpsonsListActivity,
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {
                        // Idle y Loading: no hacemos nada por ahora
                    }
                }
            }
        }
    }
}
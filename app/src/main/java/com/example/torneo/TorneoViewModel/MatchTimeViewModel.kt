package com.example.torneo.TorneoViewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MatchTimeViewModel: ViewModel() {
    val currentMatchTime = MutableStateFlow(0)
}
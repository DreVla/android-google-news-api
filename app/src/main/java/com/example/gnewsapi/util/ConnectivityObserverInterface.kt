package com.example.gnewsapi.util

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserverInterface {
    val isConnected: Flow<Boolean>
}
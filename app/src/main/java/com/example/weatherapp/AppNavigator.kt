package com.example.weatherapp

import com.example.weatherapp.api.DailyForecast

interface AppNavigator {
    fun navigateToCurrentForecast(zipcode: String)
    fun navigateToLocationEntry()
    fun navigateToForecastDetails(forecast: DailyForecast)
}
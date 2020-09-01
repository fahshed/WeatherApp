package com.example.weatherapp.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ForecastRepository
import com.example.weatherapp.Location
import com.example.weatherapp.LocationRepository
import com.example.weatherapp.api.CurrentWeather
import java.lang.IllegalArgumentException

class CurrentForecastViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val locationRepository: LocationRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrentForecastViewModel::class.java)) {
            return CurrentForecastViewModel(forecastRepository, locationRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

class CurrentForecastViewModel(
    private val forecastRepository: ForecastRepository,
    private val locationRepository: LocationRepository
): ViewModel() {
    val currentWeather: LiveData<CurrentWeather> = forecastRepository.currentWeather
    val savedLocation: LiveData<Location> = locationRepository.savedLocation

    fun loadCurrentForecast(zipcode: String) {
        forecastRepository.loadCurrentForecast(zipcode)
    }
}
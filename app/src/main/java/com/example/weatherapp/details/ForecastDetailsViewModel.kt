package com.example.weatherapp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

class ForecastDetailsViewModelFactory(private val args: ForecastDetailsFragmentArgs): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ForecastDetailsViewModel::class.java)) {
            return ForecastDetailsViewModel(args) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

class ForecastDetailsViewModel(private val args: ForecastDetailsFragmentArgs): ViewModel() {
    private val _viewState: MutableLiveData<ForecastDetailsViewState> = MutableLiveData()
    val viewState: LiveData<ForecastDetailsViewState> = _viewState

    init {
        _viewState.value = ForecastDetailsViewState(
            args.temp,
            args.description,
            DATE_FORMAT.format(Date(args.date * 1000)),
            "http://openweathermap.org/img/wn/${args.icon}@2x.png"
        )
    }
}
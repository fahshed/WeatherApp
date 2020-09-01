package com.example.weatherapp.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.*
import com.example.weatherapp.api.CurrentWeather
import com.example.weatherapp.databinding.FragmentCurrentForecastBinding

class CurrentForecastFragment: Fragment() {
    private var _binding: FragmentCurrentForecastBinding? = null
    private val binding get() = _binding!!

    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    private lateinit var viewModelFactory: CurrentForecastViewModelFactory
    private val viewModel: CurrentForecastViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentForecastBinding.inflate(inflater, container, false)
        locationRepository = LocationRepository(requireContext())
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        viewModelFactory = CurrentForecastViewModelFactory(forecastRepository, locationRepository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            binding.emptyText.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.locationName.visibility = View.VISIBLE
            binding.tempText.visibility = View.VISIBLE

            binding.locationName.text = weather.name
            binding.tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
        }
        viewModel.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        val savedLocationObserver = Observer<Location> { savedLocation ->
            when(savedLocation) {
                is Location.Zipcode -> {
                    binding.progressBar.visibility = View.VISIBLE
                    viewModel.loadCurrentForecast(savedLocation.zipcode)
                }
            }
        }
        viewModel.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        binding.locationEntryButton.setOnClickListener {
            showLocationEntry()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLocationEntry() {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }
}
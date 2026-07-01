package com.example.csc_475_ct7.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.csc_475_ct7.data.local.entity.ConversionHistoryEntity
import com.example.csc_475_ct7.data.repository.HistoryRepository
import com.example.csc_475_ct7.domain.model.ConversionCategory
import com.example.csc_475_ct7.domain.model.UnitType
import com.example.csc_475_ct7.domain.usecase.ConvertUnitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

data class ConverterUiState(
    val inputValue: String = "1",
    val outputValue: String = "",
    val category: ConversionCategory = ConversionCategory.TEMPERATURE,
    val fromUnit: UnitType = UnitType.Temperature.Celsius,
    val toUnit: UnitType = UnitType.Temperature.Fahrenheit,
    val availableUnits: List<UnitType> = emptyList()
)

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertUnitsUseCase: ConvertUnitsUseCase,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConverterUiState())
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    val history: StateFlow<List<ConversionHistoryEntity>> = historyRepository.getAllHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        updateAvailableUnits(ConversionCategory.TEMPERATURE)
    }

    fun onInputChange(input: String) {
        if (input.isEmpty()) {
            _uiState.update { it.copy(inputValue = "", outputValue = "") }
            return
        }
        
        if (input.toDoubleOrNull() == null && input != "-" && input != ".") return

        _uiState.update { it.copy(inputValue = input) }
        performConversion()
    }

    fun onCategoryChange(category: ConversionCategory) {
        _uiState.update { it.copy(category = category) }
        updateAvailableUnits(category)
        performConversion()
    }

    fun onFromUnitChange(unit: UnitType) {
        _uiState.update { it.copy(fromUnit = unit) }
        performConversion()
    }

    fun onToUnitChange(unit: UnitType) {
        _uiState.update { it.copy(toUnit = unit) }
        performConversion()
    }

    fun onSwapUnits() {
        _uiState.update {
            it.copy(
                fromUnit = it.toUnit,
                toUnit = it.fromUnit,
                inputValue = it.outputValue
            )
        }
        performConversion()
    }

    fun saveToHistory() {
        val state = _uiState.value
        if (state.outputValue.isEmpty() || state.outputValue == "Error") return

        viewModelScope.launch {
            historyRepository.insertHistory(
                ConversionHistoryEntity(
                    inputValue = state.inputValue,
                    inputUnit = state.fromUnit.abbreviation,
                    outputValue = state.outputValue,
                    outputUnit = state.toUnit.abbreviation,
                    category = state.category.name
                )
            )
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            historyRepository.clearHistory()
        }
    }

    private fun updateAvailableUnits(category: ConversionCategory) {
        val units = when (category) {
            ConversionCategory.TEMPERATURE -> listOf(
                UnitType.Temperature.Celsius,
                UnitType.Temperature.Fahrenheit,
                UnitType.Temperature.Kelvin
            )
            ConversionCategory.LENGTH -> listOf(
                UnitType.Length.Meter,
                UnitType.Length.Kilometer,
                UnitType.Length.Centimeter,
                UnitType.Length.Millimeter,
                UnitType.Length.Inch,
                UnitType.Length.Foot,
                UnitType.Length.Yard,
                UnitType.Length.Mile
            )
            ConversionCategory.WEIGHT -> listOf(
                UnitType.Weight.Gram,
                UnitType.Weight.Kilogram,
                UnitType.Weight.Milligram,
                UnitType.Weight.Ounce,
                UnitType.Weight.Pound
            )
            ConversionCategory.VOLUME -> listOf(
                UnitType.Volume.Liter,
                UnitType.Volume.Milliliter,
                UnitType.Volume.Gallon,
                UnitType.Volume.Quart,
                UnitType.Volume.Pint,
                UnitType.Volume.Cup
            )
            ConversionCategory.SPEED -> listOf(
                UnitType.Speed.KmH,
                UnitType.Speed.Mph,
                UnitType.Speed.MS,
                UnitType.Speed.Knot
            )
            ConversionCategory.TIME -> listOf(
                UnitType.Time.Second,
                UnitType.Time.Minute,
                UnitType.Time.Hour,
                UnitType.Time.Day,
                UnitType.Time.Week
            )
        }
        
        _uiState.update {
            it.copy(
                availableUnits = units,
                fromUnit = units[0],
                toUnit = units[1]
            )
        }
    }

    private fun performConversion() {
        val state = _uiState.value
        val input = state.inputValue.toDoubleOrNull() ?: return
        
        try {
            val result = convertUnitsUseCase(input, state.fromUnit, state.toUnit)
            val formattedResult = if (result % 1.0 == 0.0) {
                result.toLong().toString()
            } else {
                String.format(Locale.US, "%.4f", result).trimEnd('0').trimEnd('.')
            }
            _uiState.update { it.copy(outputValue = formattedResult) }
        } catch (e: Exception) {
            _uiState.update { it.copy(outputValue = "Error") }
        }
    }
}

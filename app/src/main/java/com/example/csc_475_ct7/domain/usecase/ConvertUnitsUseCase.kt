package com.example.csc_475_ct7.domain.usecase

import com.example.csc_475_ct7.domain.model.UnitType
import javax.inject.Inject

class ConvertUnitsUseCase @Inject constructor() {

    operator fun invoke(value: Double, from: UnitType, to: UnitType): Double {
        return when {
            from is UnitType.Temperature && to is UnitType.Temperature -> convertTemperature(value, from, to)
            from is UnitType.Length && to is UnitType.Length -> convertLength(value, from, to)
            from is UnitType.Weight && to is UnitType.Weight -> convertWeight(value, from, to)
            else -> throw IllegalArgumentException("Units must be of the same category")
        }
    }

    private fun convertTemperature(value: Double, from: UnitType.Temperature, to: UnitType.Temperature): Double {
        val celsius = when (from) {
            UnitType.Temperature.Celsius -> value
            UnitType.Temperature.Fahrenheit -> (value - 32) * 5 / 9
            UnitType.Temperature.Kelvin -> value - 273.15
        }

        return when (to) {
            UnitType.Temperature.Celsius -> celsius
            UnitType.Temperature.Fahrenheit -> (celsius * 9 / 5) + 32
            UnitType.Temperature.Kelvin -> celsius + 273.15
        }
    }

    private fun convertLength(value: Double, from: UnitType.Length, to: UnitType.Length): Double {
        val meters = when (from) {
            UnitType.Length.Meter -> value
            UnitType.Length.Kilometer -> value * 1000
            UnitType.Length.Centimeter -> value * 0.01
            UnitType.Length.Millimeter -> value * 0.001
            UnitType.Length.Inch -> value * 0.0254
            UnitType.Length.Foot -> value * 0.3048
            UnitType.Length.Yard -> value * 0.9144
            UnitType.Length.Mile -> value * 1609.344
        }

        return when (to) {
            UnitType.Length.Meter -> meters
            UnitType.Length.Kilometer -> meters / 1000
            UnitType.Length.Centimeter -> meters / 0.01
            UnitType.Length.Millimeter -> meters / 0.001
            UnitType.Length.Inch -> meters / 0.0254
            UnitType.Length.Foot -> meters / 0.3048
            UnitType.Length.Yard -> meters / 0.9144
            UnitType.Length.Mile -> meters / 1609.344
        }
    }

    private fun convertWeight(value: Double, from: UnitType.Weight, to: UnitType.Weight): Double {
        val grams = when (from) {
            UnitType.Weight.Gram -> value
            UnitType.Weight.Kilogram -> value * 1000
            UnitType.Weight.Milligram -> value * 0.001
            UnitType.Weight.Ounce -> value * 28.3495231
            UnitType.Weight.Pound -> value * 453.59237
        }

        return when (to) {
            UnitType.Weight.Gram -> grams
            UnitType.Weight.Kilogram -> grams / 1000
            UnitType.Weight.Milligram -> grams / 0.001
            UnitType.Weight.Ounce -> grams / 28.3495231
            UnitType.Weight.Pound -> grams / 453.59237
        }
    }
}

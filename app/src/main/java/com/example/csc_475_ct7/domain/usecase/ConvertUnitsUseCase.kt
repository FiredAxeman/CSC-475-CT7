package com.example.csc_475_ct7.domain.usecase

import com.example.csc_475_ct7.domain.model.UnitType
import javax.inject.Inject

class ConvertUnitsUseCase @Inject constructor() {

    operator fun invoke(value: Double, from: UnitType, to: UnitType): Double {
        return when {
            from is UnitType.Temperature && to is UnitType.Temperature -> convertTemperature(value, from, to)
            from is UnitType.Length && to is UnitType.Length -> convertLength(value, from, to)
            from is UnitType.Weight && to is UnitType.Weight -> convertWeight(value, from, to)
            from is UnitType.Volume && to is UnitType.Volume -> convertVolume(value, from, to)
            from is UnitType.Speed && to is UnitType.Speed -> convertSpeed(value, from, to)
            from is UnitType.Time && to is UnitType.Time -> convertTime(value, from, to)
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

    private fun convertVolume(value: Double, from: UnitType.Volume, to: UnitType.Volume): Double {
        val liters = when (from) {
            UnitType.Volume.Liter -> value
            UnitType.Volume.Milliliter -> value * 0.001
            UnitType.Volume.Gallon -> value * 3.78541
            UnitType.Volume.Quart -> value * 0.946353
            UnitType.Volume.Pint -> value * 0.473176
            UnitType.Volume.Cup -> value * 0.236588
        }

        return when (to) {
            UnitType.Volume.Liter -> liters
            UnitType.Volume.Milliliter -> liters / 0.001
            UnitType.Volume.Gallon -> liters / 3.78541
            UnitType.Volume.Quart -> liters / 0.946353
            UnitType.Volume.Pint -> liters / 0.473176
            UnitType.Volume.Cup -> liters / 0.236588
        }
    }

    private fun convertSpeed(value: Double, from: UnitType.Speed, to: UnitType.Speed): Double {
        val ms = when (from) {
            UnitType.Speed.MS -> value
            UnitType.Speed.KmH -> value / 3.6
            UnitType.Speed.Mph -> value * 0.44704
            UnitType.Speed.Knot -> value * 0.514444
        }

        return when (to) {
            UnitType.Speed.MS -> ms
            UnitType.Speed.KmH -> ms * 3.6
            UnitType.Speed.Mph -> ms / 0.44704
            UnitType.Speed.Knot -> ms / 0.514444
        }
    }

    private fun convertTime(value: Double, from: UnitType.Time, to: UnitType.Time): Double {
        val seconds = when (from) {
            UnitType.Time.Second -> value
            UnitType.Time.Minute -> value * 60
            UnitType.Time.Hour -> value * 3600
            UnitType.Time.Day -> value * 86400
            UnitType.Time.Week -> value * 604800
        }

        return when (to) {
            UnitType.Time.Second -> seconds
            UnitType.Time.Minute -> seconds / 60
            UnitType.Time.Hour -> seconds / 3600
            UnitType.Time.Day -> seconds / 86400
            UnitType.Time.Week -> seconds / 604800
        }
    }
}

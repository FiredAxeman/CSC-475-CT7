package com.example.csc_475_ct7.domain.model

sealed interface UnitType {
    val name: String
    val abbreviation: String

    sealed interface Temperature : UnitType {
        object Celsius : Temperature { override val name = "Celsius"; override val abbreviation = "°C" }
        object Fahrenheit : Temperature { override val name = "Fahrenheit"; override val abbreviation = "°F" }
        object Kelvin : Temperature { override val name = "Kelvin"; override val abbreviation = "K" }
    }

    sealed interface Length : UnitType {
        object Meter : Length { override val name = "Meter"; override val abbreviation = "m" }
        object Kilometer : Length { override val name = "Kilometer"; override val abbreviation = "km" }
        object Centimeter : Length { override val name = "Centimeter"; override val abbreviation = "cm" }
        object Millimeter : Length { override val name = "Millimeter"; override val abbreviation = "mm" }
        object Inch : Length { override val name = "Inch"; override val abbreviation = "in" }
        object Foot : Length { override val name = "Foot"; override val abbreviation = "ft" }
        object Yard : Length { override val name = "Yard"; override val abbreviation = "yd" }
        object Mile : Length { override val name = "Mile"; override val abbreviation = "mi" }
    }

    sealed interface Weight : UnitType {
        object Gram : Weight { override val name = "Gram"; override val abbreviation = "g" }
        object Kilogram : Weight { override val name = "Kilogram"; override val abbreviation = "kg" }
        object Milligram : Weight { override val name = "Milligram"; override val abbreviation = "mg" }
        object Ounce : Weight { override val name = "Ounce"; override val abbreviation = "oz" }
        object Pound : Weight { override val name = "Pound"; override val abbreviation = "lb" }
    }
}

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

    sealed interface Volume : UnitType {
        object Liter : Volume { override val name = "Liter"; override val abbreviation = "L" }
        object Milliliter : Volume { override val name = "Milliliter"; override val abbreviation = "mL" }
        object Gallon : Volume { override val name = "Gallon"; override val abbreviation = "gal" }
        object Quart : Volume { override val name = "Quart"; override val abbreviation = "qt" }
        object Pint : Volume { override val name = "Pint"; override val abbreviation = "pt" }
        object Cup : Volume { override val name = "Cup"; override val abbreviation = "cup" }
    }

    sealed interface Speed : UnitType {
        object KmH : Speed { override val name = "Km/h"; override val abbreviation = "km/h" }
        object Mph : Speed { override val name = "Mph"; override val abbreviation = "mph" }
        object MS : Speed { override val name = "M/s"; override val abbreviation = "m/s" }
        object Knot : Speed { override val name = "Knot"; override val abbreviation = "kn" }
    }

    sealed interface Time : UnitType {
        object Second : Time { override val name = "Second"; override val abbreviation = "s" }
        object Minute : Time { override val name = "Minute"; override val abbreviation = "min" }
        object Hour : Time { override val name = "Hour"; override val abbreviation = "h" }
        object Day : Time { override val name = "Day"; override val abbreviation = "d" }
        object Week : Time { override val name = "Week"; override val abbreviation = "wk" }
    }
}

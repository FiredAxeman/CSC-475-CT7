package com.example.csc_475_ct7.domain.usecase

import com.example.csc_475_ct7.domain.model.UnitType
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertUnitsUseCaseTest {

    private val useCase = ConvertUnitsUseCase()

    @Test
    fun `convert celsius to fahrenheit`() {
        val result = useCase(0.0, UnitType.Temperature.Celsius, UnitType.Temperature.Fahrenheit)
        assertEquals(32.0, result, 0.001)
    }

    @Test
    fun `convert fahrenheit to celsius`() {
        val result = useCase(32.0, UnitType.Temperature.Fahrenheit, UnitType.Temperature.Celsius)
        assertEquals(0.0, result, 0.001)
    }

    @Test
    fun `convert meter to kilometer`() {
        val result = useCase(1000.0, UnitType.Length.Meter, UnitType.Length.Kilometer)
        assertEquals(1.0, result, 0.001)
    }

    @Test
    fun `convert kilogram to gram`() {
        val result = useCase(1.0, UnitType.Weight.Kilogram, UnitType.Weight.Gram)
        assertEquals(1000.0, result, 0.001)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `convert across categories throws exception`() {
        useCase(1.0, UnitType.Temperature.Celsius, UnitType.Length.Meter)
    }
}

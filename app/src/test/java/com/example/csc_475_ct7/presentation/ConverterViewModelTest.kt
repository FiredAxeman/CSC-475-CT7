package com.example.csc_475_ct7.presentation

import com.example.csc_475_ct7.domain.model.ConversionCategory
import com.example.csc_475_ct7.domain.model.UnitType
import com.example.csc_475_ct7.domain.usecase.ConvertUnitsUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ConverterViewModelTest {

    private val convertUnitsUseCase = mockk<ConvertUnitsUseCase>()
    private lateinit var viewModel: ConverterViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { convertUnitsUseCase(any(), any(), any()) } returns 2.0
        viewModel = ConverterViewModel(convertUnitsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is temperature`() {
        val state = viewModel.uiState.value
        assertEquals(ConversionCategory.TEMPERATURE, state.category)
        assertEquals(UnitType.Temperature.Celsius, state.fromUnit)
    }

    @Test
    fun `onInputChange updates input and output`() {
        every { convertUnitsUseCase(10.0, any(), any()) } returns 50.0
        
        viewModel.onInputChange("10")
        
        val state = viewModel.uiState.value
        assertEquals("10", state.inputValue)
        assertEquals("50", state.outputValue)
    }

    @Test
    fun `onCategoryChange resets units`() {
        viewModel.onCategoryChange(ConversionCategory.LENGTH)
        
        val state = viewModel.uiState.value
        assertEquals(ConversionCategory.LENGTH, state.category)
        assertEquals(UnitType.Length.Meter, state.fromUnit)
    }
}

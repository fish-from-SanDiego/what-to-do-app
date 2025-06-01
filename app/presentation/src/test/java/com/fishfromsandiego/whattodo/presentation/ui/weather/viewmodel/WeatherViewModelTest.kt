package com.fishfromsandiego.whattodo.presentation.ui.weather.viewmodel

import com.fishfromsandiego.whattodo.domain.weather.model.WeatherModel
import com.fishfromsandiego.whattodo.domain.weather.model.WeatherType
import com.fishfromsandiego.whattodo.domain.weather.usecase.GetTodayWeather
import com.fishfromsandiego.whattodo.presentation.ui.weather.action.WeatherScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.weather.sideeffect.WeatherScreenSideEffect
import com.fishfromsandiego.whattodo.presentation.ui.weather.state.WeatherUiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.orbitmvi.orbit.test.OrbitTestContext
import org.orbitmvi.orbit.test.test

@RunWith(JUnit4::class)
class WeatherViewModelTest {

    val getTodayWeather = mockk<GetTodayWeather>()
    val getTodayWeatherBad = mockk<GetTodayWeather>()

    var current: WeatherModel = getRandModel()

    init {
        coEvery { getTodayWeather() } returns Result.success(getRandModel())
        coEvery { getTodayWeatherBad() } returns Result.failure(RuntimeException("error"))
    }

    @Test
    fun `init state should have correct values`() = runTest {
        testDefault {
            expectInitialState()
        }
    }

    @Test
    fun `state should have correct values on success`() = runTest {
        testDefault {
            initModel()

            skipExpectAndAssert {
                assertNotNull(state.weatherModel)
                assertTrue(state.weatherModel!!.isSuccess)
                assertFalse(state.isLoading)
                assertEquals(current, state.weatherModel!!.getOrNull()!!)
            }
        }
    }

    @Test
    fun `state should have correct values on error`() = runTest {
        WeatherViewModel(getTodayWeatherBad, WeatherUiState()).test(this, WeatherUiState()) {
            initModel()

            skipExpectAndAssert(3) {
                assertNotNull(state.weatherModel)
                assertTrue(state.weatherModel!!.isFailure)
                assertTrue(state.weatherModel!!.exceptionOrNull()!! is RuntimeException)
                assertFalse(state.isLoading)
            }
        }
    }

    suspend fun TestScope.testDefault(block: suspend OrbitTestContext<WeatherUiState, WeatherScreenSideEffect, WeatherViewModel>.() -> Unit) {
        getViewModel().test(this, WeatherUiState(), validate = block)
    }


    suspend fun OrbitTestContext<WeatherUiState, WeatherScreenSideEffect, WeatherViewModel>.initModel() {
        expectInitialState()
        containerHost.dispatch(WeatherScreenAction.LoadWeatherModel)
    }

    suspend fun OrbitTestContext<WeatherUiState, WeatherScreenSideEffect, WeatherViewModel>.skipExpectAndAssert(
        nItems: Int = 1,
        block: () -> Unit
    ) {
        skipItems(nItems)
        block()
    }

    val OrbitTestContext<WeatherUiState, WeatherScreenSideEffect, WeatherViewModel>.state
        get() = containerHost.container.stateFlow.value

    fun getRandModel(): WeatherModel {
        val type = WeatherType.entries.toTypedArray().random()
        current = WeatherModel(
            weatherType = type,
            description = type.name,
        )
        return current
    }

    fun getViewModel() = WeatherViewModel(
        getTodayWeather,
        WeatherUiState()
    )

}
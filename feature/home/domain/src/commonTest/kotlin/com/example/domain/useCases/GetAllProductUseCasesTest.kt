package com.example.domain.useCases

import com.appynitty.common.events.CommonEvents
import com.example.domain.events.homeScreen.HomeScreenEvents
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetAllProductUseCasesTest {
    private val repository = FakeSuccessProductRepository()
    private val useCase = GetAllProductUseCases(repository)

    private val errorRepository = FakeErrorRepository()
    private val useCaseerror = GetAllProductUseCases(errorRepository)

    @Test
    fun `should emit loading then product list`() = runTest {

        val emissions = useCase().toList()
        assertTrue(emissions.any { it is CommonEvents.IsLoading })
        assertTrue(emissions.any { it is HomeScreenEvents.GetAllProductList })
    }



    @Test
    fun `should emit error message when repository returns exception`() = runTest {
        val emissions = useCaseerror().toList()
        assertTrue(emissions.any { it is CommonEvents.IsLoading })
        assertTrue(emissions.any { it is CommonEvents.ErrorMessage })
    }

    @Test
    fun `should fail because error message is different`() = runTest {
        val emissions = useCaseerror().toList()
        val error = emissions.filterIsInstance<CommonEvents.ErrorMessage>().first()
        assertTrue(error.errorMessage == "network_failure")
    }

    @Test
    fun `should fail when expecting product list`() = runTest {
        val emissions = useCaseerror().toList()
        assertTrue(
            emissions.any { it is HomeScreenEvents.GetAllProductList }
        )
    }

    @Test
    fun `should emit error message when BaseResult Error is returned`() = runTest {

        val useCase = GetAllProductUseCases(FakeBaseErrorRepository())

        val emissions = useCase().toList()

        val error = emissions.filterIsInstance<CommonEvents.ErrorMessage>().first()

        assertEquals("something_went_wrong", error.errorMessage)
    }

    @Test
    fun `should emit loading true then loading false`() = runTest {

        val emissions = useCase().toList()

        val loadingEvents = emissions.filterIsInstance<CommonEvents.IsLoading>()

        assertTrue(loadingEvents.first().isLoading)
        assertFalse(loadingEvents.last().isLoading)
    }

    @Test
    fun `should emit product list but repository returns error`() = runTest {

        val useCase = GetAllProductUseCases(FakeBaseErrorRepository())

        val emissions = useCase().toList()

        // This assertion will FAIL
        assertTrue(emissions.any { it is HomeScreenEvents.GetAllProductList })
    }



}
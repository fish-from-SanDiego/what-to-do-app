package com.fishfromsandiego.whattodo.presentation.ui.chore.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.fishfromsandiego.whattodo.common.exceptions.WhatToDoAppCaughtException
import com.fishfromsandiego.whattodo.domain.chore.model.ChoreModel
import com.fishfromsandiego.whattodo.domain.chore.repository.ChoreRepository
import com.fishfromsandiego.whattodo.domain.chore.usecase.AddAndGetNewChore
import com.fishfromsandiego.whattodo.domain.chore.usecase.EditExistingChore
import com.fishfromsandiego.whattodo.domain.chore.usecase.ListAllChoresNewFirst
import com.fishfromsandiego.whattodo.presentation.ui.chore.action.ChoresScreenAction
import com.fishfromsandiego.whattodo.presentation.ui.chore.sideeffect.ChoresSideEffect
import com.fishfromsandiego.whattodo.presentation.ui.chore.state.ChoresUiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.orbitmvi.orbit.test.OrbitTestContext
import org.orbitmvi.orbit.test.test
import java.time.LocalDate
import java.time.ZoneOffset
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class ChoresViewModelTest {
    private var repo = ChoreRepositoryFakeImpl()
    val addAndGetNewChore = AddAndGetNewChore(repo)
    val editExistingChore = EditExistingChore(repo)
    val listAllChoresNewFirst = ListAllChoresNewFirst(repo)


    @Before
    fun setUp() {
        runBlocking {
            addAndGetNewChore(
                ChoreModel(
                    title = "First",
                    description = "desc",
                    date = LocalDate.now(),
                )
            )
            addAndGetNewChore(
                ChoreModel(
                    title = "Second",
                    description = null,
                    date = LocalDate.now(),
                )
            )
        }
    }

    @After
    fun cleanUp() {
        repo = ChoreRepositoryFakeImpl()
    }


    @Test
    fun `init state should have correct values`() = runTest {
        testDefault {
            initModel()

            expectState {
                copy(
                    chores = Result.success(repo.chores.value),
                    expandedIds = setOf(),
                    isLoading = false
                )
            }

            assertNotNull(state.chores)
        }
    }


    @Test
    fun `result should be exception on repo exception`() = runTest {
        val listMock = mockk<ListAllChoresNewFirst>()
        coEvery { listMock.invoke() } returns flow {
            throw RuntimeException("ew, some error occured")
        }
        ChoresViewModel(
            listAllChoresNewFirst = listMock,
            addAndGetNewChore = mockk<AddAndGetNewChore>(),
            editExistingChore = mockk<EditExistingChore>(),
            initialState = ChoresUiState()
        ).test(this, ChoresUiState()) {
            initModel()

            skipExpectAndAssert {
                assertNotNull(state.chores)
                assertTrue(state.chores!!.isFailure)
                assertTrue(state.chores!!.exceptionOrNull()!! is WhatToDoAppCaughtException)
            }
        }
    }

    @Test
    fun `should correctly navigate to editing existing item`() = runTest {
        testDefault {
            initModel()
            skipItems(1)
            val choreToEdit = state.chores!!.getOrNull()!!.first()
            val prev = state.copy()
            containerHost.dispatch(ChoresScreenAction.EditChore(chore = choreToEdit))
            expectState {
                prev.copy(
                    editChoreId = choreToEdit.id,
                    editChoreTitle = choreToEdit.title,
                    editChoreDescription = TextFieldValue(
                        text = choreToEdit.description ?: "",
                        selection = TextRange.Zero,
                    ),
                    editChoreDateMillis = choreToEdit.date.atStartOfDay().atZone(ZoneOffset.UTC)
                        .toInstant().toEpochMilli()
                )
            }
            expectSideEffect(ChoresSideEffect.NavigateToChoreEdition)
        }
    }

    @Test
    fun `should correctly navigate to creating new item`() = runTest {
        testDefault {
            initModel()
            skipItems(1)
            containerHost.dispatch(ChoresScreenAction.CreateNewChore)
            expectSideEffect(ChoresSideEffect.NavigateToChoreEdition)

            assertNull(state.editChoreId)
            assertNull(state.editChoreDateMillis)
            assertEquals(
                state.editChoreDescription,
                TextFieldValue(text = "", selection = TextRange.Zero)
            )
            assertTrue(state.editChoreTitle.isEmpty())
        }
    }

    @Test
    fun `should correctly update value and end editing`() = runTest {
        testDefault {
            initModel()
            skipItems(1)
            containerHost.dispatch(
                ChoresScreenAction.EditChore(
                    state.chores!!.getOrNull()!!.first()
                )
            )
            skipItems(2)
            val newTitle = "First new"
            val prev = state.copy()
            containerHost.dispatch(ChoresScreenAction.EditTitleAndUpdateShowWrong(newTitle))
            expectState {
                prev.copy(
                    editChoreTitle = newTitle,
                    showTitleInputWrong = false,
                )
            }
            containerHost.dispatch(ChoresScreenAction.SaveChore)

//            2 because flow emit causes state update
            skipExpectAndAssert(2) {
                assertEquals(state.chores!!.getOrNull()!!.first().title, newTitle)
                assertEquals(repo.chores.value.first().title, newTitle)
            }
            expectSideEffect(ChoresSideEffect.NavigateBackToChoreList)
        }
    }

    @Test
    fun `should show input as wrong on title rename`() = runTest {
        testDefault {
            initModel()
            skipItems(1)
            containerHost.dispatch(
                ChoresScreenAction.EditChore(state.chores!!.getOrNull()!!.first())
            )
            skipItems(2)
            val newTitle = ""
            containerHost.dispatch(ChoresScreenAction.EditTitleAndUpdateShowWrong(newTitle))

            skipExpectAndAssert {
                assertTrue(state.showTitleInputWrong)
                assertEquals(state.editChoreTitle, newTitle)
            }

            val choresSaved = state.chores!!.getOrNull()!!

//            can't save chore -> don't post side effect
            containerHost.dispatch(ChoresScreenAction.SaveChore)

            assertEquals(state.chores!!.getOrNull()!!, choresSaved)
        }
    }

    @Test
    fun `should hide date picker on date selection` () = runTest {
        testDefault {
            initModel()
            skipItems(1)
            containerHost.dispatch(
                ChoresScreenAction.EditChore(state.chores!!.getOrNull()!!.first())
            )
            skipItems(2)
            containerHost.dispatch(ChoresScreenAction.ChangeShowPicker(true))
            skipItems(1)
            val newDateMillis = 1745402400011
            containerHost.dispatch(ChoresScreenAction.SelectDate(newDateMillis))

            skipExpectAndAssert {
                assertFalse(state.showTitleInputWrong)
                assertEquals(state.editChoreDateMillis, newDateMillis)
                assertFalse(state.showPicker)
            }
        }
    }

    @Test
    fun `should add chore to expanded on expansion` () = runTest {
        testDefault {
            initModel()
            skipItems(1)
            containerHost.dispatch(ChoresScreenAction.ExpandChoreDescription(choreId = 1))

            skipExpectAndAssert {
                assertTrue(state.expandedIds.contains(1))
                assertFalse(state.expandedIds.contains(2))
            }
        }
    }

    @Test
    fun `should remove chore from expanded on collapse` () = runTest {
        testDefault {
            initModel()
            skipItems(1)

            containerHost.dispatch(ChoresScreenAction.ExpandChoreDescription(choreId = 1))
            skipItems(1)
            containerHost.dispatch(ChoresScreenAction.ExpandChoreDescription(choreId = 2))
            skipItems(1)
            containerHost.dispatch(ChoresScreenAction.CollapseChoreDescription(choreId = 2))

            skipExpectAndAssert {
                assertTrue(state.expandedIds.contains(1))
                assertFalse(state.expandedIds.contains(2))
            }
        }
    }


    suspend fun TestScope.testDefault(block: suspend OrbitTestContext<ChoresUiState, ChoresSideEffect, ChoresViewModel>.() -> Unit) {
        getViewModel().test(this, ChoresUiState(), validate = block)
    }


    suspend fun OrbitTestContext<ChoresUiState, ChoresSideEffect, ChoresViewModel>.initModel() {
        expectInitialState()
        containerHost.dispatch(ChoresScreenAction.ListChoresNewFirst)
    }

    suspend fun OrbitTestContext<ChoresUiState, ChoresSideEffect, ChoresViewModel>.skipExpectAndAssert(
        nItems: Int = 1,
        block: () -> Unit
    ) {
        skipItems(nItems)
        block()
    }

    val OrbitTestContext<ChoresUiState, ChoresSideEffect, ChoresViewModel>.state
        get() = containerHost.container.stateFlow.value

    fun getViewModel() = ChoresViewModel(
        listAllChoresNewFirst, addAndGetNewChore, editExistingChore,
        ChoresUiState()
    )

}


private class ChoreRepositoryFakeImpl : ChoreRepository {
    val chores = MutableStateFlow<List<ChoreModel>>(emptyList())
    private val mutex = Mutex()
    private var nextId = 1L

    override suspend fun updateChore(id: Long, newValue: ChoreModel): Result<Unit> =
        mutex.withLock {
            val index = chores.value.indexOfFirst { it.id == id }
            return if (index != -1) {
                val updated = chores.value.toMutableList()
                updated[index] = newValue.copy(id = id)
                chores.value = updated
                Result.success(Unit)
            } else {
                Result.failure(NoSuchElementException("Chore with id $id not found"))
            }
        }

    override suspend fun createandGetChore(value: ChoreModel): Result<ChoreModel> = mutex.withLock {
        val newChore = value.copy(id = nextId++)
        chores.value = chores.value + newChore
        Result.success(newChore)
    }

    override suspend fun createChore(value: ChoreModel): Result<Unit> = mutex.withLock {
        val newChore = value.copy(id = nextId++)
        chores.value = chores.value + newChore
        Result.success(Unit)
    }

    override fun getAllChores(): Flow<List<ChoreModel>> = chores

    override fun getAllChoresOrderedByDateDesc(): Flow<List<ChoreModel>> =
        chores.map { it.sortedByDescending { chore -> chore.date } }
}
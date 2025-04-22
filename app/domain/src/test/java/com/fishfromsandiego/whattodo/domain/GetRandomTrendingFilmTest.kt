package com.fishfromsandiego.whattodo.domain

import com.fishfromsandiego.whattodo.domain.film.model.FilmModel
import com.fishfromsandiego.whattodo.domain.film.repository.FilmRepository
import com.fishfromsandiego.whattodo.domain.film.usecase.GetRandomTrendingFilm
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate
import kotlin.collections.contains

@RunWith(JUnit4::class)
class GetRandomTrendingFilmTest {
    val sampleFilmModel = FilmModel(
        id = 0,
        title = "Title",
        overview = "Overview",
        releaseDate = LocalDate.of(2004, 1, 1),
        posterUriStr = "https://someposter.com"
    )

    val filmsPool = List(20) { getRandomFilmModel() }
    val filmsShortPool = List(2) { getRandomFilmModel() }
    val filmsSinglePool = List(1) { getRandomFilmModel() }

    val repo = mockk<FilmRepository>()
    val repoShort = mockk<FilmRepository>()
    val repoSingle = mockk<FilmRepository>()
    val repoEmpty = mockk<FilmRepository>()
    val repoBad = mockk<FilmRepository>()

    init {
        coEvery { repo.getTrendingFilms() } returns Result.success(filmsPool)
        coEvery { repoShort.getTrendingFilms() } returns Result.success(filmsShortPool)
        coEvery { repoSingle.getTrendingFilms() } returns Result.success(filmsSinglePool)
        coEvery { repoEmpty.getTrendingFilms() } returns Result.success(listOf())
        coEvery { repoBad.getTrendingFilms() } returns Result.failure(RuntimeException())
    }


    @Test
    fun `should return success with one of films on repo success`() {
//        Arrange
        val sut = GetRandomTrendingFilm(repo)
        val results = mutableListOf<Result<FilmModel>>()

//        Act
        for (i in 1..50) {
            results.addLast(runBlocking { sut() })
        }

//        Test
        for (res in results) {
            val model = res.getOrNull()
            assertNotNull(model)
            assertTrue(filmsPool.contains(model))
        }

    }

    @Test
    fun `should exclude film with given id when possible`() {
//        Arrange
        val sut = GetRandomTrendingFilm(repoShort)
        val results = mutableListOf<Pair<Int, Result<FilmModel>>>()

//        Act
        for (i in 1..50) {
            val itemToExclude = filmsShortPool.random()
            results.addLast(itemToExclude.id to runBlocking { sut(itemToExclude.id) })
        }

        //        Test
        for (res in results) {
            val model = res.second.getOrNull()
            assertNotNull(model)
            assertNotEquals(model, filmsShortPool.find { it.id == res.first })
        }
    }

    @Test
    fun `should not fail on single-item list`() {
//        Arrange
        val sut = GetRandomTrendingFilm(repoSingle)

//        Act
        val res = runBlocking { sut(filmsSinglePool.first().id) }

//        Test
        assertTrue(res.isSuccess)
        assertEquals(res.getOrNull()!!, filmsSinglePool.first())
    }

    @Test
    fun `should fail on empty list`() {
//        Arrange
        val sut = GetRandomTrendingFilm(repoEmpty)

//        Act
        val res = runBlocking { sut() }

//        Test
        assertTrue(res.isFailure)
        assertEquals(res.exceptionOrNull()!!.message, "Loaded films list was empty")
    }

    @Test
    fun `should fail on repo fail`() {
//        Arrange
        val sut = GetRandomTrendingFilm(repoBad)

//        Act
        val res = runBlocking { sut() }

//        Test
        assertTrue(res.isFailure)
    }

    private fun getRandomFilmModel(): FilmModel {
        val filmId = (0..100000).random()
        return sampleFilmModel.run {
            FilmModel(
                id = filmId,
                title = "${this.title} $filmId",
                overview = "${this.overview} $filmId",
                releaseDate = LocalDate.of(
                    (2004..2020).random(),
                    (1..12).random(),
                    (1..25).random()
                ),
                posterUriStr = this.posterUriStr,
            )
        }
    }
}


//FilmModel(
//val id: Int,
//val title: String,
//val overview: String,
//val releaseDate: LocalDate,
//val posterUri: Uri,
//)
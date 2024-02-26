package io.github.hikking

import io.github.hikking.tmdb.api.KtorTmdbService
import io.github.hikking.tmdb.api.TmdbService
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import java.time.LocalDate


class KtorTmdbServiceTest : ShouldSpec({

    val tmdbService: TmdbService = KtorTmdbService(HttpClient(OkHttp) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                path("/3/")
            }
            accept(ContentType.Application.Json)
        }
        install(Resources)
        install(Logging)
        install(ContentNegotiation) {
            json()
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMGZjMzVmNWVjODdmNTJlZGViMGQ5MTc2NTVlMDU2ZiIsInN1YiI6IjVjMDJlMDA1OTI1MTQxN2U1NTA0NzYxYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.XwnmxVHqjnXCRqAy2mxbqoBmkqhPGt2-6Mr9pkeuULw",
                        ""
                    )
                }
            }
        }
    })

    context("get movie by id") {
        withData(105, 165, 196) { id ->
            val movieById = shouldNotThrowAny { tmdbService.getMovieById(id) }
            movieById.id shouldBe id
        }

        withData(105 to "Назад в будущее", 165 to "Назад в будущее II", 196 to "Назад в будущее III") { (id, title) ->
            val movieById = shouldNotThrowAny { tmdbService.getMovieById(id, language = "ru-RU") }
            movieById.id shouldBe id
            movieById.title shouldBe title
        }
    }

    context("get movie account states by id") {
        withData(105, 165, 196) { id ->
            val accountStatesById = shouldNotThrowAny { tmdbService.getAccountStates(id) }
            accountStatesById.id shouldBe id
        }
    }

    context("get alternative titles by id") {
        withData(105, 165, 196) { id ->
            val alternativeTitles = shouldNotThrowAny { tmdbService.getAlternativeTitles(id, "RU") }
            alternativeTitles.id shouldBe id
            alternativeTitles.titles shouldNot beEmpty()
        }
    }

    context("get changes by id") {
        shouldNotThrowAny {
            tmdbService.getChanges(
                955916,
                startDate = LocalDate.parse("2024-02-10"),
                endDate = LocalDate.parse("2024-02-24"),
                page = 1
            )
        }
    }

    context("get credits by id") {
        withData(603, 604, 605) { id ->
            val credits = shouldNotThrowAny {
                tmdbService.getCredits(id)
            }
            credits.id shouldBe id
        }
    }

    context("get external ids for the movie by id") {
        withData(603, 604, 605) { id ->
            val externalIds = shouldNotThrowAny {
                tmdbService.getExternalIds(id)
            }
            externalIds.id shouldBe id
        }
    }

    context("get images for the movie by id") {
        val languages = setOf("ru", "en")
        withData(603 to languages, 604 to languages, 605 to languages) { (id, languages) ->
            val images = shouldNotThrowAny {
                tmdbService.getImages(id, languages)
            }
            images.id shouldBe id
            images.logos.map { it.language } shouldContainExactly languages
            images.posters.map { it.language } shouldContainExactly languages
            images.backdrops.map { it.language } shouldContainExactly languages
        }
    }

    context("get keywords for the movie by id") {
        withData(603, 604, 605) { id ->
            val keywords = shouldNotThrowAny {
                tmdbService.getKeyWords(id)
            }
            keywords.id shouldBe id
        }
    }
})
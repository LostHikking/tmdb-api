@file:Suppress("TooManyFunctions")

package io.github.hikking.tmdb.api

import io.github.hikking.tmdb.api.serialization.objects.AccountStates
import io.github.hikking.tmdb.api.serialization.objects.AlternativeTitles
import io.github.hikking.tmdb.api.serialization.objects.Changes
import io.github.hikking.tmdb.api.serialization.objects.Credits
import io.github.hikking.tmdb.api.serialization.objects.ExternalIds
import io.github.hikking.tmdb.api.serialization.objects.Images
import io.github.hikking.tmdb.api.serialization.objects.KeyWords
import io.github.hikking.tmdb.api.serialization.objects.Movie
import io.github.hikking.tmdb.api.serialization.objects.UserLists
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.util.StringValuesBuilder
import java.time.LocalDate

class KtorTmdbService(private val httpClient: HttpClient) : TmdbService() {
    override suspend fun getMovieById(movieId: Int, appendToResponse: String?, language: String?): Movie {
        return httpClient.get {
            url {
                appendPathSegments("movie", "$movieId")
                parameters.append("append_to_response", appendToResponse)
                parameters.append("language", language)
            }
        }.body()
    }

    override suspend fun getAccountStates(movieId: Int, sessionId: String?, guestSessionId: String?): AccountStates {
        return httpClient.get {
            url {
                appendPathSegments("movie", "$movieId", "account_states")
                parameters.append("session_id", sessionId)
                parameters.append("guest_session_id", guestSessionId)
            }
        }.body()
    }

    override suspend fun getAlternativeTitles(movieId: Int, country: String?): AlternativeTitles {
        return httpClient.get {
            url {
                appendPathSegments("movie", "$movieId", "alternative_titles")
                parameters.append("country", country)
            }
        }.body()
    }

    override suspend fun getChanges(movieId: Int, endDate: LocalDate?, page: Int?, startDate: LocalDate?): Changes {
        return httpClient.get {
            url {
                appendPathSegments("movie", "$movieId", "changes")
                parameters.append("end_date", endDate)
                parameters.append("page", page)
                parameters.append("end_date", startDate)
            }
        }.body()
    }


    override suspend fun getCredits(movieId: Int, language: String?): Credits {
        return httpClient.get {
            url {
                appendPathSegments("movie", "$movieId", "credits")
                parameters.append("language", language)
            }
        }.body()
    }

    override suspend fun getExternalIds(movieId: Int): ExternalIds {
        return httpClient.get {
            url {
                appendPathSegments("movie", "$movieId", "external_ids")
            }
        }.body()
    }

    override suspend fun getImages(movieId: Int, languages: Set<String?>?): Images {
        return httpClient.get {
            url {
                appendPathSegments("movie", "$movieId", "images")
                parameters.append("include_image_language", languages?.joinToString(","))
            }
        }.body()
    }

    override suspend fun getKeyWords(movieId: Int): KeyWords {
        return httpClient.get {
            url {
                appendPathSegments("movie", "$movieId", "keywords")
            }
        }.body()
    }

    override suspend fun getLatestMovie(): Movie {
        return httpClient.get {
            url {
                appendPathSegments("movie", "latest")
            }
        }.body()
    }

    override suspend fun getUserListsThatMovieBelongs(movieId: Int, page: Int, languages: Set<String?>?): UserLists {
        return httpClient.get {
            url {
                appendPathSegments("movie", "$movieId", "lists")
                parameters.append("page", page)
                parameters.append("language", languages?.joinToString(","))
            }
        }.body()
    }

    private fun StringValuesBuilder.append(name: String, value: Any?) {
        value?.let {
            append(name, value.toString())
        }
    }
}

package io.github.hikking.tmdb.api

import io.github.hikking.tmdb.api.serialization.objects.*
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

    private fun StringValuesBuilder.append(name: String, value: Any?) {
        value?.let {
            append(name, value.toString())
        }
    }
}

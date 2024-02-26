package io.github.hikking.tmdb.api

import io.github.hikking.tmdb.api.serialization.objects.*
import java.time.LocalDate

abstract class TmdbService() {
    /**
     * Get the top level details of a movie by ID.
     */
    abstract suspend fun getMovieById(movieId: Int, appendToResponse: String? = null, language: String? = null): Movie

    /**
     * Get the rating, watchlist and favourite status of an account.
     */
    abstract suspend fun getAccountStates(
        movieId: Int,
        sessionId: String? = null,
        guestSessionId: String? = null,
    ): AccountStates

    /**
     * Get the alternative titles for a movie.
     */
    abstract suspend fun getAlternativeTitles(movieId: Int, country: String? = null): AlternativeTitles

    /**
     * Get the recent changes for a movie.
     */
    abstract suspend fun getChanges(movieId: Int, endDate: LocalDate? = null, page: Int? = null, startDate: LocalDate? = null): Changes

    /**
     * Get the top level details of a movie by ID.
     */
    abstract suspend fun getCredits(movieId: Int, language: String? = null): Credits

    /**
     * Get external urls of a movie by ID.
     */
    abstract suspend fun getExternalIds(movieId: Int): ExternalIds
}
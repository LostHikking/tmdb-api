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
import java.time.LocalDate

abstract class TmdbService {
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
    abstract suspend fun getChanges(
        movieId: Int,
        endDate: LocalDate? = null,
        page: Int? = null,
        startDate: LocalDate? = null,
    ): Changes

    /**
     * Get the top level details of a movie by ID.
     */
    abstract suspend fun getCredits(movieId: Int, language: String? = null): Credits

    /**
     * Get external urls of a movie by ID.
     */
    abstract suspend fun getExternalIds(movieId: Int): ExternalIds

    /**
     * Get the images that belong to a movie.
     */
    abstract suspend fun getImages(movieId: Int, languages: Set<String?>? = null): Images

    /**
     * Get keywords of a movie by ID.
     */
    abstract suspend fun getKeyWords(movieId: Int): KeyWords

    /**
     * Get the latest movie.
     */
    abstract suspend fun getLatestMovie(): Movie

    /**
     * Get the lists that a movie has been added to.
     */
    abstract suspend fun getUserListsThatMovieBelongs(
        movieId: Int, page: Int, languages: Set<String?>? = null
    ): UserLists
}

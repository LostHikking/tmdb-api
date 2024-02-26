package io.github.hikking.tmdb.api.serialization.objects

import io.github.hikking.tmdb.api.serialization.serializers.LocalDateSerializer
import io.github.hikking.tmdb.api.serialization.serializers.ObjectOrFalseSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Movie(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("belongs_to_collection")
    val belongsToCollection: MovieCollection?,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Long,
    @SerialName("imdb_id")
    val imdbId: String,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerialName("release_date")
    @Serializable(with = LocalDateSerializer::class)
    val releaseDate: LocalDate,
    val revenue: Long,
    val runtime: Long,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Long,
)

@Serializable
data class Credits(
    val id: Long,
    val cast: List<Cast>,
    val crew: List<Crew>,
)

@Serializable
data class Cast(
    val adult: Boolean,
    val gender: Gender,
    val id: Long,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("cast_id")
    val castId: Long,
    val character: String,
    @SerialName("credit_id")
    val creditId: String,
    val order: Long,
)

@Serializable
data class Crew(
    val adult: Boolean,
    val gender: Gender,
    val id: Long,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("credit_id")
    val creditId: String,
    val department: String,
    val job: String,
)

@Serializable
enum class Gender {
    @SerialName("0")
    UNKNOWN,
    @SerialName("1")
    FEMALE,
    @SerialName("2")
    MALE,
}

@Serializable
data class MovieCollection(
    val id: Int,
    val name: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("backdrop_path")
    val backdropPath: String,
)

@Serializable
data class Genre(
    val id: Long,
    val name: String,
)

@Serializable
data class ProductionCompany(
    val id: Long,
    @SerialName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerialName("origin_country")
    val originCountry: String,
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String,
    val name: String,
)

@Serializable
data class SpokenLanguage(
    @SerialName("english_name")
    val englishName: String,
    @SerialName("iso_639_1")
    val iso6391: String,
    val name: String,
)

@Serializable
data class AccountStates(
    val id: Long,
    val favorite: Boolean,
    @Serializable(with = RatedSerializer::class)
    val rated: Rated?,
    val watchlist: Boolean,
)

object RatedSerializer : ObjectOrFalseSerializer<Rated>(Rated.serializer())

@Serializable
data class Rated(
    val value: Double,
)

@Serializable
data class AlternativeTitles(
    val id: Long,
    val titles: List<Title>,
)

@Serializable
data class Title(
    @SerialName("iso_3166_1")
    val country: String,
    val title: String,
    val type: String,
)

@Serializable
data class Changes(
    @Contextual
    val changes: List<ChangeGroup<@Contextual Any>>,
)

@Serializable
sealed class ChangeGroup<T: Any> {
    abstract val key: String
    abstract val items: List<BaseChange<T>>
}

@Serializable
data class HomepageChangeGroup(
    override val key: String,
    override val items: List<BaseChange<String>>
): ChangeGroup<String>()

@Serializable
data class OverviewChangeGroup(
    override val key: String,
    override val items: List<BaseChange<String>>
): ChangeGroup<String>()

@Serializable
data class ImagesChangeGroup(
    override val key: String,
    override val items: List<BaseChange<ImageChange>>
): ChangeGroup<ImageChange>()

@Serializable
data class RuntimeChangeGroup(
    override val key: String,
    override val items: List<BaseChange<Int>>
): ChangeGroup<Int>()

@Serializable
data class SpokenLanguageChangeGroup(
    override val key: String,
    override val items: List<BaseChange<List<String>>>
): ChangeGroup<List<String>>()

@Serializable
data class TranslationsChangeGroup(
    override val key: String,
    override val items: List<BaseChange<String>>
): ChangeGroup<String>()

@Serializable
sealed class ImageChange {
    @SerialName("file_path")
    abstract val filePath: String
    @SerialName("iso_639_1")
    abstract val language: String?
}

@Serializable
data class Backdrop(
    override val filePath: String,
    override val language: String?,
): ImageChange()

@Serializable
data class Poster(
    override val filePath: String,
    override val language: String?,
): ImageChange()

@Serializable
data class TitleChangeGroup(
    override val key: String,
    override val items: List<BaseChange<String>>
): ChangeGroup<String>()

@Serializable
sealed class BaseChange<T: Any> {
    abstract val id: String
    abstract val action: String
    abstract val time: String
    abstract val language: String
    abstract val country: String
    abstract val value: T
}

@Serializable
data class AddChange<T: Any>(
    override val id: String,
    override val action: String,
    override val time: String,
    @SerialName("iso_639_1")
    override val language: String,
    @SerialName("iso_3166_1")
    override val country: String,
    override val value: T,
): BaseChange<T>()

@Serializable
data class UpdateChange<T: Any>(
    override val id: String,
    override val action: String,
    override val time: String,
    override val language: String,
    override val country: String,
    override val value: T,
    val originalValue: T,
) : BaseChange<T>()
package info.movito.themoviedbapi

import info.movito.themoviedbapi.model.Artwork
import info.movito.themoviedbapi.model.ArtworkType
import info.movito.themoviedbapi.model.MovieImages
import info.movito.themoviedbapi.model.core.ResultsPage
import info.movito.themoviedbapi.model.people.Person
import info.movito.themoviedbapi.model.people.PersonCredits
import info.movito.themoviedbapi.model.people.PersonPeople
import info.movito.themoviedbapi.tools.ApiUrl
import info.movito.themoviedbapi.tools.MovieDbException


class TmdbPeople internal constructor(tmdbApi: TmdbApi) : AbstractTmdbApi(tmdbApi) {

    val DEFAULT_LANGUAGE = "en-US"
    /**
     * Get the latest person id.
     */
    val personLatest: PersonPeople
        get() {
           return personLatest(DEFAULT_LANGUAGE)
        }

    fun personLatest(language: String): PersonPeople {
        val apiUrl = ApiUrl(TMDB_METHOD_PERSON, "latest")

        apiUrl.addLanguage(language)

        return mapJsonResult(apiUrl, PersonPeople::class.java)
    }

    /**
     * This method is used to retrieve all of the basic person information.
     *
     *
     * It will return the single highest rated profile image.
     *
     * @param personId
     */



    fun getPersonInfo(personId: Int, vararg appendToResponse: String): PersonPeople{
        return getPersonInfo(DEFAULT_LANGUAGE, personId, *appendToResponse)
    }

    fun getPersonInfo(language: String, personId: Int, vararg appendToResponse: String): PersonPeople {
        val apiUrl = ApiUrl(TMDB_METHOD_PERSON, personId)

        apiUrl.appendToResponse(*appendToResponse)
        apiUrl.addLanguage(language)

        return mapJsonResult(apiUrl, PersonPeople::class.java)
    }

    /**
     * This method is used to retrieve all of the cast & crew information for the person.
     *
     *
     * It will return the single highest rated poster for each movie record.
     *
     * @param personId
     */

    fun getCombinedPersonCredits(personId: Int): PersonCredits {
        return getCombinedPersonCredits(personId, DEFAULT_LANGUAGE)
    }
    fun getCombinedPersonCredits(personId: Int, language: String): PersonCredits {
        val apiUrl = ApiUrl(TMDB_METHOD_PERSON, personId, "combined_credits")

        apiUrl.addLanguage(language)

        return mapJsonResult(apiUrl, PersonCredits::class.java)
    }


    /**
     * This method is used to retrieve all of the profile images for a person.
     *
     * @param personId
     */
    fun getPersonImages(personId: Int): List<Artwork> {
        val apiUrl = ApiUrl(TMDB_METHOD_PERSON, personId, "images")

        return mapJsonResult<MovieImages>(apiUrl, MovieImages::class.java).getAll(ArtworkType.PROFILE)
    }


    /**
     * Get the changes for a specific person id.
     *
     *
     * Changes are grouped by key, and ordered by date in descending order.
     *
     *
     * By default, only the last 24 hours of changes are returned.
     *
     *
     * The maximum number of days that can be returned in a single request is 14.
     *
     *
     * The language is present on fields that are translatable.
     *
     * @param personId
     * @param startDate
     * @param endDate
     */
    fun getPersonChanges(personId: Int, startDate: String, endDate: String) {
        throw MovieDbException("Not implemented yet")
    }


    /**
     * Get the list of popular people on The Movie Database.
     *
     *
     * This list refreshes every day.
     *
     * @param page
     * @return
     */

    fun getPersonPopular(page: Int?): PersonResultsPage {
        return getPersonPopular(page, DEFAULT_LANGUAGE)
    }

    fun getPersonPopular(page: Int?, language: String) : PersonResultsPage {
        val apiUrl = ApiUrl(TMDB_METHOD_PERSON, "popular")

        apiUrl.addPage(page)
        apiUrl.addLanguage(language)

        return mapJsonResult(apiUrl, PersonResultsPage::class.java)
    }



    class PersonResultsPage : ResultsPage<Person>()

    companion object {

        val TMDB_METHOD_PERSON = "person"
    }
}

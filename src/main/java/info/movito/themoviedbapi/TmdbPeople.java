package info.movito.themoviedbapi;

import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.ArtworkType;
import info.movito.themoviedbapi.model.MovieImages;
import info.movito.themoviedbapi.model.core.ResultsPage;
import info.movito.themoviedbapi.model.people.Person;
import info.movito.themoviedbapi.model.people.PersonCredits;
import info.movito.themoviedbapi.model.people.PersonPeople;
import info.movito.themoviedbapi.tools.ApiUrl;
import info.movito.themoviedbapi.tools.MovieDbException;

import java.util.List;


public class TmdbPeople extends AbstractTmdbApi {

    public static final String TMDB_METHOD_PERSON = "person";


    TmdbPeople(TmdbApi tmdbApi) {
        super(tmdbApi);
    }


    /**
     * This method is used to retrieve all of the basic person information.
     * 
     * It will return the single highest rated profile image.
     */
    public PersonPeople getPersonInfo(int personId, String... appendToResponse) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_PERSON, personId);

        apiUrl.appendToResponse(appendToResponse);


        return mapJsonResult(apiUrl, PersonPeople.class);
    }


    /**
     * This method is used to retrieve all of the cast & crew information for the person.
     * 
     * It will return the single highest rated poster for each movie record.
     */
    public PersonCredits getCombinedPersonCredits(int personId) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_PERSON, personId, "combined_credits");

        return mapJsonResult(apiUrl, PersonCredits.class);
    }


    /**
     * This method is used to retrieve all of the profile images for a person.
     */
    public List<Artwork> getPersonImages(int personId) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_PERSON, personId, "images");

        return mapJsonResult(apiUrl, MovieImages.class).getAll(ArtworkType.PROFILE);
    }


    /**
     * Get the changes for a specific person id.
     * 
     * Changes are grouped by key, and ordered by date in descending order.
     * 
     * By default, only the last 24 hours of changes are returned.
     * 
     * The maximum number of days that can be returned in a single request is 14.
     * 
     * The language is present on fields that are translatable.
     */
    public void getPersonChanges(int personId, String startDate, String endDate) {
        throw new MovieDbException("Not implemented yet");
    }


    /**
     * Get the list of popular people on The Movie Database.
     * 
     * This list refreshes every day.
     */
    public PersonResultsPage getPersonPopular(Integer page) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_PERSON, "popular");

        apiUrl.addPage(page);

        return mapJsonResult(apiUrl, PersonResultsPage.class);
    }


    public static class PersonResultsPage extends ResultsPage<Person> {

    }


    /**
     * Get the latest person id.
     */
    public PersonPeople getPersonLatest() {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_PERSON, "latest");


        return mapJsonResult(apiUrl, PersonPeople.class);
    }
}

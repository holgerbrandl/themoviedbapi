package info.movito.themoviedbapi;

import info.movito.themoviedbapi.model.Collection;
import info.movito.themoviedbapi.model.Company;
import info.movito.themoviedbapi.model.Multi;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.ResultsPage;
import info.movito.themoviedbapi.model.keywords.Keyword;
import info.movito.themoviedbapi.tools.ApiUrl;

import static info.movito.themoviedbapi.TmdbCollections.TMDB_METHOD_COLLECTION;
import static info.movito.themoviedbapi.TmdbLists.TMDB_METHOD_LIST;
import static info.movito.themoviedbapi.TmdbMovies.TMDB_METHOD_MOVIE;
import static info.movito.themoviedbapi.TmdbTV.TMDB_METHOD_TV;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


public class TmdbSearch extends AbstractTmdbApi {

    public static final String TMDB_METHOD_SEARCH = "search";
    private static final String PARAM_QUERY = "query";
	
    public static final Object TMDB_METHOD_MULTI = "multi";


    public TmdbSearch(TmdbApi tmdbApi) {
        super(tmdbApi);
    }


    /**
     * Search Movies This is a good starting point to start finding movies on TMDb.
     *
     * @param query
     * @param searchYear   Limit the search to the provided year. Zero (0) will get all years
     * @param language     The language to include. Can be blank/null.
     * @param includeAdult true or false to include adult titles in the search
     * @param page         The page of results to return. 0 to get the default (first page)
     */
    public MovieResultsPage searchMovie(String query, Integer searchYear, String language, boolean includeAdult, Integer page) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_SEARCH, TMDB_METHOD_MOVIE);

        if (isBlank(query)) {
            throw new RuntimeException("query must not be blank");
        }

        apiUrl.addParam(PARAM_QUERY, query);

        // optional parameters

        if (searchYear != null && searchYear > 0) {
            apiUrl.addParam(PARAM_YEAR, Integer.toString(searchYear));
        }

        apiUrl.addLanguage(language);

        apiUrl.addParam(PARAM_ADULT, Boolean.toString(includeAdult));

        apiUrl.addPage(page);

        return mapJsonResult(apiUrl, MovieResultsPage.class);
    }


    /**
     * Search for TV shows by title.
     *
     * @param query
     * @param language The language to include. Can be blank/null.
     * @param page     The page of results to return. 0 to get the default (first page)
     */
    public TvResultsPage searchTv(String query, String language, Integer page) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_SEARCH, TMDB_METHOD_TV);

        if (isBlank(query)) {
            throw new RuntimeException("query must not be blank");
        }

        apiUrl.addParam(PARAM_QUERY, query);

        // optional parameters

        apiUrl.addLanguage(language);

        apiUrl.addPage(page);

        return mapJsonResult(apiUrl, TvResultsPage.class);
    }


    /**
     * Search for collections by name.
     *
     * @param query
     * @param language
     * @param page
     */
    public CollectionResultsPage searchCollection(String query, String language, Integer page) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_SEARCH, TMDB_METHOD_COLLECTION);

        if (isNotBlank(query)) {
            apiUrl.addParam(PARAM_QUERY, query);
        }

        apiUrl.addLanguage(language);

        apiUrl.addPage(page);

        return mapJsonResult(apiUrl, CollectionResultsPage.class);
    }


    /**
     * This is a good starting point to start finding people on TMDb.
     * <p/>
     * The idea is to be a quick and light method so you can iterate through people quickly.
     *
     * @param query
     * @param includeAdult
     * @param page
     */

    public TmdbPeople.PersonResultsPage searchPerson(String query, boolean includeAdult, Integer page) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_SEARCH, TmdbPeople.Companion.getTMDB_METHOD_PERSON());

        apiUrl.addParam(PARAM_QUERY, query);

        apiUrl.addParam(PARAM_ADULT, includeAdult);

        apiUrl.addPage(page);

        return mapJsonResult(apiUrl, TmdbPeople.PersonResultsPage.class);
    }


    /**
     * Search for lists by name and description.
     *
     * @param query
     * @param language
     * @param page
     */
    public TmdbAccount.MovieListResultsPage searchList(String query, String language, Integer page) {
        System.err.println("This method is part of the API but seems currently not available. " +
                "See https://www.themoviedb.org/talk/593409e3c3a36859ef01eddb#597124f8c3a3681608008424");
        
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_SEARCH, TMDB_METHOD_LIST);

        if (isNotBlank(query)) {
            apiUrl.addParam(PARAM_QUERY, query);
        }

        apiUrl.addLanguage(language);

        apiUrl.addPage(page);

        return mapJsonResult(apiUrl, TmdbAccount.MovieListResultsPage.class);
    }


    /**
     * Search Companies.
     * <p/>
     * You can use this method to search for production companies that are part of TMDb. The company IDs will map to
     * those returned on movie calls.
     * <p/>
     * http://help.themoviedb.org/kb/api/search-companies
     *
     * @param companyName
     * @param page
     */
    public CompanyResultsPage searchCompany(String companyName, Integer page) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_SEARCH, "company");
        apiUrl.addParam(PARAM_QUERY, companyName);

        apiUrl.addPage(page);

        return mapJsonResult(apiUrl, CompanyResultsPage.class);
    }


    /**
     * Search for keywords by name
     *
     * @param query
     * @param page
     */
    public KeywordResultsPage searchKeyword(String query, Integer page) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_SEARCH, "keyword");

        if (isNotBlank(query)) {
            apiUrl.addParam(PARAM_QUERY, query);
        }

        apiUrl.addPage(page);

        return mapJsonResult(apiUrl, KeywordResultsPage.class);
    }

    /**
     * Search the movie, tv show and person collections with a single query.
     * <p>Each mapped result is the same response you would get from each independent search.</p>
     * @param query
     * @param language
     * @param page
     * @return ResultsPage of Multi.
     * @see Multi
     */
    public MultiListResultsPage searchMulti(String query, String language, Integer page) {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_SEARCH, TMDB_METHOD_MULTI);

        if (isBlank(query)) {
            throw new RuntimeException("query must not be blank");
        }

        apiUrl.addParam(PARAM_QUERY, query);

        // optional parameters

        apiUrl.addLanguage(language);

        apiUrl.addPage(page);

        return mapJsonResult(apiUrl, MultiListResultsPage.class);
    }


    public static class KeywordResultsPage extends ResultsPage<Keyword> {

    }


    public static class CompanyResultsPage extends ResultsPage<Company> {

    }


    public static class CollectionResultsPage extends ResultsPage<Collection> {

    }

    public static class MultiListResultsPage extends ResultsPage<Multi> {

    }

}

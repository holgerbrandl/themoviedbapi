package info.movito.themoviedbapi;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;


public class AbstractTmdbApiTest {

    protected static TmdbApi tmdb;

    // Test data
    protected static final int ID_MOVIE_BLADE_RUNNER = 78;
    protected static final int ID_MOVIE_THE_AVENGERS = 24428;
    protected static final int ID_MOVIE_DIE_HARD = 562;

    protected static final int ID_COLLECTION_STAR_WARS = 10;

    protected static final int ID_PERSON_BRUCE_WILLIS = 62;
    protected static final String BRUCE_WILLIS_RU_BIOGRAPHY_CONTAINS = "Брюс Уиллис";
    protected static final String FOUR_ROOMS_RU_TITLE = "Четыре комнаты";

    protected static final int ID_COMPANY = 2;
    protected static final String COMPANY_NAME = "Marvel Studios";

    protected static final int ID_GENRE_ACTION = 28;

    protected static final String ID_KEYWORD = "1721";

    protected static final String LANGUAGE_DEFAULT = "";
    protected static final String LANGUAGE_ENGLISH = "en";
    protected static final String LANGUAGE_RUSSIAN = "ru";


    private static String apiKey;


    @After
    public void avoidRequestCountLimit() throws InterruptedException {
        Thread.sleep(1000);
    }

    // https://stackoverflow.com/questions/9338428/using-secret-api-keys-on-travis-ci
    // gem install --user travis  # or sudo  gem install travis
    // cd /Users/holger/projects/movieorganizer/other_project_sources/themoviedbapi
    // travis encrypt apikey=TEST_ACCOUNT_APIKEY -r holgerbrandl/themoviedbapi --add

    @BeforeClass
    public static void setUpClass() throws Exception {
        apiKey = System.getenv("apikey");

        if (StringUtils.isBlank(apiKey)) {
            String g = "Missing api key: To run test you need to provide the key as environment variable named 'apikey' " +
                    "and you have to make sure that this is key relates to a linked application";

            throw new RuntimeException(g);
        }

        tmdb = new TmdbApi(apiKey);
    }


    public static String getApiKey() {
        return apiKey;
    }


    @AfterClass
    public static void tearDownClass() throws Exception {
    }
}

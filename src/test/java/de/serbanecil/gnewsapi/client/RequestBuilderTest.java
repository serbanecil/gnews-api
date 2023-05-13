package de.serbanecil.gnewsapi.client;

import de.serbanecil.gnewsapi.exception.GnewsApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestBuilderTest {

    private final String DEFAULT_CATEGORY = "defaultCategory";
    private final String DEFAULT_LANGUAGE= "defaultLanguage";
    private final String DEFAULT_COUNTRY = "defaultCountry";
    private final int DEFAULT_MAX = 10;

    private RequestBuilder subject;

    @BeforeEach
    public void createRequestBuilder() {
        String BASE_PATH = "basePath?";
        subject = new RequestBuilder(BASE_PATH,DEFAULT_CATEGORY, DEFAULT_LANGUAGE,DEFAULT_COUNTRY ,
                DEFAULT_MAX + "");
    }

    @Test
    public void testAddCategorySuccess() {
        String result = subject.addCategory("general").getBasePath();

        assertEquals("basePath?category=general", result);
    }

    @Test
    public void testAddCategoryBlank() {
        String result = subject.addCategory("").getBasePath();

        assertEquals(String.format("basePath?category=%s", DEFAULT_CATEGORY), result);
    }

    @Test
    public void testAddCategoryException() {
        Exception exception = assertThrows(GnewsApiException.class, () -> subject.addCategory("newCategory"));

        String expectedMessage = "The category newCategory is not allowed. Please choose one of:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAddLanguageSuccess() {
        String result = subject.addLanguage("de").getBasePath();

        assertEquals("basePath?language=de", result);
    }

    @Test
    public void testAddLanguageBlank() {
        String result = subject.addLanguage("").getBasePath();

        assertEquals(String.format("basePath?language=%s", DEFAULT_LANGUAGE), result);
    }

    @Test
    public void testAddLanguageException() {
        Exception exception = assertThrows(GnewsApiException.class, () -> subject.addLanguage("newLanguage"));

        String expectedMessage = "The language newLanguage is not allowed. Please choose one of:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAddCountrySuccess() {
        String result = subject.addCountry("de").getBasePath();

        assertEquals("basePath?country=de", result);
    }

    @Test
    public void testAddCountryBlank() {
        String result = subject.addCountry("").getBasePath();

        assertEquals(String.format("basePath?country=%s", DEFAULT_COUNTRY), result);
    }

    @Test
    public void testAddCountryException() {
        Exception exception = assertThrows(GnewsApiException.class, () -> subject.addCountry("newCountry"));

        String expectedMessage = "The country newCountry is not allowed. Please choose one of:";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAddMaxArticlesSuccess() {
        String result = subject.addMaxArticles(3).getBasePath();
        assertEquals("basePath?max=3", result);
    }

    @Test
    public void testAddMaxArticlesBelowZeroAndAboveTen() {
        String result = subject.addMaxArticles(-5).getBasePath();
        assertEquals(String.format("basePath?max=%s", DEFAULT_MAX + ""), result);

        createRequestBuilder();

        result = subject.addMaxArticles(42).getBasePath();
        assertEquals(String.format("basePath?max=%s", DEFAULT_MAX + ""), result);
    }

    @Test
    public void testAddKeywordsSuccess() {
        String result = subject.addKeywords("testKeywords").getBasePath();
        assertEquals("basePath?q=\"testKeywords\"", result);
    }

    @Test
    public void testAddKeywordsBlank() {
        String result = subject.addKeywords("").getBasePath();
        assertEquals("basePath?", result);
    }

    @Test
    public void testAddKeywordsInBlankBoth() {
        String result = subject.addKeywords("", "").getBasePath();
        assertEquals("basePath?", result);
    }

    @Test
    public void testAddKeywordsInBlankKeywords() {
        String result = subject.addKeywords("", "title").getBasePath();
        assertEquals("basePath?in=title", result);
    }

    @Test
    public void testAddKeywordsInBlankIn() {
        String result = subject.addKeywords("keywords", "").getBasePath();
        assertEquals("basePath?q=\"keywords\"", result);
    }

    @Test
    public void testAddKeywordsInSuccessSingleWord() {
        String result = subject.addKeywords("keywords", "title").getBasePath();
        assertEquals("basePath?q=\"keywords\"&in=title", result);
    }

    @Test
    public void testAddKeywordsInSuccessAllWords() {
        String result = subject.addKeywords("keywords", "TITLE, CoNtent,   DEscription").getBasePath();
        assertEquals("basePath?q=\"keywords\"&in=title,content,description", result);
    }

    @Test
    public void testAddKeywordsInException() {
        Exception exception = assertThrows(GnewsApiException.class, () -> subject.addKeywords("keywords", "author"));

        String expectedMessage = "Invalid value for 'in'. Only ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAddApiKeySuccess() {
        String result = subject.addApiKey("testKey").getBasePath();
        assertEquals("basePath?apikey=testKey", result);
    }
}

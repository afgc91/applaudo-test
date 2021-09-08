package models.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static utils.JsonUtils.*;

public class ApplaudoResponse {
    public enum AssertionType{
        EQUALS_TO, EQUALS_TO_IGNORING_CASE, MATCHES, HAS_KEY, HAS_ELEMENTS
    }

    public enum ResponseCode{
        OK, NO_CONTENT, BAD_REQUEST, NOT_FOUND, CONFLICT, CREATED, INTERNAL_SERVER_ERROR, UNAUTHORIZED, FORBIDDEN
    }

    private final Response response;

    public ApplaudoResponse(Response response) {
        this.response = response;
    }

    public void assertThat(ResponseCode responseCode) {
        switch (responseCode) {
            case OK:
                Assertions.assertEquals(200, this.response.getStatusCode());
                break;
            case NO_CONTENT:
                Assertions.assertEquals(204, this.response.getStatusCode());
                break;
            case BAD_REQUEST:
                Assertions.assertEquals(400, this.response.getStatusCode());
                break;
            case NOT_FOUND:
                Assertions.assertEquals(404, this.response.getStatusCode());
                break;
            case CONFLICT:
                Assertions.assertEquals(409, this.response.getStatusCode());
                break;
            case CREATED:
                Assertions.assertEquals(201, this.response.getStatusCode());
                break;
            case INTERNAL_SERVER_ERROR:
                Assertions.assertEquals(500, this.response.getStatusCode());
                break;
            case UNAUTHORIZED:
                Assertions.assertEquals(401, this.response.getStatusCode());
                break;
            case FORBIDDEN:
                Assertions.assertEquals(403, this.response.getStatusCode());
                break;
            default:
                Assertions.fail();
        }
    }

    public void assertThat(AssertionType assertionType, String jsonPath, String evalParam) {
        String element = getObjectFromJsonAsString(response.asString(), jsonPath);
        switch (assertionType) {
            case EQUALS_TO:
                Assertions.assertEquals(evalParam, element);
                break;
            case EQUALS_TO_IGNORING_CASE:
                Assertions.assertEquals(evalParam.toUpperCase(), element.toUpperCase());
                break;
            case MATCHES:
                //TODO
                break;
            case HAS_KEY:
                Assertions.assertNotNull(element);
                break;
            case HAS_ELEMENTS:
                //TODO
                break;
            default:
                Assertions.fail();
        }
    }
}

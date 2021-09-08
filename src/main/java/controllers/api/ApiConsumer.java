package controllers.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.Variables;
import utils.JsonUtils;

import static io.restassured.RestAssured.given;

/**
 *
 * @author andresgascac@gmail.com
 *
 */
public class ApiConsumer {
    public enum HttpMethod{
        GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD
    }

    private String url;
    private String servicePath;
    private HttpMethod httpMethod;
    private ContentType contentType;

    private Map<String, String> headers;
    private Map<String, String> queryParameters;
    private Map<String, String> pathParameters;
    private String body;
    private Response response;
    private String fullServiceUrl;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiConsumer.class);

    public ApiConsumer() {
        url = Variables.API_BASE_URL;
        servicePath = Variables.CHARACTERS_PATH;
        httpMethod = HttpMethod.GET;
        contentType = ContentType.JSON;

        headers = null;
        queryParameters = null;
        pathParameters = null;
        body = null;
    }

    public void sendRequest() {
        setFullServiceUrl();

        StringBuilder requestStrBuilder = new StringBuilder();
        requestStrBuilder.append("Request:\nURL: ").append(fullServiceUrl).append("\n").append(httpMethod)
                .append("\nHeaders:\nContent-Type = ").append(contentType.toString());

        try {
            RequestSpecification specification = given().urlEncodingEnabled(false).contentType(this.contentType)
                    .relaxedHTTPSValidation();

            if (headers != null && headers.size() > 0) {
                headers.forEach((k, v) -> requestStrBuilder.append("\n").append(k).append(" = ").append(v));
                specification = specification.headers(headers);
            }

            if (body != null) {
                requestStrBuilder.append("\nBody:\n").append(JsonUtils.getPrettyJsonString(body));
                specification = specification.body(body);
            }

            switch (httpMethod) {
                case GET:
                    response = specification.when().get(fullServiceUrl).then().extract().response();
                    break;
                case POST:
                    response = specification.when().post(fullServiceUrl).then().extract().response();
                    break;
                case PUT:
                    response = specification.when().put(fullServiceUrl).then().extract().response();
                    break;
                case DELETE:
                    response = specification.when().delete(fullServiceUrl).then().extract().response();
                    break;
                case PATCH:
                    response = specification.when().patch(fullServiceUrl).then().extract().response();
                    break;
                case OPTIONS:
                    response = specification.when().options(fullServiceUrl).then().extract().response();
                    break;
                case HEAD:
                    response = specification.when().head(fullServiceUrl).then().extract().response();
            }
            StringBuilder responseStrBuilder = new StringBuilder();
            responseStrBuilder.append("Response:\n").append(response.getStatusCode()).append("\nHeaders:");
            List<Header> responseHeaders = response.headers().asList();
            responseHeaders.forEach(header -> responseStrBuilder.append("\n").append(header.getName()).append(" = ").append(header
                    .getValue()));
            String responseContent = response.asString();
            if (responseContent != null && !responseContent.equals("")) {
                responseStrBuilder.append("\nBody:\n").append(JsonUtils.getPrettyJsonString(responseContent));
            }

            LOGGER.info(requestStrBuilder.toString() + "\n" + Variables.LOGGER_LINE_DIVIDER);
            LOGGER.info(responseStrBuilder.toString() + "\n" + Variables.LOGGER_LINE_DIVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            response = null;
            LOGGER.info("The request is invalid and could not be sent to the server");
        }
    }

    public Response getResponse() {
        return this.response;
    }

    public void addHeader(String headerName, String headerValue) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(headerName, headerValue);
    }

    public void authenticate(String token) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Authorization", "Bearer " + token);
    }

    public void addQueryParameter(String parameterName, String parameterValue) {
        if (queryParameters == null) {
            queryParameters = new HashMap<>();
        }
        queryParameters.put(parameterName, parameterValue);
    }

    public void addPathParameter(String parameterName, String parameterValue) {
        if (pathParameters == null) {
            pathParameters = new HashMap<>();
        }
        pathParameters.put(parameterName, parameterValue);
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setServiceUrl(String url) {
        this.url = url;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    private void setFullServiceUrl() {
        fullServiceUrl = url + "/" + servicePath;
        setPathParameters();
        fullServiceUrl += getQueryParameters();
    }

    private void setPathParameters() {
        if (pathParameters != null && pathParameters.size() != 0) {
            for (Map.Entry<String, String> parameter : pathParameters.entrySet()) {
                setPathParameter(parameter.getKey(), parameter.getValue());
            }
        }
    }

    private void setPathParameter(String parameterKey, String parameterValue) {
        fullServiceUrl = fullServiceUrl.replace("{" + parameterKey + "}", encodeUrlPath(parameterValue));
    }

    private String getQueryParameters() {
        String queryParams;
        StringBuilder urlParams = new StringBuilder();
        if (queryParameters != null && queryParameters.size() != 0) {
            int queryParametersSize = queryParameters.size();
            int counter = 0;
            urlParams.append("?");
            for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
                urlParams.append(parameter.getKey()).append("=").append(parameter.getValue());
                if (counter < queryParametersSize - 1) {
                    urlParams.append("&");
                }
                counter += 1;
            }
        }
        queryParams = encodeUrlPath(urlParams.toString());
        return  queryParams;
    }

    private String encodeUrlPath(String path) {
        try {
            return URLEncoder.encode(path, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

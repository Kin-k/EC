package ec.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ec.steps.EconomicCalendarSteps;
import org.apache.http.HttpHeaders;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;


public class UrlShortener {

    private static final String USER_AGENT = EconomicCalendarSteps.USER_AGENT;
    private static final String X_AUTH_TOKEN = "28d6b06ceacd870ed5f18cf2";

    private final MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final WebTarget webTarget = ClientBuilder.newClient()
                                                     .register(JacksonFeature.class)
                                                     .target("https://lst.to/api/v1/link");

    public UrlShortener() {
        headers.add(HttpHeaders.USER_AGENT, USER_AGENT);
        headers.add("X-AUTH-TOKEN", X_AUTH_TOKEN);
    }

    public String shortUrl(@Nonnull String url) {
        Response response = webTarget.request(APPLICATION_JSON_TYPE)
                                     .headers(headers)
                                     .post(Entity.entity(new RequestData(url), APPLICATION_JSON_TYPE));
        checkStatus(response, "Can't create short URL for " + url);
        ResponseData responseData = response.readEntity(ResponseData.class);
        log.debug("Short URL created: {}", responseData.data.shortUrl);
        log.debug("Response DTO: {}", responseData);
        return responseData.data.shortUrl;
    }

    public void delete(@Nonnull String shortUrlOrId) {
        Matcher matcher = Pattern.compile("(https://lst\\.to/)?(.+)")
                                 .matcher(shortUrlOrId);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Wrong short URL or ID");
        }
        String id = matcher.group(2);

        Response response = webTarget.path(id)
                                     .request()
                                     .headers(headers)
                                     .delete();
        checkStatus(response, "Can't delete short URL https://lst.to/" + id);
        log.debug("Short url https://lst.to/{} deleted", id);
    }

    private void checkStatus(Response response, String errorMessage) {
        int status = response.getStatus();
        if (status != 200) {
            throw new RuntimeException(format("%s (status: %s '%s')",
                                              errorMessage, status, response.getStatusInfo()));
        }
    }

    public static class RequestData {

        public final Data data;

        public RequestData(String url) {
            data = new Data(url);
        }

        public static class Data {

            public final String type = "link";
            public final String url;

            public Data(String url) {
                this.url = url;
            }
        }
    }

    public static class DeleteData {

        public final Data data;

        public DeleteData(String shortLink) {
            data = new Data(shortLink);
        }

        public static class Data {

            @JsonProperty("short")
            public final String shortLink;

            public Data(String shortLink) {
                this.shortLink = shortLink;
            }
        }
    }

    public static class ResponseData {

        public Data data;

        public static class Data {

            public String url;
            @JsonProperty("short")
            public String shortUrl;
            public String qr;
            public String type;
            public String created;
        }

        @Override
        public String toString() {
            try {
                return new ObjectMapper().setSerializationInclusion(NON_NULL)
                                         .writeValueAsString(this);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

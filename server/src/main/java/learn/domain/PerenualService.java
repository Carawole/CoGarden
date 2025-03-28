package learn.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PerenualService {

    private final String API_URL = "https://www.perenual.com/api/v2/";

    private final String API_KEY = System.getenv("API_KEY");

    public ResponseEntity<Object> searchPlants(String query) {
        RestTemplate restTemplate = new RestTemplate();
        Result<Object> result = new Result<>();

        if (query == null || query.isEmpty()) {
            result.addErrorMessage("Query is required.", ResultType.INVALID);
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

        String url = API_URL + "species-list" + "?key=" + API_KEY + "&q=" + query;

        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            result.addErrorMessage("There wan an error with the API. Please try again later.", ResultType.INVALID);
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    public ResponseEntity<Object> getPlantDetails(int id) {
        RestTemplate restTemplate = new RestTemplate();
        Result<Object> result = new Result<>();

        if (id <= 0) {
            result.addErrorMessage("Id must be greater than 0.", ResultType.INVALID);
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

        String url = API_URL + "species/details/" + id + "?key=" + API_KEY;

        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            result.addErrorMessage("There wan an error with the API. Please try again later.", ResultType.INVALID);
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}

package atlas;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class Controller {
	private AddressRepo repo;
    public Controller(AddressRepo repo) {
        this.repo = repo;
    }
    
	@RequestMapping("/geocode")
	@ResponseBody
	public ResponseEntity<String> geocode(@RequestParam(value = "latlng") String latLong, @RequestParam(value = "key") String apiKey,
            HttpServletRequest request, HttpServletResponse response) {
		try {
			return new ResponseEntity<String>(repo.getAddress(latLong, apiKey).getFormattedAddress(), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidInputException e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/geocode/history")
	@ResponseBody
	public ResponseEntity<List<Address>> geocode(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<List<Address>>(repo.getCache(10), HttpStatus.OK);
	}
}

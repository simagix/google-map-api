package atlas;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@RequestMapping("/geocode")
	@ResponseBody
	public ResponseEntity<Address> geocode(@RequestParam(value = "latlng") String latLong, @RequestParam(value = "key") String apiKey,
            HttpServletRequest request, HttpServletResponse response) {
		try {
			return new ResponseEntity<Address>(AddressRepo.getInstance().getAddress(latLong, apiKey), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<Address>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidInputException e) {
			return new ResponseEntity<Address>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/geocode/history")
	@ResponseBody
	public ResponseEntity<Address[]> geocode(@RequestParam(value = "key") String apiKey,
            HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<Address[]>(AddressRepo.getInstance().getHistory(apiKey), HttpStatus.OK);
	}
}

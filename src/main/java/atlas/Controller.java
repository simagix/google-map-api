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
	public ResponseEntity<String> geocode(@RequestParam(value = "latlng") String latLong, @RequestParam(value = "key") String apiKey,
            HttpServletRequest request, HttpServletResponse response) {
		try {
			return new ResponseEntity<String>(AddressRepo.getInstance().getAddress(latLong, apiKey).getFormattedAddress(), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidInputException e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/geocode/history")
	@ResponseBody
	public ResponseEntity<Address[]> geocode(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<Address[]>(AddressRepo.getInstance().getHistory(), HttpStatus.OK);
	}
}

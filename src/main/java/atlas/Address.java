package atlas;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	private String latLong;
	private String formattedAddress;
	private Date eventTime;

	public Address(String latLong, String key) throws ClientProtocolException, IOException, InvalidInputException {
		this.latLong = latLong;
		this.eventTime = new Date();
		getFromGoogleMap(latLong, key);
	}

	/**
	 * Get coordinates string
	 * @return
	 */
	public String getLatLong() {
		return latLong;
	}

	/**
	 * Get formatted address
	 * @return
	 */
	public String getFormattedAddress() {
		return formattedAddress;
	}
	
	/**
	 * Get event date/time
	 * @return
	 */
	public Date getEventTime() {
		return eventTime;
	}

	private void getFromGoogleMap(String latLong, String key)
			throws InvalidInputException, ClientProtocolException, IOException {
		validateLatLong(latLong);
		String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latLong + "&key=" + key;
		System.out.println("getFromGoogleMap " + url);
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("accept", "application/json");
		HttpResponse response = client.execute(request);
		String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
		JSONObject json = new JSONObject(body);
		JSONArray array = json.getJSONArray("results");
		for (int i = 0; i < array.length();) {
			JSONObject object = array.getJSONObject(i);
			this.formattedAddress = object.getString("formatted_address");
			return;
		}

		throw new InvalidInputException();
	}
	
	private void validateLatLong(String latLong) throws InvalidInputException {
		String tokens[] = latLong.split(",");
		float latitude = Float.parseFloat(tokens[0]);
		float longitude = Float.parseFloat(tokens[1]);
		if(latitude > 90 || latitude < -90) {
			throw new InvalidInputException();
		} else if(longitude > 180 || longitude < -180) {
			throw new InvalidInputException();
		}
	}
}

package atlas;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddressRepo {
	private static AddressRepo instance = null;
	private static int NUM_CACHED = 10;
	private int index;
	private Address[] addresses;

	private AddressRepo() {
		addresses = new Address[NUM_CACHED];
	}

	public static AddressRepo getInstance() {
		if (null == instance) {
			instance = new AddressRepo();
		}
		return instance;
	}
	
	/**
	 * Get cached lookup history
	 * @return
	 */
	public Address[] getHistory() {
		return addresses;
	}

	/**
	 * Get 1st formatted address from results
	 * @param latLong
	 * @param key
	 * @return
	 * @throws InvalidInputException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Address getAddress(String latLong, String key)
			throws InvalidInputException, ClientProtocolException, IOException {
		Address addr = lookupFromCache(latLong);
		if (null == addr) {
			return getFromGoogleMap(latLong, key);
		}

		return addr;
	}

	private Address lookupFromCache(String latLong) {
		for (Address addr : addresses) {
			if (null != addr && latLong.equals(addr.getLatLong())) {
				return addr;
			}
		}

		return null;
	}

	private Address getFromGoogleMap(String latLong, String key)
			throws InvalidInputException, ClientProtocolException, IOException {
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
			Address address = new Address(latLong, object.getString("formatted_address"));
			synchronized(addresses) {
				addresses[index%NUM_CACHED] = address;
				index++;
				for (Address addr : addresses) {
					if(null != addr) {
						System.out.println("--- " + latLong + " to " + addr.getLatLong());
					}
				}
			}
			return address;
		}

		throw new InvalidInputException();
	}
}

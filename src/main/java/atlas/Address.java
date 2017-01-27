package atlas;

import java.util.Date;

public class Address {
	private String latLong;
	private String formattedAddress;
	private Date eventTime;

	public Address(String latLong, String address) {
		this.latLong = latLong;
		this.formattedAddress = address;
		this.eventTime = new Date();
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
}

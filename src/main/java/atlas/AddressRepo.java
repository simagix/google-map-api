package atlas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class AddressRepo {
	@Cacheable("addresses")
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
		return new Address(latLong, key);
	}
	
	@Autowired
    private org.springframework.cache.CacheManager cacheManager;

	public List<Address> getCache(int num) {
		Cache cache = cacheManager.getCache("addresses");
        ConcurrentHashMap<?, Address> map = (ConcurrentHashMap<?, Address>) cache.getNativeCache();
        return new ArrayList<Address>(map.values());
	}
}

package com.ip_position.ipposition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.ip_position.ipposition.city.City;
import com.ip_position.ipposition.ipinfo.IpInfo;
import com.ip_position.ipposition.ipinfo.IpInfoRepository;
import com.ip_position.ipposition.ipinfo.IpInfoService;
import com.ip_position.ipposition.latlng.LatLng;
import com.ip_position.ipposition.provider.Provider;

@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:test.properties")
class IpInfoServiceSpringBootTest {

	@Autowired
	private IpInfoService ipInfoService;

	@Autowired
	private IpInfoRepository ipInfoRepository;

	@Test
	void testGetIpInfo() {
		// Создаем тестовые данные
		City testCity = new City("Canada", "CA", "QC", "Quebec", "Montreal", "H1K");
		LatLng testLatLng = new LatLng(45.6085, -73.5493);
		Provider testProvider = new Provider("Le Groupe Videotron Ltee", "Videotron Ltee", "AS5769 Videotron Ltee");
		IpInfo testIpInfo = new IpInfo(testCity, testLatLng, "America/Toronto", testProvider, "24.48.0.1");

		// Вызываем метод, который тестируем
		ipInfoService.addNewIpInfo(testIpInfo);

		// Извлекаем результат из базы данных
		IpInfo result = ipInfoRepository.findResponseByIP("TestQuery").orElse(null);

		// Проверяем, что результат не является null
		assertNotNull(result);
		// Дополнительные проверки, в зависимости от логики вашего кода
		// Например, проверка, что результат содержит ожидаемые данные
		assertEquals("Montreal", result.getCity().getCityName());
		assertEquals(45.6085, result.getPosition().getLatitude());
		assertEquals("Le Groupe Videotron Ltee", result.getProvider().getIsp());
	}
}
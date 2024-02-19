# IP Position Project

This project provides a service for retrieving and managing information related to IP addresses. It includes components for handling city data, provider information, geographic coordinates, and IP information. The application utilizes Spring Boot and JPA for backend functionality.

## Project Structure

The project is structured as follows:

- **com.ip_position.ipposition.city:** Contains the City entity and its repository for database operations.
- **com.ip_position.ipposition.provider:** Contains the Provider entity and its repository for database operations.
- **com.ip_position.ipposition.latlng:** Contains the LatLng entity and its repository for database operations.
- **com.ip_position.ipposition.ipinfo:** Contains the IpInfo entity, its repository, and the service responsible for IP-related operations.
- **com.ip_position.ipposition.config:** Includes the configuration class (`IpInfoConfig`) for beans like `RestTemplate` and `Logger`.
- **com.ip_position.ipposition.controller:** Holds the REST controller (`IpInfoController`) for handling API endpoints related to IP information.
- **com.ip_position.ipposition.service:** Contains the service class (`IpInfoService`) responsible for business logic.

## Dependencies

- **Spring Boot:** For building the application.
- **Spring Data JPA:** For simplifying database operations.
- **Spring Web:** For building RESTful APIs.
- **H2 Database:** An embedded database for development purposes.
- **RestTemplate:** For making HTTP requests.
- **Java Logging API:** For logging information.

## Database Schema

The application uses the H2 database with tables for storing information related to cities, providers, geographic coordinates, and IP details. The database schema includes:

- **City:** Information about cities, including country, region, and zip code.
- **Provider:** Details about Internet Service Providers (ISPs).
- **LatLng:** Geographic coordinates (latitude and longitude).
- **IpInfo:** IP-related information, including city, geographic coordinates, time zone, provider, and the queried IP.

## How to Run the Project

1. Clone the repository: `git clone https://github.com/your-username/ip-position.git`
2. Navigate to the project directory: `cd ip-position`
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

The application will start, and you can access the API at `http://localhost:8080/api/ip`.

## API Endpoints

- **GET /api/ip:** Retrieve information for all IPs or a specific IP. Use the optional `ip` query parameter for a specific IP.
- **POST /api/ip:** Add new IP information. Send a JSON payload with the IP details.

## Sample Usage

```bash
# Retrieve information for all IPs
curl http://localhost:8080/api/ip

# Retrieve information for a specific IP
curl http://localhost:8080/api/ip?ip=8.8.8.8

# Add new IP information
curl -X POST -H "Content-Type: application/json" -d '{"city": {"country": "United States", "region": "CA", "cityName": "Mountain View"}, "position": {"latitude": 37.386, "longitude": -122.0838}, "timeZone": "America/Los_Angeles", "provider": {"isp": "Google", "org": "Google LLC", "asName": "AS15169"}, "query": "8.8.8.8"}' http://localhost:8080/api/ip
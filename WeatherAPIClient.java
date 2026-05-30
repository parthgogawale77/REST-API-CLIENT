import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Import JSON library for parsing API response
import org.json.JSONObject;

public class WeatherAPIClient {

    // Replace with your OpenWeather API key
    private static final String API_KEY = "be82b0cb42fac0e122e63e835cdbb0b2";

    public static void main(String[] args) {

        try {

            // City for weather search
            String city = "pune";

            // Create API URL with city name and API key
            String apiURL =
                    "https://api.openweathermap.org/data/2.5/weather?q="
                    + city
                    + "&appid="
                    + API_KEY
                    + "&units=metric";

            // Create URL object
            URL url = new URL(apiURL);

            // Open HTTP connection
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            // Set request type
            connection.setRequestMethod("GET");

            // Get HTTP response code
            int responseCode =
                    connection.getResponseCode();

            // Check if request succeeded
            if (responseCode == 200) {

                // Read response from API
                BufferedReader reader =
                        new BufferedReader(
                        new InputStreamReader(
                        connection.getInputStream()));

                String line;

                // Store complete response
                StringBuilder response =
                        new StringBuilder();

                // Read data line by line
                while ((line = reader.readLine()) != null) {

                    response.append(line);

                }

                // Close reader
                reader.close();

                // Convert JSON string into JSONObject
                JSONObject json =
                        new JSONObject(response.toString());

                // Extract city name
                String cityName =
                        json.getString("name");

                // Extract temperature
                double temp =
                        json.getJSONObject("main")
                        .getDouble("temp");

                // Extract humidity
                int humidity =
                        json.getJSONObject("main")
                        .getInt("humidity");

                // Extract weather description
                String weather =
                        json.getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("description");

                // Display formatted weather report
                System.out.println("\n===== WEATHER REPORT =====");
                System.out.println("City: " + cityName);
                System.out.println("Temperature: " + temp + " °C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Condition: " + weather);

            } else {

                // Display error if request fails
                System.out.println("HTTP Request Failed");

            }

            // Close connection
            connection.disconnect();

        }

        // Handle exceptions
        catch (Exception e) {

            e.printStackTrace();

        }
    }
}

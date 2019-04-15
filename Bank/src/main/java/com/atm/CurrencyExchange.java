package com.atm;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * A class for currency exchange using an currency exchange api.
 */
public class CurrencyExchange {

    /**
     * Helper method for reading from the a buffered reader.
     * @param rd Reader
     * @return String
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Reads the JSON from the given url
     * @param url String
     * @return JSONObject
     * @throws IOException if cannot connect to website
     * @throws JSONException if cannot read JSON
     */
   private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
       try (InputStream is = new URL(url).openStream()) {
           BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
           String jsonText = readAll(rd);
           return new JSONObject(jsonText);
       }
    }
    /**
     * Method for exchanging from Canadian dollars to USD with a fixed rate of 1 CAD to 0.75 USD.
     * @param amount int
     * @return int
     */
    public int cadToUsd(int amount) {
        //get json from web page using that thing CAD_USD assign to w
        double conversion = 0.0;
        try{JSONObject w = readJsonFromUrl("https://free.currencyconverterapi.com/api/v6/convert?q=CAD_USD&compact=ultra&apiKey=b5f7d3296e9f9ac7a390");
//        JSONObject js = JSONObject(w);
        conversion = w.getDouble("CAD_USD");
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return (int) Math.round(amount * conversion);
    }

    /**
     * Method for exchanging from USD to Canadian dollars with a fixed rate of 1 USD to 1.34 CAD.
     * @param amount int
     * @return int
     */
    public int usdToCad(int amount) {
        double conversion = 0.0;
        try{JSONObject w = readJsonFromUrl("https://free.currencyconverterapi.com/api/v6/convert?q=USD_CAD&compact=ultra&apiKey=b5f7d3296e9f9ac7a390");
//        JSONObject js = JSONObject(w);
            conversion = w.getDouble("USD_CAD");
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return (int) Math.round(amount * conversion);
    }
}


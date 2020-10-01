package me.nikox.zwierzoinator.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HasteUtil {

    /**
     * Upload tekstu na hastebina.
     *
     * @param text - Tekst do uploadu
     * @param raw - Czy tekst ma zostać uploadowany w formie raw
     * @return String z linkiem do uploadowanego tekstu.
     * @throws IOException
     */

    public String post(String text, boolean raw) throws IOException {
        byte[] postData = text.getBytes(UTF_8);
        int postDataLength = postData.length;

        String requestURL = "https://paste.letcraft.pl/documents";
        URL url = new URL(requestURL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Hastebin Java Api");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);

        String response = null;
        DataOutputStream wr;
        try {
            wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.contains("\"key\"")) {
            response = response.substring(response.indexOf(":") + 2, response.length() - 2);

            String postURL = raw ? "https://paste.letcraft.pl/raw/" : "https://paste.letcraft.pl/";
            response = postURL + response;
        }

        return response;
    }
}

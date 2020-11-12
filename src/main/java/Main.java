import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/" +
                "apod?api_key=ILmUABarOn1iMnFSOVOmpJaSFaMcup4OlN3VBCpk";
        String fileUrl = "url.txt";
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        CloseableHttpResponse response = httpClient.execute(httpGet(url));
        String body = new String(response.getEntity().getContent().readAllBytes(),
                StandardCharsets.UTF_8);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        JsonInJavaClass jsonInJavaClass = gson.fromJson(body, JsonInJavaClass.class);
        url = jsonInJavaClass.getUrl();
        File file = new File(fileUrl);
        FileWriter writer = new FileWriter(file, false);
        writer.write(String.valueOf(url));
        writer.flush();
        httpGet(url);
        String nameFile = url.substring(url.lastIndexOf("/") + 1);
        File fileImg = new File(nameFile);
        URL urlImg = new URL(url);
        InputStream in = new BufferedInputStream(urlImg.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] resp = out.toByteArray();
        FileOutputStream fos = new FileOutputStream(fileImg);
        fos.write(resp);
        fos.close();
    }

    public static HttpGet httpGet(String url) {
        HttpGet request = new HttpGet(url);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        return request;
    }
}

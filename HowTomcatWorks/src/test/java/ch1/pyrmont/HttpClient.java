package ch1.pyrmont;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class HttpClient {

    private String postContent =
            "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n" +
            "Accept-Encoding: gzip, deflate, sdch\n" +
            "Accept-Language: zh-CN,zh;q=0.8\n" +
            "Cookie: JSESSIONID=9430565298D13F8261FE422ADBE08E98\n\n";

    private Socket clientSocket = null;

    private final int BUF_SIZE = 1024;
    private byte[] clientHttpResponseBuffer = new byte[BUF_SIZE];

    private int port = 8080;

    public static void main(String[] args) {
        HttpClient client = new HttpClient();
        client.connect();
    }

    public void connect(){
        try{
            clientSocket = new Socket();
            SocketAddress address = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), port);
            clientSocket.connect(address);

            // After Connect successfully. Try to send a HTTP request to server.
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(postContent.getBytes());


            InputStream inputStream = clientSocket.getInputStream();
            inputStream.read( clientHttpResponseBuffer );

            System.out.println(new String(clientHttpResponseBuffer));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
/*
    public String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }*/

}

package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private DataOutputStream dos;
    private Map<String, String> headers;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
        headers = new HashMap<>();

    }

    public void sendRedirect(String url) throws IOException {
        response302Header(dos,url);
    }

    public void addHeader(String key, String value) {
        headers.put(key,value);
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void forward(String url) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp"+url).toPath());
        response200Header(dos,body.length);
        responseBody(dos,body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            for (String s : headers.keySet()) {
                dos.writeBytes(s+": "+headers.get(s)+"\r\n");
            }
            dos.writeBytes("\r\n");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}

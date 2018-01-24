package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private String method;
    private String path;
    private Map<String,String> params;
    private Map<String,String> headers;

    public HttpRequest(InputStream in) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

            String line=br.readLine();
            log.debug("requestLine : {}",line);

            if (line==null)
                return;

            String tokens[] = line.split(" ");
            method = tokens[0];
            path = tokens[1];

            headers=new HashMap<>();
            while (!(line=br.readLine()).equals("")) {
                log.debug("header : {}", line);

                String[] headerToken=line.split(":");
                headers.put(headerToken[0].trim(),headerToken[1].trim());
            }

            if (method.equals("GET")) {
                String getParams = path.split("\\?")[1];
                path=path.split("\\?")[0];
                params= HttpRequestUtils.parseQueryString(getParams);
            } else {
                int contentLength= Integer.parseInt(headers.get("Content-Length"));
                String body = IOUtils.readData(br, contentLength);
                params= HttpRequestUtils.parseQueryString(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String param) {
        return params.get(param);
    }
    public String getHeader(String param) {
        return headers.get(param);
    }
}

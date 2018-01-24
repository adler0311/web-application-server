package webserver.Controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.getMethod().equals("GET")) {
            doGet(request,response);
        } else {
            doPost(request,response);
        }
    }

    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            response.forward(request.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void doPost(HttpRequest request, HttpResponse response) {


    }
}

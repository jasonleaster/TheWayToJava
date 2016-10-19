package ch3.pyrmont;

import ch3.pyrmont.connector.http.HttpRequest;
import ch3.pyrmont.connector.http.HttpResponse;
import java.io.IOException;

public class StaticResourceProcessor {

  public void process(HttpRequest request, HttpResponse response) {
    try {
      response.sendStaticResource();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}

package ru.services.praktikum.scooter.qa;

import static ru.services.praktikum.scooter.qa.config.Configuration.BASE_URI;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.Before;

public class AbstractTest {

  @Before
  public void setUpForAllRequests() {
    RestAssured.requestSpecification = new RequestSpecBuilder()
      .setContentType(ContentType.JSON)
      .setBaseUri(BASE_URI)
      .build();
  }
}

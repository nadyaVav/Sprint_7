package ru.praktikumServices.qaScooter.steps;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikumServices.qaScooter.model.Courier;

public class CourierSteps {

  private static final String CREATE_COURIER = "/api/v1/courier";
  private static final String LOGIN_COURIER = "/api/v1/courier/login";
  private static final String DELETE_COURIER = "/api/v1/courier/{id}";

  @Step("Send POST request to /api/v1/courier for creating courier")
  public Response createCourier(Courier courier) {
    return given()
        .body(courier)
        .when()
        .post(CREATE_COURIER);
  }

  @Step("Send POST request to /api/v1/courier/login to login courier")
  public Response loginCourier(Courier courier) {
    return given()
        .body(courier)
        .when()
        .post(LOGIN_COURIER);
  }


  @Step("Send DELETE request to /api/v1/courier/{id} for deleting courier")
  public Response deleteCourier(Courier courier) {
    return given()
      .pathParam("id", courier.getId())
      .when()
      .delete(DELETE_COURIER);
  }
}

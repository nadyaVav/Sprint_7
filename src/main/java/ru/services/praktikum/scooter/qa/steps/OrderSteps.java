package ru.services.praktikum.scooter.qa.steps;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.services.praktikum.scooter.qa.model.Order;

public class OrderSteps {

  private static final String CREATE_ORDER = "/api/v1/orders";
  private static final String CANCEL_ORDER = "/api/v1/orders/cancel";
  private static final String GET_ALL_ORDERS = "/api/v1/orders";
  private static final String GET_ORDER_BY_ID = "/api/v1/orders/track";

  @Step("Send POST request to /api/v1/orders/cancel for creating order")
  public Response createOrder(Order order) {
    return given()
      .body(order)
      .when()
      .post(CREATE_ORDER);
  }

  @Step("Send PUT request to /api/v1/orders/cancel for canceling order")
  public Response cancelOrder(Order order) {
    return given()
      .queryParam("track", order.getTrackId())
      .when()
      .put(CANCEL_ORDER);
  }

  @Step("Send GET request to /api/v1/orders for getting lists of orders")
  public Response getAllOrders(Integer courierId, String nearestStation, Integer limit, Integer page) {

    RequestSpecification request = given();

    if (courierId != null) {
      request.queryParam("courierId", courierId);
    }
    if (nearestStation != null) {
      request.queryParam("nearestStation", nearestStation);
    }
    if (limit != null) {
      request.queryParam("limit", limit);
    }
    if (page != null) {
      request.queryParam("page", page);
    }

    return request
      .when()
      .get(GET_ALL_ORDERS);

  }

  @Step("Send GET request to /api/v1/orders/track for getting order by track")
  public Response getOrderByTrackId(Integer trackId) {

    RequestSpecification request = given();

    if (trackId != null) {
      request.queryParam("t", trackId);
    }

    return request
      .when()
      .get(GET_ORDER_BY_ID);
  }

}

package ru.services.praktikum.scooter.qa;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.Test;
import ru.services.praktikum.scooter.qa.steps.OrderSteps;

public class GetListOrdersTest extends AbstractTest {

  @Before
  public void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  @Test
  @DisplayName("[200] [GET /api/v1/orders] Request without params")
  public void shouldReturnListAllOrders() {

    new OrderSteps()
      .getAllOrders(null, "", null, null)
      .then()
      .statusCode(200)
      .body("orders", notNullValue())
      .body("orders.size()", greaterThan(0));
  }

}

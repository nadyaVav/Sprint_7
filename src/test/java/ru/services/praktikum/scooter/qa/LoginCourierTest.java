package ru.services.praktikum.scooter.qa;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static ru.services.praktikum.scooter.qa.config.ErrorMessages.COURIER_ACCOUNT_DOES_NOT_EXIST;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.services.praktikum.scooter.qa.model.Courier;
import ru.services.praktikum.scooter.qa.steps.CourierSteps;

public class LoginCourierTest extends AbstractTest {

  private CourierSteps courierSteps = new CourierSteps();
  private Courier courier;

  @Before
  public void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    courier = new Courier();
    courier.setLogin(randomAlphabetic(10));
    courier.setPassword(randomAlphabetic(10));
  }

  @Test
  @DisplayName("[200] [POST /api/v1/courier/login] Correct request to login courier should return id")
  public void shouldReturnId() {

    new CourierSteps()
      .createCourier(courier);

    new CourierSteps()
      .loginCourier(courier)
      .then()
      .statusCode(200)
      .body("id", notNullValue());

  }

  @Test
  @DisplayName("[404] [POST /api/v1/courier/login] Account doesn't exist")
  public void shouldReturnErrorIfAccountDoesNotExistToLoginCourier() {

    new CourierSteps()
      .createCourier(courier);

    String originalLogin = courier.getLogin();
    String originalPassword = courier.getPassword();

    courier.setLogin(randomAlphabetic(10));
    courier.setPassword(randomAlphabetic(10));

    new CourierSteps()
      .loginCourier(courier)
      .then()
      .statusCode(404)
      .body("code", is(404))
      .body("message", is(COURIER_ACCOUNT_DOES_NOT_EXIST));

    courier.setLogin(originalLogin);
    courier.setPassword(originalPassword);
  }

  @After
  public void tearDown() {
    Integer id = courierSteps.loginCourier(courier)
      .then().extract().body().path("id");
    courier.setId(id);
    courierSteps.deleteCourier(courier);
  }

}

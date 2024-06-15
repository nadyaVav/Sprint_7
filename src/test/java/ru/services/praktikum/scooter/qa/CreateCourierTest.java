package ru.services.praktikum.scooter.qa;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import ru.services.praktikum.scooter.qa.model.Courier;
import ru.services.praktikum.scooter.qa.steps.CourierSteps;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;
import static ru.services.praktikum.scooter.qa.config.ErrorMessages.COURIER_LOGIN_ALREADY_EXIST;

public class CreateCourierTest extends AbstractTest{

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
  @DisplayName("[201] [POST /api/v1/courier] Create courier. Correct request")
  public void shouldReturn201IfDataIsCorrectForCreatingCourier() {

    courier.setFirstName(randomAlphabetic(10));

    new CourierSteps()
      .createCourier(courier)
      .then()
      .statusCode(201)
      .body("ok", is(true));
  }

  @Test
  @DisplayName("[409] [POST /api/v1/courier] Create courier. Courier is already created")
  public void shouldReturn409IfLoginAlreadyExistForCreatingCourier() {

    courier.setFirstName(randomAlphabetic(10));

    new CourierSteps()
      .createCourier(courier);

    new CourierSteps()
      .createCourier(courier)
      .then()
      .statusCode(409)
      .body("code", is(409))
      .body("message", is(COURIER_LOGIN_ALREADY_EXIST));
  }


  @After
  public void tearDown() {
    Integer id = courierSteps.loginCourier(courier)
      .then().extract().body().path("id");
    courier.setId(id);
    courierSteps.deleteCourier(courier);
  }

}

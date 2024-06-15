package ru.services.praktikum.scooter.qa;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static ru.services.praktikum.scooter.qa.config.ErrorMessages.CREATE_COURIER_REQUEST_WITHOUT_REQ_FIELDS;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.services.praktikum.scooter.qa.model.Courier;
import ru.services.praktikum.scooter.qa.steps.CourierSteps;

@RunWith(Parameterized.class)
public class CreateCourierWithoutRequiredFieldsTest extends AbstractTest {

  private String login;
  private String password;
  private String firstName;
  private Courier courier;

  public CreateCourierWithoutRequiredFieldsTest(String login, String password, String firstName) {
    this.login = login;
    this.password = password;
    this.firstName = firstName;
  }

  @Parameterized.Parameters
  public static Object[][] getTestData() {
    // Тестовые данные
    return new Object[][] {
      {null, null, null},
      {null, null, randomAlphabetic(10)},
      {null, randomAlphabetic(10), null},
      {randomAlphabetic(10), null, null}
    };
  }

  @Test
  @DisplayName("[400] [POST /api/v1/courier] Create courier. Request without required fields.")
  public void shouldReturnErrorIfRequiredFieldIsEmptyToCreateCourier() {

    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    courier = new Courier();
    courier.setLogin(login);
    courier.setPassword(password);
    courier.setFirstName(firstName);

    new CourierSteps()
      .createCourier(courier)
      .then()
      .statusCode(400)
      .body("code", is(400))
      .body("message", is(CREATE_COURIER_REQUEST_WITHOUT_REQ_FIELDS));
  }

}


package ru.services.praktikum.scooter.qa;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.*;
import static ru.services.praktikum.scooter.qa.config.ErrorMessages.GET_ORDER_BY_TRACK_ORDER_DOES_NOT_EXIST;
import static ru.services.praktikum.scooter.qa.config.ErrorMessages.GET_ORDER_BY_TRACK_WITHOUT_PARAM;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.Before;
import ru.services.praktikum.scooter.qa.model.Colours;
import ru.services.praktikum.scooter.qa.model.Order;
import ru.services.praktikum.scooter.qa.steps.OrderSteps;

public class GetOrderByTrackTest extends AbstractTest {

  private OrderSteps orderSteps = new OrderSteps();
  private Order order;
  private final Integer NON_EXISTANT_ORDER = 323424;


  @Before
  public void setUp() {
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    Faker faker = new Faker(new Locale("ru"));
    FakeValuesService fakeValuesService = new FakeValuesService(
      new Locale("ru"), new RandomService());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    order = new Order();
    order.setFirstName(faker.name().firstName());
    order.setLastName(faker.name().lastName());
    order.setAddress(faker.address().streetAddress());
    order.setMetroStation(randomNumeric(1));
    order.setPhone(fakeValuesService.numerify("+7##########"));
    order.setRentTime(Integer.valueOf(randomNumeric(3)));
    order.setDeliveryDate(sdf.format(faker.date().future(30, TimeUnit.DAYS)));
    order.setComment(fakeValuesService.bothify("??? ### ?? ## "));
    order.setColor(new String[]{ String.valueOf(Colours.BLACK)});
  }

  @Test
  @DisplayName("[200] [GET /api/v1/orders/track] Get order by trackId")
  public void shouldReturn200AndExactOrder() {

    Integer trackId = orderSteps
      .createOrder(order)
      .then()
      .extract()
      .path("track");

    order.setTrackId(trackId);

    orderSteps.getOrderByTrackId(trackId)
      .then()
      .statusCode(200)
      .body("order.track", is(trackId));

    orderSteps.cancelOrder(order);
  }


  @Test
  @DisplayName("[400] [GET /api/v1/orders/track] Get order without trackId")
  public void shouldReturn400WhenGetOrderByTrackWithoutTrackId() {

    new OrderSteps().getOrderByTrackId(null)
      .then()
      .statusCode(400)
      .body("code", is(400))
      .body("message", is(GET_ORDER_BY_TRACK_WITHOUT_PARAM));
  }

  @Test
  @DisplayName("[404] [GET /api/v1/orders/track] Get order by track that doesn't exist")
  public void shouldReturn400WhenGetOrderThatDoesNotExist() {

    new OrderSteps().getOrderByTrackId(NON_EXISTANT_ORDER)
      .then()
      .statusCode(404)
      .body("code", is(404))
      .body("message", is(GET_ORDER_BY_TRACK_ORDER_DOES_NOT_EXIST));
  }


}

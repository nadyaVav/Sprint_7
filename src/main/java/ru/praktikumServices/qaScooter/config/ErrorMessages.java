package ru.praktikumServices.qaScooter.config;

public class ErrorMessages {

  //create courier
  public static final String COURIER_LOGIN_ALREADY_EXIST = "Этот логин уже используется. Попробуйте другой.";
  public static final String CREATE_COURIER_REQUEST_WITHOUT_REQ_FIELDS = "Недостаточно данных для создания учетной записи";
  public static final String LOGIN_COURIER_REQUEST_WITHOUT_REQ_FIELDS = "Недостаточно данных для входа";

  //login courier
  public static final String COURIER_ACCOUNT_DOES_NOT_EXIST = "Учетная запись не найдена";

  //getOrder
  public static final String GET_ORDER_BY_TRACK_WITHOUT_PARAM = "Недостаточно данных для поиска";
  public static final String GET_ORDER_BY_TRACK_ORDER_DOES_NOT_EXIST = "Заказ не найден";



}

package org.esmerilprogramming.cloverx.http;

import io.undertow.util.StatusCodes;

public enum StatusSuccess {
  
  ACCEPTED(StatusCodes.ACCEPTED , StatusCodes.ACCEPTED_STRING ),
  CREATED(StatusCodes.CREATED , StatusCodes.CREATED_STRING),
  FOUND(StatusCodes.FOUND, StatusCodes.FOUND_STRING),
  MOVED_PERMANENTLY( StatusCodes.MOVED_PERMENANTLY , StatusCodes.MOVED_PERMANENTLY_STRING),
  MULTIPLE_CHOICES( StatusCodes.MULTIPLE_CHOICES , StatusCodes.MULTIPLE_CHOICES_STRING ),
  NOT_CONTENT(StatusCodes.NO_CONTENT , StatusCodes.NO_CONTENT_STRING),
  NON_AUTHORITATIVE_INFORMATION(StatusCodes.NON_AUTHORITATIVE_INFORMATION , StatusCodes.NON_AUTHORITATIVE_INFORMATION_STRING),
  OK(StatusCodes.OK , StatusCodes.OK_STRING),
  TEMPORARY_REDIRECT ( StatusCodes.TEMPORARY_REDIRECT , StatusCodes.TEMPORARY_REDIRECT_STRING),
  USE_PROXY ( StatusCodes.USE_PROXY , StatusCodes.USE_PROXY_STRING );
  
  
  private Integer code;
  private String description;

  private StatusSuccess(Integer code, String description) {
    this.code = code;
    this.description = description;
  }

  public Integer getCode() {
    return code;
  }
  
  public String getDescription() {
    return description;
  }
  
}

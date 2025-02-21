package com.mavericksstube.maverickshub.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse <T>{
    private T data;
    private int code;
    private boolean status;
}

package com.api.vaccinationmanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel<T> {
    private Timestamp timestamp;
    private Integer status;
    private String message;
    private T data;
}

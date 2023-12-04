package com.example.sneakershopapi.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CaptureResponseDTO {
    private String id;
    private String status;
    private String amount; // you can customize this according to the structure of Capture


    public CaptureResponseDTO() {

    }




}

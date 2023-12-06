package com.mineralidentificationservice.rabbit;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ImageToRecognitionMessage {
    UUID classificationID;
    String imageBase64;
}

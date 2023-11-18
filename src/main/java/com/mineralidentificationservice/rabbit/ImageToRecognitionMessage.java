package com.mineralidentificationservice.rabbit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImageToRecognitionMessage {
    String userName;
    String imagePath;
}

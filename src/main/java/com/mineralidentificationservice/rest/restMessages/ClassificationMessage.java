package com.mineralidentificationservice.rest.restMessages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassificationMessage {
    private String authToken;
    private String stringImage;
}

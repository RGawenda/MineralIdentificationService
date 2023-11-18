package com.mineralidentificationservice.rest;


import com.google.gson.Gson;
import com.mineralidentificationservice.ProcessImage;
import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.rest.restMessages.ClassificationMessage;
import com.mineralidentificationservice.service.FoundMineralService;
import com.mineralidentificationservice.service.MineralService;
import com.mineralidentificationservice.service.UserAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@Log4j2
@RestController
@RequestMapping("/classification-rest")
public class MineralsClassificationRestController {
    private final MineralService mineralService;
    private final FoundMineralService foundMineralService;
    private final UserAccountService userAccountService;
    private ProcessImage processImage;

    public MineralsClassificationRestController(ProcessImage processImage, MineralService mineralService, FoundMineralService foundMineralService, UserAccountService userAccountService) {
        this.processImage = processImage;
        this.mineralService = mineralService;
        this.foundMineralService = foundMineralService;
        this.userAccountService = userAccountService;
    }

    @GetMapping("/mineral-classification")
    public void classificationImage(@RequestParam String param) {
        log.info("classification");
//        ClassificationMessage image;
//        Gson gson = new Gson();
//        image = gson.fromJson(param, ClassificationMessage.class);
//        String g = image.getStringImage();
//        String l = image.getAuthToken();
        processImage.process(param);
    }

    @GetMapping("/all-collection")
    public void getCollections(String auth) {
        log.info("get all collections");
    }

    @GetMapping("/collected-mineral")
    public void getMineralCollections(String auth, String mineralName) {
        log.info("get collected: " + mineralName);
    }

    //CRUD minerals
    @PostMapping("/add-mineral-to-collection")
    public ResponseEntity<FoundMineral> addMineralToCollection(FoundMineral newMineral) {
        FoundMineral savedEntity = foundMineralService.addRecord(newMineral);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }

    @GetMapping("/show-mineral/{id}")
    public ResponseEntity<FoundMineral> showMineral(@PathVariable Long id) {
        FoundMineral entity = foundMineralService.getRecord(id);
        log.info("show profile");
        return ResponseEntity.ok(entity);

    }

    @PutMapping("/edit-mineral-to-collection/{id}")
    public ResponseEntity<FoundMineral> editMineralFromCollection(@PathVariable Long id, @RequestBody FoundMineral mineral) {
        FoundMineral updateEntity = foundMineralService.updateRecord(id, mineral);
        log.info("edit");
        return ResponseEntity.ok(updateEntity);
    }

    @DeleteMapping("/delete-mineral-to-collection/{id}")
    public ResponseEntity<Object> deleteMineralFromCollection(@PathVariable Long id) {
        foundMineralService.deleteRecord(id);
        log.info("delete");
        return ResponseEntity.noContent().build();
    }

    //CRUD minerals image
    @PostMapping("/add-image")
    public void addImage(String param) {
        log.info("add"+param);
    }

    @GetMapping("/show-image")
    public void showImage() {
        log.info("show profile");
    }

    @PutMapping("/edit-image")
    public void editImage() {
        log.info("edit");
    }

    @DeleteMapping("/delete-image")
    public void deleteImage() {
        log.info("delete");
    }

    @GetMapping("/show-profile")
    public void showProfile() {
        log.info("show profile");
    }
}


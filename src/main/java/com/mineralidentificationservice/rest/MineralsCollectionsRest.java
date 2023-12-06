package com.mineralidentificationservice.rest;

import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.service.UserAccountService;
import com.mineralidentificationservice.utils.FileUtilsConv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mineralidentificationservice.service.FoundMineralService;

import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/collection-rest")
public class MineralsCollectionsRest {
    private final FoundMineralService foundMineralService;
    private final UserAccountService userAccountService;

    @Value("${users.dir}")
    private String usersDir;

    public MineralsCollectionsRest(FoundMineralService foundMineralService, UserAccountService userAccountService) {
        this.foundMineralService = foundMineralService;
        this.userAccountService = userAccountService;
    }

    @GetMapping("/all-collection")
    public void getCollections(String auth) {
        log.info("get all collections");

    }

    @GetMapping("/show-profile")
    public void showProfile() {
        log.info("show profile");
    }

    @GetMapping("/collected-mineral")
    public void getMineralCollections(String auth, String mineralName) {
        log.info("get collected: " + mineralName);
    }

    //CRUD minerals
    @PostMapping("/add-mineral-to-collection")
    public ResponseEntity<FoundMineral> addMineralToCollection(@RequestParam String userFolder, @RequestParam List<String> images, @RequestParam FoundMineral newMineral) {
        List<String> fileLocation = FileUtilsConv.saveAllImage(images, userFolder);
        newMineral.getPaths().addAll(fileLocation);
        FoundMineral savedEntity = foundMineralService.addRecord(newMineral);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }

    @GetMapping("/show-mineral")
    public ResponseEntity<FoundMineral> showMineral(@PathVariable Long id) {
        FoundMineral entity = foundMineralService.getRecord(id);

        List<String> images = FileUtilsConv.loadAllImageToBase64(entity.getPaths());
        log.info("show mineral");
        return ResponseEntity.ok(entity);
    }

    @PutMapping("/edit-mineral-to-collection")
    public ResponseEntity<FoundMineral> editMineralFromCollection(@PathVariable Long id, @RequestBody FoundMineral mineral, @RequestBody List<String> newImages,  @RequestBody List<String> deletedImages) {
        FoundMineral updateEntity = foundMineralService.updateRecord(id, mineral);
        log.info("edit");
        return ResponseEntity.ok(updateEntity);
    }

    @DeleteMapping("/delete-mineral-to-collection")
    public ResponseEntity<Object> deleteMineralFromCollection(@PathVariable Long id) {
        List<String> fileToDelete = foundMineralService.getRecord(id).getPaths();
        FileUtilsConv.deleteAllImage(fileToDelete);
        foundMineralService.deleteRecord(id);
        log.info("delete minerals "+id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/move-image")
    public void editImage() {
        log.info("edit image");
    }


}

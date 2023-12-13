package com.mineralidentificationservice.rest;

import com.mineralidentificationservice.model.*;
import com.mineralidentificationservice.rest.restMessages.MineralMessage;
import com.mineralidentificationservice.service.*;
import com.mineralidentificationservice.utils.FileUtilsConv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/collection-rest")
public class MineralsCollectionsRest {
    private final FoundMineralService foundMineralService;
    private final UserAccountService userAccountService;
    private final TagsService tagsService;
    private final MineralImagesService mineralImagesServices;
    private final TagsFoundMineralService tagsFoundMineralService;

    private final MineralService mineralService;

    @Value("${users.dir}")
    private String usersDir;

    public MineralsCollectionsRest(TagsFoundMineralService tagsFoundMineralService, MineralService mineralService, MineralImagesService mineralImagesServices, TagsService tagsService, FoundMineralService foundMineralService, UserAccountService userAccountService) {
        this.foundMineralService = foundMineralService;
        this.userAccountService = userAccountService;
        this.mineralImagesServices = mineralImagesServices;
        this.tagsService = tagsService;
        this.mineralService = mineralService;
        this.tagsFoundMineralService = tagsFoundMineralService;
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
    public ResponseEntity<FoundMineral> addMineralToCollection(@RequestParam Long id, @RequestParam MineralMessage mineral) {
        FoundMineral newMineral = mineral.getFoundMineral();
        UserAccount userAccount = userAccountService.getAccount(id);
        String username = userAccount.getUsername();

        Minerals minerals = mineralService.getMineralByName(mineral.getMineralName());

        newMineral.setMineralId(minerals);
        newMineral.setAccountId(userAccount);

        List<Tags> tagsList = new ArrayList<>();
        List<MineralImages> mineralImages = new ArrayList<>();
        List<TagsFoundMineral> tagsFoundMineralList = new ArrayList<>();

        List<String> fileLocation = FileUtilsConv.saveAllImage(mineral.getImages(), usersDir+username);

        for(String file: fileLocation){
            MineralImages mineralImages1 = new MineralImages();
            mineralImages1.setPath(file);
            mineralImagesServices.addMineralImages(mineralImages1);
            mineralImages.add(mineralImages1);
        }
        newMineral.setMineralImages(mineralImages);

        for(String tag: mineral.getTags()){
            TagsFoundMineral tagsFoundMineral = new TagsFoundMineral();
            Tags tags = tagsService.getMineralByName(tag);

            if(tags != null){

                tagsList.add(tags);

            }else {
                Tags newtags = new Tags();
                newtags.setTagName(tag);
                tagsService.addTag(newtags);
                tagsList.add(newtags);

            }
            tagsFoundMineralService.addRecord(tagsFoundMineral);
            tagsFoundMineralList.add(tagsFoundMineral);
        }

        newMineral.setTags(tagsFoundMineralList);


        FoundMineral savedEntity = foundMineralService.addMineralImages(newMineral);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }

    @GetMapping("/show-mineral")
    public ResponseEntity<MineralMessage> showMineral(@RequestParam Long id) {
        log.info("show mineral");

        FoundMineral entity = foundMineralService.getMineralImages(id);
        //List<String> images = FileUtilsConv.loadAllImageToBase64(entity.getPaths());
        MineralMessage mineralMessage =  new MineralMessage();
        mineralMessage.setFoundMineral(entity);
        //mineralMessage.setImages(images);
        return ResponseEntity.ok(mineralMessage);
    }

    @PutMapping("/edit-mineral-to-collection")
    public ResponseEntity<FoundMineral> editMineralFromCollection(@RequestParam String username, @RequestParam MineralMessage editedMineral) {
        FoundMineral mineral = editedMineral.getFoundMineral();

        List<String> deletedImages = FileUtilsConv.deleteAllImage(editedMineral.getDeletedImages(), usersDir+username);
        List<String> images = FileUtilsConv.saveAllImage(editedMineral.getImages(), usersDir+username);

        //mineral.getPaths().addAll(images);
        //mineral.getPaths().removeAll(deletedImages);

        FoundMineral updateEntity = foundMineralService.updateMineralImages(editedMineral.getId(), mineral);
        log.info("edit");
        return ResponseEntity.ok(updateEntity);
    }

    @DeleteMapping("/delete-mineral-to-collection")
    public ResponseEntity<Object> deleteMineralFromCollection(@RequestParam String username, @RequestParam Long id) {
        //List<String> fileToDelete = foundMineralService.getMineralImages(id).getPaths();
        //FileUtilsConv.deleteAllImage(fileToDelete, usersDir+username);
        //foundMineralService.deleteRecord(id);
        log.info("delete minerals "+id);
        return ResponseEntity.noContent().build();
    }


}

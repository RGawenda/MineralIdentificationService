package com.mineralidentificationservice.rest;

import com.mineralidentificationservice.model.*;
import com.mineralidentificationservice.rest.restMessages.MineralMessage;
import com.mineralidentificationservice.service.*;
import com.mineralidentificationservice.utils.FileUtilsConv;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("all-collection")
    public ResponseEntity<List<MineralMessage>> getAllEntities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("get collection page: "+page+" size: "+size);

        Pageable pageable = PageRequest.of(page, size);
        Page<FoundMineral> entities = foundMineralService.getAllEntities(pageable);
        List<MineralMessage> mineralMessages = new ArrayList<>();

        for(FoundMineral found: entities){
            log.info(found.getName());

            MineralMessage newMineral = new MineralMessage();
            newMineral.setFoundMineral(found);
            newMineral.setImagesFromDatabase(found.getMineralImages());
            List<Tags> tagsList = tagsService.getTagsByFoundMineral(found);
            newMineral.setTagsFromTagsEntityList(tagsList);

            mineralMessages.add(newMineral);
        }

        return ResponseEntity.ok(mineralMessages);
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
    public ResponseEntity<MineralMessage> addMineralToCollection(@RequestParam Long id, @RequestBody MineralMessage mineral) {
        log.info("add mineral");
        FoundMineral newMineral = mineral.getFoundMineral();
        UserAccount userAccount = userAccountService.getAccount(id);
        Minerals minerals = mineralService.getMineralByName(mineral.getMineralName());

        newMineral.setMineralId(minerals);
        newMineral.setAccountId(userAccount);

        List<MineralImages> mineralImages = new ArrayList<>();
        List<TagsFoundMineral> tagsFoundMineralList = new ArrayList<>();

        List<String> fileLocation = FileUtilsConv.saveAllImage(mineral.getImages(), usersDir + userAccount.getUsername());
        foundMineralService.addMineralImages(newMineral);

        for (String file : fileLocation) {
            MineralImages mineralImages1 = new MineralImages();
            mineralImages1.setPath(file);
            mineralImages1.setFoundMineralID(newMineral);
            mineralImagesServices.addMineralImages(mineralImages1);
            mineralImages.add(mineralImages1);
        }

        newMineral.setMineralImages(mineralImages);
        if (mineral.getTags() != null) {
            log.info("tags");
            for (String tag : mineral.getTags()) {
                log.info("add tag: " + tag);

                TagsFoundMineral tagsFoundMineral = new TagsFoundMineral();
                tagsFoundMineral.setFoundMineralId(newMineral);

                List<Tags> tags = tagsService.getTagsByName(tag);

                if (tags != null && !tags.isEmpty()) {
                    tagsFoundMineral.setTagId(tags.get(0));
                    tags.get(0).getTagsFoundMineralsList().add(tagsFoundMineral);

                } else {
                    Tags newtags = new Tags();
                    newtags.setTagName(tag);
                    tagsService.addTag(newtags);
                    List<TagsFoundMineral> tagsFoundMinerals = new ArrayList<>();
                    tagsFoundMinerals.add(tagsFoundMineral);
                    newtags.setTagsFoundMineralsList(tagsFoundMinerals);
                    tagsFoundMineral.setTagId(newtags);
                }
                tagsFoundMineralService.addRecord(tagsFoundMineral);
                tagsFoundMineralList.add(tagsFoundMineral);
            }
        }
        newMineral.setTags(tagsFoundMineralList);

        return ResponseEntity.ok(mineral);
    }

    @GetMapping("/show-mineral")
    public ResponseEntity<MineralMessage> showMineral(@RequestParam Long id) {
        log.info("show mineral");

        FoundMineral entity = foundMineralService.getMineralImages(id);
        //List<String> images = FileUtilsConv.loadAllImageToBase64(entity.getPaths());
        MineralMessage mineralMessage = new MineralMessage();
        mineralMessage.setFoundMineral(entity);
        //mineralMessage.setImages(images);
        return ResponseEntity.ok(mineralMessage);
    }

    @PutMapping("/edit-mineral-to-collection")
    public ResponseEntity<FoundMineral> editMineralFromCollection(@RequestParam String username, @RequestParam MineralMessage editedMineral) {
        FoundMineral mineral = editedMineral.getFoundMineral();

        List<String> deletedImages = FileUtilsConv.deleteAllImage(editedMineral.getDeletedImages(), usersDir + username);
        List<String> images = FileUtilsConv.saveAllImage(editedMineral.getImages(), usersDir + username);

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
        log.info("delete minerals " + id);
        return ResponseEntity.noContent().build();
    }


}

package com.mineralidentificationservice.rest;

import com.google.gson.Gson;
import com.mineralidentificationservice.model.*;
import com.mineralidentificationservice.rest.restMessages.MineralMessage;
import com.mineralidentificationservice.service.*;
import com.mineralidentificationservice.specification.FoundMineralFilter;
import com.mineralidentificationservice.utils.FileUtilsConv;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public MineralsCollectionsRest(TagsFoundMineralService tagsFoundMineralService, MineralService mineralService,
                                   MineralImagesService mineralImagesServices, TagsService tagsService,
                                   FoundMineralService foundMineralService, UserAccountService userAccountService) {
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
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String filter) {

        log.info("get collection page: " + page + " size: " + size + "filter: " + filter);

        Pageable pageable = PageRequest.of(page, size);
        FoundMineralFilter foundMineralFilter = new Gson().fromJson(filter, FoundMineralFilter.class);
        Page<FoundMineral> entities = foundMineralService.getAllByUser(foundMineralFilter, pageable);
        List<MineralMessage> mineralMessages = new ArrayList<>();

        for (FoundMineral found : entities) {
            log.info(found.getName());

            MineralMessage newMineral = new MineralMessage();
            newMineral.setFoundMineral(found);
            newMineral.setImagesFromDatabase(found.getMineralImages());
            newMineral.setMineralName(found.getMineralId().getMineralName());
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
    public ResponseEntity<MineralMessage> addMineralToCollection(@RequestBody MineralMessage mineral) {
        log.info("add mineral");
        UserAccount userAccount = getUser();

        FoundMineral newMineral = mineral.getFoundMineral();
        Minerals minerals = mineralService.getMineralByName(mineral.getMineralName());

        newMineral.setMineralId(minerals);
        newMineral.setAccountId(userAccount);

        List<MineralImages> mineralImages = new ArrayList<>();
        List<TagsFoundMineral> tagsFoundMineralList = new ArrayList<>();

        List<String> fileLocation = FileUtilsConv.saveAllImage(mineral.getImages(),
                usersDir + File.separator + userAccount.getUsername());

        foundMineralService.addFoundMineral(newMineral);

        addImagesToDatabase(newMineral, mineralImages, fileLocation);

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
            newMineral.setTags(tagsFoundMineralList);

        }

        return ResponseEntity.ok(mineral);
    }

    private void addImagesToDatabase(FoundMineral newMineral, List<MineralImages> mineralImages,
                                     List<String> fileLocation) {
        for (String file : fileLocation) {
            log.info(file);
            MineralImages mineralImages1 = new MineralImages();
            mineralImages1.setPath(file);
            mineralImages1.setFoundMineralID(newMineral);
            mineralImagesServices.addMineralImages(mineralImages1);
            mineralImages.add(mineralImages1);
        }
    }

    @GetMapping("/show-mineral")
    public ResponseEntity<MineralMessage> showMineral(@RequestParam Long id) {
        log.info("show mineral");

        FoundMineral entity = foundMineralService.getFoundMineral(id);
        //List<String> images = FileUtilsConv.loadAllImageToBase64(entity.getPaths());
        MineralMessage mineralMessage = new MineralMessage();
        mineralMessage.setFoundMineral(entity);
        //mineralMessage.setImages(images);
        return ResponseEntity.ok(mineralMessage);
    }

    @PutMapping("/edit-mineral-in-collection")
    public ResponseEntity<MineralMessage> editMineralFromCollection(@RequestBody MineralMessage editedMineralMessage) {
        FoundMineral editedMineral2 = foundMineralService.getFoundMineral(editedMineralMessage.getId());
        FoundMineral editedMineral = editedMineralMessage.getFoundMineral();

        editedMineral.setAccountId(editedMineral2.getAccountId());

        log.info("edit");

        Minerals minerals = mineralService.getMineralByName(editedMineralMessage.getMineralName());
        editedMineral.setMineralId(minerals);

        List<MineralImages> mineralImages = new ArrayList<>();
        List<TagsFoundMineral> tagsFoundMineralList = new ArrayList<>();

        UserAccount userAccount = getUser();

        if (editedMineralMessage.getDeletedImages() != null) {
            log.info("delete images");
            for (Long imageToDelete : editedMineralMessage.getDeletedImages()) {
                FileUtilsConv.deleteImage(mineralImagesServices.getMineralImage(imageToDelete).getPath());
                mineralImagesServices.deleteMineralImages(imageToDelete);
            }
        }

        if (editedMineralMessage.getImages() != null) {
            log.info("add images");
            List<String> newImages = FileUtilsConv.saveAllImage(editedMineralMessage.getImages(),
                    usersDir + File.separator + userAccount.getUsername());

            addImagesToDatabase(editedMineral, mineralImages, newImages);
        }

        foundMineralService.updateFoundMineral(editedMineralMessage.getId(), editedMineral);

        editedMineral.setMineralImages(mineralImages);

        List<Tags> currentlyTags = tagsService.getTagsByFoundMineral(editedMineral);


        if (editedMineralMessage.getTags() != null && !editedMineralMessage.getTags().isEmpty()) {
            log.info("tags");
            for (String tag : editedMineralMessage.getTags()) {
                log.info("tag: " + tag);
                TagsFoundMineral tagsFoundMineral = new TagsFoundMineral();
                tagsFoundMineral.setFoundMineralId(editedMineral);

                List<Tags> tags = tagsService.getTagsByName(tag);

                if (tags != null && !tags.isEmpty()) {
                    Optional<TagsFoundMineral> tagRelation = tagsFoundMineralService.checkIfRelations(editedMineral,
                            tags.get(0));

                    if (tagRelation.isEmpty()) {
                        log.info("add tag: " + tag);
                        tagsFoundMineral.setTagId(tags.get(0));
                        tags.get(0).getTagsFoundMineralsList().add(tagsFoundMineral);
                        tagsFoundMineralService.addRecord(tagsFoundMineral);
                        tagsFoundMineralList.add(tagsFoundMineral);

                    }

                } else {
                    log.info("create new tag: " + tag);
                    Tags newtags = new Tags();
                    newtags.setTagName(tag);
                    tagsService.addTag(newtags);
                    List<TagsFoundMineral> tagsFoundMinerals = new ArrayList<>();
                    log.info("add tag: " + tag);
                    tagsFoundMinerals.add(tagsFoundMineral);
                    newtags.setTagsFoundMineralsList(tagsFoundMinerals);
                    tagsFoundMineral.setTagId(newtags);
                    tagsFoundMineralService.addRecord(tagsFoundMineral);
                    tagsFoundMineralList.add(tagsFoundMineral);
                }

                currentlyTags.removeIf(cuTag -> tag.equals(cuTag.getTagName()));

            }
            editedMineral.setTags(tagsFoundMineralList);

            if (!currentlyTags.isEmpty()) {
                tagsFoundMineralService.deleteRecordsByFoundMineralAndTagName(editedMineral, currentlyTags);
            }

        } else {
            if (!currentlyTags.isEmpty()) {
                tagsFoundMineralService.deleteRecordsByFoundMineral(editedMineral);
            }
        }

        return ResponseEntity.ok(editedMineralMessage);
    }

    @DeleteMapping("/delete-mineral-from-collection")
    public ResponseEntity<Object> deleteMineralFromCollection(@RequestParam Long id) {
        FoundMineral mineralToDelete = foundMineralService.getFoundMineral(id);

        List<MineralImages> fileToDelete = mineralImagesServices.getMineralImages(mineralToDelete);

        for (MineralImages mineralImages : fileToDelete) {
            FileUtilsConv.deleteImage(mineralImages.getPath());
            mineralImagesServices.deleteMineralImages(mineralImages.getId());
        }

        tagsFoundMineralService.deleteRecordsByFoundMineral(mineralToDelete);

        foundMineralService.deleteFoundMineral(id);
        log.info("delete minerals " + id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-tags")
    public List<String> getTags(@RequestParam("user") String user) {
        log.info("get tags by user");
        List<String> tagsToSend = new ArrayList<>();

        Set<Tags> tagsSet = tagsService.getTagsByUser(userAccountService.getAccountByUsername(user).getId());
        for (Tags tag : tagsSet) {
            tagsToSend.add(tag.getTagName());
        }
        return tagsToSend;
    }

    private UserAccount getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userAccountService.getAccountByUsername(username);
    }

}

package com.mineralidentificationservice.rest.restMessages;


import com.mineralidentificationservice.model.FoundMineral;
import com.mineralidentificationservice.model.MineralImages;
import com.mineralidentificationservice.model.Tags;
import com.mineralidentificationservice.model.TagsFoundMineral;
import com.mineralidentificationservice.utils.FileUtilsConv;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class MineralMessage {
    private Long id;
    private String name;
    private String comment;
    private String discoveryPlace;
    private String value;
    private String weight;
    private String size;
    private String inclusion;
    private String clarity;
    private List<Long> imagesID;
    private List<String> images;
    private List<Long> deletedImages;
    private List<String> tags;
    private String mineralName;

    public void setFoundMineral(FoundMineral foundMineral) {
        this.id = foundMineral.getId();
        this.name = foundMineral.getName();
        this.comment = foundMineral.getComment();
        this.discoveryPlace = foundMineral.getDiscoveryPlace();
        this.value = foundMineral.getValue();
        this.weight = foundMineral.getWeight();
        this.size = foundMineral.getSize();
        this.inclusion = foundMineral.getInclusion();
        this.clarity = foundMineral.getClarity();
    }

    public FoundMineral getFoundMineral() {
        FoundMineral foundMineral = new FoundMineral();
        foundMineral.setId(this.id);
        foundMineral.setName(this.name);
        foundMineral.setComment(this.comment);
        foundMineral.setDiscoveryPlace(this.discoveryPlace);
        foundMineral.setValue(this.value);
        foundMineral.setWeight(this.weight);
        foundMineral.setSize(this.size);
        foundMineral.setInclusion(this.inclusion);
        foundMineral.setClarity(this.clarity);
        return foundMineral;
    }

    public void setImagesFromDatabase(List<MineralImages> images) {
        List<Long> imagesID = new ArrayList<>();
        List<String> imagesBase64 = new ArrayList<>();

        for (MineralImages image : images) {
            log.info(image.getPath());
            imagesBase64.add(FileUtilsConv.loadImage(image.getPath()));
            imagesID.add(image.getId());
        }
        setImages(imagesBase64);
        setImagesID(imagesID);
    }

    public void setTagsFromTagsEntityList(List<Tags> tagsEntityList) {
        List<String> tags = new ArrayList<>();
        for (Tags tag : tagsEntityList) {
            tags.add(tag.getTagName());
        }
        setTags(tags);
    }

}

package com.mineralidentificationservice.rest.restMessages;


import com.mineralidentificationservice.model.FoundMineral;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private List<String> images;
    private List<String> deletedImages;
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
}

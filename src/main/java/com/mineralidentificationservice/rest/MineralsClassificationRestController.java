package com.mineralidentificationservice.rest;


import com.mineralidentificationservice.ProcessImage;
import com.mineralidentificationservice.service.MineralService;
import com.mineralidentificationservice.services.ClassificationResultService;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.*;
import java.util.UUID;


@Log4j2
@RestController
@RequestMapping("/classification-rest")
public class MineralsClassificationRestController {
    private final ProcessImage processImage;
    private final MineralService mineralService;
    private final ClassificationResultService classificationResultService;

    public MineralsClassificationRestController(ClassificationResultService classificationResultService, ProcessImage processImage, MineralService mineralService) {
        this.processImage = processImage;
        this.mineralService = mineralService;
        this.classificationResultService = classificationResultService;
    }

    @GetMapping("/mineral-classification")
    public String classificationImage(@RequestParam String image) {
        UUID uuid = UUID.randomUUID();

        log.info("classification");
        processImage.process(uuid, image);
        //        List<String> mineralList = new ArrayList<>();
//        mineralService.getMineralsByNames(mineralList);
        String res = classificationResultService.getClassificationResult(uuid);

        while (null == res) {
            res = classificationResultService.getClassificationResult(uuid);
        }
        log.info(res);
        return res;
    }

}


package com.mineralidentificationservice.rest;


import com.mineralidentificationservice.service.ProcessImageService;
import com.mineralidentificationservice.model.Minerals;
import com.mineralidentificationservice.service.MineralService;
import com.mineralidentificationservice.service.ClassificationResultService;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Log4j2
@RestController
@RequestMapping("/classification-rest")
public class MineralsClassificationRestController {
    private final ProcessImageService processImage;
    private final MineralService mineralService;
    private final ClassificationResultService classificationResultService;

    public MineralsClassificationRestController(ClassificationResultService classificationResultService, ProcessImageService processImage, MineralService mineralService) {
        this.processImage = processImage;
        this.mineralService = mineralService;
        this.classificationResultService = classificationResultService;
    }

    @PostMapping("/mineral-classification")
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

    @GetMapping("/get-minerals-names")
    public List<String> getMineralsNames() {
        log.info("get all minerals");
        return mineralService.getAllMineralNames();
    }

    @GetMapping("/get-mineral")
    public Minerals getMineral(@RequestParam String mineralName) {
        log.info("get mineral by name: " + mineralName);
        return mineralService.getMineralByName(mineralName);
    }

}


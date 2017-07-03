package io.dango.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by MainasuK on 2017-7-3.
 */
@RestController
public class FaceDemoController {

    @RequestMapping(path = "/face/detect", method = RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] detect(@RequestParam("photo") MultipartFile photo) throws IOException {
        return photo.getBytes();
    }
}

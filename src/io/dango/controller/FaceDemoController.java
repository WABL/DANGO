package io.dango.controller;

import io.dango.utility.FaceDetechTool;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by MainasuK on 2017-7-3.
 */
@RestController
public class FaceDemoController {

    @RequestMapping(path = "/face/detect", method = RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] detect(@RequestParam("photo") MultipartFile photo) throws IOException {
        InputStream in = new ByteArrayInputStream(photo.getBytes());
        BufferedImage image = new FaceDetechTool().detechFace(ImageIO.read(in));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, ".jpg",out);
        return out.toByteArray();
    }
}

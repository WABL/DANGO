package io.dango.utility;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.core.io.ClassPathResource;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;

import static org.opencv.core.CvType.*;

/**
 * Created by 54472 on 2017/7/3.
 */
public class FaceDetectTool {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public Integer faceNumberForDetected = 0;

    public BufferedImage detechFace(BufferedImage bufferedImage) throws IOException {

        BufferedImage image = bufferedImage;
        int rows = image.getHeight();
        int cols = image.getWidth();
        int type = 0;
        switch (image.getType()) {
            case BufferedImage.TYPE_3BYTE_BGR:
                type = CV_8UC3;
                break;

            case BufferedImage.TYPE_BYTE_GRAY:
                type = CV_8UC1;
                break;

            case BufferedImage.TYPE_4BYTE_ABGR:
                type = CV_8UC4;
                break;
        }

        //chang image to Mat from BufferedImage
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat image_final = new Mat(rows, cols, type);
        image_final.put(0,0, pixels);

        System.out.println(getClass().getResource("lbpcascade_frontalface.xml").getPath());
        ClassPathResource resource = new ClassPathResource("lbpcascade_frontalface.xml", getClass());
        System.out.println(resource.getFile().getCanonicalPath());

        CascadeClassifier faceDetector = new CascadeClassifier(resource.getFile().getCanonicalPath());

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image_final, faceDetections);

        faceNumberForDetected = faceDetections.toArray().length;
        System.out.println(String.format("Detected %s faces", faceNumberForDetected));

        for(Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image_final, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 5);

        }

//        String filename = "faceDetection.png";
//        System.out.println(String.format("Writing %s", filename));
//        Imgcodecs.imwrite(filename, image_final);
        return matToBufferedImage(image_final);

    }

    private BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int)matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;

        matrix.get(0, 0, data);

        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;

            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;

                // bgr to rgb
                byte b;
                for(int i=0; i<data.length; i=i+3) {
                    b = data[i];
                    data[i] = data[i+2];
                    data[i+2] = b;
                }
                break;

            default:
                return null;
        }

        BufferedImage image = new BufferedImage(cols, rows, type);
        image.getRaster().setDataElements(0, 0, cols, rows, data);

        return image;
    }
}

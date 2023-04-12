package ru.liga.exchangerateforecast.serviceforgraph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.exchangerateforecast.entity.GraphResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class PrepareResponseForGraphService {
    private final Logger logger = LoggerFactory.getLogger(PrepareResponseForGraphService.class);

    public SendPhoto preparePhoto(GraphResult graphResult){
        logger.debug("Начинаю готовить фото...");
        BufferedImage objBufferedImage = graphResult.getImage();

        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", bas);
        } catch (IOException e) {
            logger.error("Ошибка записи в файл...");
            logger.error(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }

        byte[] byteArray = bas.toByteArray();

        InputStream inputStream = new ByteArrayInputStream(byteArray);
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File outputfile = new File("grath.png");

        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(outputfile));
        logger.debug("Фото для отправки готово...");
        return sendPhoto;
    }
}

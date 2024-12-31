package ru.hogwarts.schoolloohcs.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.schoolloohcs.model.Avatar;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${schoolloohcs.avatar.dir.path}")
    private String avatarsDir;  //path to avatars

    private final StudentServiceImpl studentService;
    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarService(StudentServiceImpl studentService,
                         AvatarRepository avatarRepository){
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    //Add avatar
    public void uploadAvatar(long studentId,
                             MultipartFile file) throws IOException {
        Student student = studentService.findStudent(studentId);
        Path filePath = Path.of(avatarsDir, studentId + getExtension(file.getOriginalFilename()));

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        //Streams
        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024); //for fast is
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024); //for fast os
                ){bis.transferTo(bos);}

        Avatar avatar = findAvatar(studentId);

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImageData(filePath));

        avatarRepository.save(avatar);
    }

    //generateData
    private byte[] generateImageData(Path filePath) throws IOException{
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();){
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();

        }
    }



    public Avatar findAvatar(long studentId){
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    //file extension
    public String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    //Clear DB
    public void clearDBAvatar(){
        avatarRepository.deleteAll();
    }


}

package com.rent.apartment.service;


import com.rent.apartment.database.BaseRepository;
import com.rent.apartment.database.ImageRepository;
import com.rent.apartment.dto.ApartmentDto;
import com.rent.apartment.dto.ImageDto;
import com.rent.apartment.mapper.BaseMapper;
import com.rent.apartment.mapper.ImageMapper;
import com.rent.apartment.model.Image;
import com.rent.apartment.model.User;
import com.rent.apartment.utils.Constants;
import com.rent.apartment.utils.SqlQuery;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class ImageService extends BaseService<Long, Image, ImageDto> {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public BaseRepository<Image, Long> getRepository() {
        return imageRepository;
    }

    @Override
    public BaseMapper<Long, ImageDto, Image> getBaseMapper() {
        return imageMapper;
    }

    public Image uploadProfileImage(MultipartFile file) throws IOException {
        StringBuilder profileImagesPath = new StringBuilder()
                .append(Constants.generalImagesPath)
                .append(File.separator)
                .append("profileImages")
                .append(File.separator)
                .append(userDetailsService.loggedInUser().getUsername());
        creatDirectoryIfNotExist(profileImagesPath.toString());
        String imagePath = profileImagesPath + File.separator + System.currentTimeMillis() + file.getOriginalFilename();
        return Image.builder().isCurrentProfileImage(true).path(processSingleImage(file, imagePath)).build();
    }


    public void uploadApartmentImages(MultipartFile[] imagesList, ApartmentDto apartment, User user) {
        StringBuilder apartmentImagesPath = new StringBuilder()
                .append(Constants.generalImagesPath)
                .append("apartmentImages")
                .append(Constants.separator)
                .append(user.getUsername())
                .append(apartment.getId());
        creatDirectoryIfNotExist(apartmentImagesPath.toString());
        processImagesListAsync(imagesList, apartmentImagesPath.toString(), apartment);
    }

    @SneakyThrows
    private void processImagesListAsync(MultipartFile[] imagesList, String apartmentPath, ApartmentDto apartment) {

        MultipartFile[] partOne = Arrays.copyOfRange(imagesList, 0, 21);
        MultipartFile[] partTwo = Arrays.copyOfRange(imagesList, 21, 42);
        MultipartFile[] partThree = Arrays.copyOfRange(imagesList, 42, 63);
        MultipartFile[] partFour = Arrays.copyOfRange(imagesList, 63, 84);

        CompletableFuture<List<String>> completableFuturePartOne = CompletableFuture.supplyAsync(() -> {
            ArrayList<String> paths1 = new ArrayList<>();
            for (MultipartFile multipartFile : partOne) {
                try {
                    processListOfImages(multipartFile, paths1, apartmentPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return paths1;
        });

        CompletableFuture<List<String>> completableFuturePartTwo = CompletableFuture.supplyAsync(() -> {
            ArrayList<String> paths2 = new ArrayList<>();
            for (MultipartFile multipartFile : partTwo) {
                try {
                    processListOfImages(multipartFile, paths2, apartmentPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return paths2;
        });

        CompletableFuture<List<String>> completableFuturePartThree = CompletableFuture.supplyAsync(() -> {
            ArrayList<String> paths3 = new ArrayList<>();
            for (MultipartFile multipartFile : partThree) {
                try {
                    processListOfImages(multipartFile, paths3, apartmentPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return paths3;
        });

        CompletableFuture<List<String>> completableFuturePartFour = CompletableFuture.supplyAsync(() -> {
            ArrayList<String> paths4 = new ArrayList<>();
            for (MultipartFile multipartFile : partFour) {
                try {
                    processListOfImages(multipartFile, paths4, apartmentPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return paths4;
        });

        Arrays.asList(completableFuturePartOne,
                completableFuturePartTwo,
                completableFuturePartThree,
                completableFuturePartFour)
                .parallelStream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .forEach(path -> jdbcTemplate.execute(SqlQuery.insertImageToApartment(apartment.getId(), path)));

    }

    private void processListOfImages(MultipartFile file, List<String> imagesPaths, String apartmentPath) throws IOException {
        String imagePath = apartmentPath + Constants.separator + System.currentTimeMillis() + file.getOriginalFilename();
        imagesPaths.add(imagePath);
        File convertFile = new File(imagePath);
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
    }

    private String processSingleImage(MultipartFile file, String imagePath) throws IOException {
        File convertFile = new File(imagePath);
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return imagePath;
    }


    @SneakyThrows
    private void creatDirectoryIfNotExist(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            Path path = Paths.get(directoryPath);
            Files.createDirectories(path);
        }
    }
}

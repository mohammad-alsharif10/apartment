package com.rent.apartment.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rent.apartment.database.ApartmentRepository;
import com.rent.apartment.database.BaseRepository;
import com.rent.apartment.dto.ApartmentDto;
import com.rent.apartment.mapper.ApartmentMapper;
import com.rent.apartment.mapper.BaseMapper;
import com.rent.apartment.mapper.UserMapper;
import com.rent.apartment.model.Apartment;
import com.rent.apartment.model.User;
import com.rent.apartment.response.PageResult;
import com.rent.apartment.response.SingleResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Slf4j
public class ApartmentService extends BaseService<Long, Apartment, ApartmentDto> {

    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;
    private final ImageService imageService;
    private final UserMapper userMapper;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    public BaseRepository<Apartment, Long> getRepository() {
        return apartmentRepository;
    }

    @Override
    public BaseMapper<Long, ApartmentDto, Apartment> getBaseMapper() {
        return apartmentMapper;
    }

    @Override
    public ResponseEntity<PageResult<Long, ApartmentDto>> findAll(String pageNumber, String size, String sort) {
        return super.findAll(pageNumber, size, sort);
    }

    @Override
    public ResponseEntity<SingleResult<Long, ApartmentDto>> findById(Long modelId) {
        return super.findById(modelId);
    }

    @Override
    public ResponseEntity<SingleResult<Long, ApartmentDto>> delete(Long modelId) {
        return super.delete(modelId);
    }

    @SneakyThrows
    public ResponseEntity<SingleResult<Long, ApartmentDto>> postApartment(MultipartFile[] images, String apartmentDtoString) {
        User user = userDetailsService.loggedInUser();
        ApartmentDto apartmentDto = readSaveApartmentJson(apartmentDtoString, user);
        imageService.uploadApartmentImages(images, apartmentDto, user);
        return new ResponseEntity<>(
                new SingleResult<>(false, 200, "Images have been uploaded", apartmentDto), HttpStatus.OK);
    }

    @SneakyThrows
    private ApartmentDto readSaveApartmentJson(String apartmentJson, User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        Apartment apartment = objectMapper.readValue(apartmentJson, Apartment.class);
        apartment.setUser(user);
        apartment = apartmentRepository.save(apartment);
        ApartmentDto apartmentDto = apartmentMapper.toBaseDto(apartment);
        apartmentDto.setId(apartment.getId());
        return apartmentDto;
    }
}

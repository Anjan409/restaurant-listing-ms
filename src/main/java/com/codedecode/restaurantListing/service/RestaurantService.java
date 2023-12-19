package com.codedecode.restaurantListing.service;

import com.codedecode.restaurantListing.dto.RestaurantDTO;
import com.codedecode.restaurantListing.entity.Restaurant;
import com.codedecode.restaurantListing.mapper.RestaurantMapper;
import com.codedecode.restaurantListing.repo.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepo restaurantRepo;

    public List<RestaurantDTO> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        // map it to list of DTO's

        List<RestaurantDTO> restaurantDTOList =  restaurants.stream()
                .map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant))
                .collect(Collectors.toList());
        return restaurantDTOList;
    }

    public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {

        Restaurant savedRestaurant = restaurantRepo.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));

        return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(savedRestaurant);
    }

    public ResponseEntity<RestaurantDTO> fetchById(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(id);

        if(restaurant.isPresent()){
            return new ResponseEntity<>(RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}

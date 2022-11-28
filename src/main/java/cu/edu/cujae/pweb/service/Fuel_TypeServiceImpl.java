package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.FuelDto;
import cu.edu.cujae.pweb.utils.ApiRestMapper;
import cu.edu.cujae.pweb.utils.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Fuel_TypeServiceImpl implements Fuel_TypeService {

    @Autowired
    private RestService restService;

    @Override
    public List<FuelDto> getFuels() {
        List<FuelDto> fuels = new ArrayList<>();

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<FuelDto> apiRestMapper = new ApiRestMapper<>();
            String response = (String) restService.GET("/api/v1/fuels/", params, String.class).getBody();
            fuels = apiRestMapper.mapList(response, FuelDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return fuels;
    }

    @Override
    public FuelDto getFuelById(Integer fuelId) {
        FuelDto fuel = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<FuelDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/fuels/{fuelId}");
            String uri = template.expand(fuelId).toString();
            String response = (String) restService.GET(uri, params, String.class).getBody();
            fuel = apiRestMapper.mapOne(response, FuelDto.class);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return fuel;
    }

    @Override
    public void createFuel(FuelDto fuel) {
        restService.POST("/api/v1/fuels/", fuel, String.class).getBody();
    }

    @Override
    public void updateFuel(FuelDto fuel) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        restService.PUT("/api/v1/fuels/", params, fuel, String.class).getBody();
    }

    @Override
    public void deleteFuel(Integer fuelId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        UriTemplate template = new UriTemplate("/api/v1/fuels/{fuelId}");
        String uri = template.expand(fuelId).toString();
        restService.DELETE(uri, params, String.class, null).getBody();
    }
}

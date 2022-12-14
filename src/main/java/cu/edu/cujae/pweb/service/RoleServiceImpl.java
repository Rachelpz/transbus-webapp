package cu.edu.cujae.pweb.service;

import cu.edu.cujae.pweb.dto.RoleDto;
import cu.edu.cujae.pweb.security.CurrentUserUtils;
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
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RestService restService;

	@Override
	public List<RoleDto> getRoles() {
		List<RoleDto> roleList = new ArrayList<RoleDto>();
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			ApiRestMapper<RoleDto> apiRestMapper = new ApiRestMapper<>();
			String response = (String)restService.GET("/api/v1/roles", params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
			roleList = apiRestMapper.mapList(response, RoleDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return roleList;
	}

	@Override
	public List<RoleDto> getRolesByUser(String userId) {
		List<RoleDto> roleList = new ArrayList<RoleDto>();
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			ApiRestMapper<RoleDto> apiRestMapper = new ApiRestMapper<>();

			UriTemplate template = new UriTemplate("/api/v1/roles/users/{userId}");
			String uri = template.expand(userId).toString();

			String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
			roleList = apiRestMapper.mapList(response, RoleDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return roleList;
	}

	@Override
	public RoleDto getRoleById(Long roleId) {
		RoleDto role = null;

		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			ApiRestMapper<RoleDto> apiRestMapper = new ApiRestMapper<>();

			UriTemplate template = new UriTemplate("/api/v1/roles/{id}");
			String uri = template.expand(roleId).toString();
			String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
			role = apiRestMapper.mapOne(response, RoleDto.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return role;
	}

}

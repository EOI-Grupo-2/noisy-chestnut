package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.FollowsDTO;
import com.atm.buenas_practicas_java.entities.Follows;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowsMapper extends AbstractServiceMapper<Follows, FollowsDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public FollowsDTO toDto(Follows follows) {
        FollowsDTO dto = new FollowsDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(follows, dto);

        // Mapear información de los usuarios
        if (follows.getUserFollower() != null) {
            dto.setUserFollowerId(follows.getUserFollower().getId());
            dto.setUserFollowerUsername(follows.getUserFollower().getUsername());
        }

        if (follows.getUserFollowed() != null) {
            dto.setUserFollowedId(follows.getUserFollowed().getId());
            dto.setUserFollowedUsername(follows.getUserFollowed().getUsername());
        }

        return dto;
    }

    @Override
    public Follows toEntity(FollowsDTO dto) throws Exception {
        Follows follows = new Follows();
        ModelMapper mapper = new ModelMapper();
        mapper.map(dto, follows);

        // Mapear las relaciones de usuarios
        if (dto.getUserFollowerId() != null) {
            User userFollower = userRepository.findById(dto.getUserFollowerId())
                    .orElseThrow(() -> new Exception("User follower not found with id: " + dto.getUserFollowerId()));
            follows.setUserFollower(userFollower);
        }

        if (dto.getUserFollowedId() != null) {
            User userFollowed = userRepository.findById(dto.getUserFollowedId())
                    .orElseThrow(() -> new Exception("User followed not found with id: " + dto.getUserFollowedId()));
            follows.setUserFollowed(userFollowed);
        }

        return follows;
    }
}

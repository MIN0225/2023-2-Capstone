package com.hapjusil.service;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.hapjusil.domain.PracticeRoom;
import com.hapjusil.dto.PracticeRoomRequestDTO;
import com.hapjusil.dto.PracticeRoomResponseDTO;
import com.hapjusil.repository.PracticeRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PracticeRoomService {
    @Autowired
    private PracticeRoomRepository practiceRoomRepository;

    public List<PracticeRoom> findAvailablePracticeRooms(LocalDate date) {
        return practiceRoomRepository.findAvailablePracticeRoomsByDate(date);
    }

    public List<PracticeRoomResponseDTO> findAvailablePracticeRoomsInfo(LocalDate date, LocalTime startTime) {
        return practiceRoomRepository.findAvailablePracticeRooms(date, startTime).stream()
                .map(practiceRoom -> {
                    PracticeRoomResponseDTO dto = new PracticeRoomResponseDTO();
                    dto.setId(practiceRoom.getId());
                    dto.setName(practiceRoom.getName());
                    dto.setRooms(practiceRoom.getRooms().stream()
                            .map(room -> {
                                PracticeRoomResponseDTO.RoomDTO roomDTO = new PracticeRoomResponseDTO.RoomDTO();
                                roomDTO.setId(room.getId());
                                roomDTO.setRoomName(room.getRoomName());
                                roomDTO.setPrice(room.getPrice());
                                roomDTO.setMaxCapacity(room.getMaxCapacity());
                                return roomDTO;
                            })
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Page<PracticeRoom> findPracticeRoomsByRating(int page, int size) {
        return practiceRoomRepository.findByOrderByRateDesc(PageRequest.of(page, size));
    }

    public Page<PracticeRoom> findPracticeRoomsByName(int page, int size) { // 합주실 이름순으로 정렬
        return practiceRoomRepository.findAllByOrderByNameAsc(PageRequest.of(page, size));
    }

    public PracticeRoom savePracticeRoom(PracticeRoomRequestDTO dto) { // 합주실 등록. 추후 이미지 등록 수정예정
        PracticeRoom practiceRoom = new PracticeRoom();
        practiceRoom.setName(dto.getName());
        practiceRoom.setThumbnail(dto.getThumbnail());
        practiceRoom.setPhoneNumber(dto.getPhoneNumber());
        practiceRoom.setWebsite(dto.getWebsite());
        practiceRoom.setLocation(dto.getLocation());
        practiceRoom.setRate(dto.getRate());
        return practiceRoomRepository.save(practiceRoom);
    }

    public List<PracticeRoom> searchPracticeRoomsByName(String name) {
        return practiceRoomRepository.findByNameContaining(name);
    }

    public PracticeRoom findPracticeRoomById(long id) {
        Optional<PracticeRoom> practiceRoom = practiceRoomRepository.findById(id);
        return practiceRoom.orElse(null);
    }

    public PracticeRoom updatePracticeRoom(long id, PracticeRoomRequestDTO dto) {
        PracticeRoom existingPracticeRoom = findPracticeRoomById(id);

        if (existingPracticeRoom == null) {
            throw new ResourceNotFoundException("PracticeRoom not found with id: " + id);
        }

        // Update the PracticeRoom fields with the new data from the DTO
        existingPracticeRoom.setName(dto.getName());
        existingPracticeRoom.setThumbnail(dto.getThumbnail());
        existingPracticeRoom.setPhoneNumber(dto.getPhoneNumber());
        existingPracticeRoom.setWebsite(dto.getWebsite());
        existingPracticeRoom.setLocation(dto.getLocation());
        existingPracticeRoom.setRate(dto.getRate());

        // Save the updated PracticeRoom to the repository
        return practiceRoomRepository.save(existingPracticeRoom);
    }

}

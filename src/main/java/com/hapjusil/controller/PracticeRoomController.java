package com.hapjusil.controller;

import com.hapjusil.domain.PracticeRoom;
import com.hapjusil.dto.PracticeRoomRequestDTO;
import com.hapjusil.dto.PracticeRoomResponseDTO;
import com.hapjusil.service.PracticeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/practice-rooms")
public class PracticeRoomController {
    @Autowired
    private PracticeRoomService practiceRoomService;

    @GetMapping("/available") // 날짜 입력시 합주실 조회
    public List<PracticeRoom> getAvailablePracticeRooms(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return practiceRoomService.findAvailablePracticeRooms(date);
    }

    @GetMapping("/room-info") // 날짜, 시작시간 입력시 합주실 조회
    public List<PracticeRoomResponseDTO> getAvailablePracticeRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime) {
        return practiceRoomService.findAvailablePracticeRoomsInfo(date, startTime);
    }

    @GetMapping("/sorted-by-rating")
    public Page<PracticeRoom> getPracticeRoomsByRating(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size) {
        return practiceRoomService.findPracticeRoomsByRating(page, size);
    }

    @GetMapping("/sorted-by-name") // 합주실 이름순으로 정렬
    public Page<PracticeRoom> getPracticeRoomsByName(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size) {
        return practiceRoomService.findPracticeRoomsByName(page, size);
    }

    @PostMapping
    public PracticeRoom addPracticeRoom(@RequestBody PracticeRoomRequestDTO dto) {
        return practiceRoomService.savePracticeRoom(dto);
    }

    @GetMapping("/search")
    public List<PracticeRoom> searchPracticeRooms(@RequestParam String name) {
        return practiceRoomService.searchPracticeRoomsByName(name);
    }
}
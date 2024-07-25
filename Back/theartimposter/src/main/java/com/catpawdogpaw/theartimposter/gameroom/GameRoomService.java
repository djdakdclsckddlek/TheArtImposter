package com.catpawdogpaw.theartimposter.gameroom;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.catpawdogpaw.theartimposter.config.CacheService;
import com.catpawdogpaw.theartimposter.gameroom.mapper.GameRoomMapper;
import com.catpawdogpaw.theartimposter.gameroom.model.GameRoom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameRoomService {
	private final GameRoomMapper gameRoomMapper;
	private final CacheService cacheService;

	public GameRoom findOrCreateRoom() {
		List<GameRoom> rooms = gameRoomMapper.findAll();
		GameRoom gameRoom = rooms.stream().filter(room -> room.getDestroyAt().isAfter(LocalDateTime.now())
				&& cacheService.getPlayerCount(room.getGameRoomId().toString()) < 5).findFirst().orElse(null);

		if (gameRoom == null) {
			gameRoom = new GameRoom();
			gameRoom.setGameSettingId(1L); // Example gameSettingId
			gameRoom.setCreatedAt(LocalDateTime.now());
			gameRoom.setDestroyAt(LocalDateTime.now().plusHours(1)); // Room will be destroyed in 1 hour

			gameRoomMapper.createRoom(gameRoom);
			cacheService.addGameRoom(gameRoom);
		}
		return gameRoom;
	}

	public void joinRoom(Long gameRoomId) {
		cacheService.incrementPlayerCount(gameRoomId.toString());
	}

	public void leaveRoom(Long gameRoomId) {
		cacheService.decrementPlayerCount(gameRoomId.toString());
		int playerCount = cacheService.getPlayerCount(gameRoomId.toString());

		if (playerCount == 0) {
			gameRoomMapper.deleteRoom(gameRoomId);
			cacheService.removeGameRoom(gameRoomId.toString());
		}
	}
}

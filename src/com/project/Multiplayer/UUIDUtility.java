package com.project.Multiplayer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class UUIDUtility {

	public UUIDUtility() {}
	

	public UUID generateTypeUUID() {
		 
	    long most64SigBits = get64MostSignificantBits();
	    long least64SigBits = get64LeastSignificantBits();
	 
	    return new UUID(most64SigBits, least64SigBits);
	}
	
	private long get64LeastSignificantBits() {
	    Random random = new Random();
	    long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
	    long variant3BitFlag = 0x8000000000000000L;
	    return random63BitLong + variant3BitFlag;
	}
	 
	private long get64MostSignificantBits() {
	    LocalDateTime start = LocalDateTime.of(1582, 10, 15, 0, 0, 0);
	    Duration duration = Duration.between(start, LocalDateTime.now());
	    long seconds = duration.getSeconds();
	    long nanos = duration.getNano();
	    long timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
	    long least12SignificatBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
	    long version = 1 << 12;
	    return (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificatBitOfTime;
	}
	
}

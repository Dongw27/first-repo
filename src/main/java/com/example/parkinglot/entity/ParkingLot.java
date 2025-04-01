package com.example.parkinglot.entity;

public class ParkingLot {

    public String parkingLotNo;         // 주차장관리번호
    public String parkingLotName;       // 주차장명
    public double latitude;             // 위도
    public double longitude;            // 경도

    public ParkingLot(String parkingLotNo, String parkingLotName, double latitude, double longitude) {
        this.parkingLotNo = parkingLotNo;
        this.parkingLotName = parkingLotName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

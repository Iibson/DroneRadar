export interface Drone {
    registrationNumber: string;
    country: string;
    fuel: number;
    identification: number;
    identificationLabel: number;
    model: string;
    operator: string;
    sign: string;
    type: string;
    currentFlight: CurrentFlight;
    flights: FlightBasicInfo[];
}
export interface CurrentFlight {
    altitude: number;
    heading: number;
    speed: number;
}

export interface FlightBasicInfo {
    id: number;
    takeOff: Date;
    landing: Date;
    distance: number;
    startCoordinate: Coordinate;
    endCoordinate: Coordinate;
    avgAltitude: number;
}

export interface Coordinate {
    direction: string;
    latitude: number;
    longitude: number;
}
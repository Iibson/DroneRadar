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
}
export interface CurrentFlight {
    altitude: number;
    heading: number;
    speed: number;
}
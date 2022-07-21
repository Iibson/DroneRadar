import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Drone } from 'src/shared/model/drone';

@Injectable({
  providedIn: 'root'
})
export class DroneService {
  public baseUrl = "http://localhost:8080/drone/";

  currentDroneInfo: Subject<any> = new Subject<any>();
  constructor(private httpClient: HttpClient) { }

  notifyAboutDroneInfo(value: Drone) {
    this.currentDroneInfo.next(value);
  }
 
    

  public getDroneInfoById(droneId: number | string): Observable<Drone> {
    return this.httpClient.get<Drone>(this.baseUrl+droneId);
  }
}

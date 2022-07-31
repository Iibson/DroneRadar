import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Pageable} from "../shared/model/pageable";
import {DroneBasicDataDto} from "../shared/model/drone-basic-data-dto";

@Injectable({
  providedIn: 'root'
})
export class RestService {
  url : string = "http://localhost:8080";

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };


  constructor(private http: HttpClient) { }

  getDronesBasicData() : Observable<Pageable> {
    return this.http.get<Pageable>(this.url + "/drones?page=1&elements=20", this.httpOptions);
  }

  getDronesBasicDataByIds(registrationNumbers : string[]) : Observable<DroneBasicDataDto[]> {
    return this.http.get<DroneBasicDataDto[]>(this.url + "/drones-ids?page=1&elements=20&drones=" + registrationNumbers.reduce((a,b) => a + "," + b, ""), this.httpOptions);
  }
}

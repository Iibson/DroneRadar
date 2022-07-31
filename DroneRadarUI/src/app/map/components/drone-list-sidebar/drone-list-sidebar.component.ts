import { Component, OnInit } from '@angular/core';
import { MapService } from '../../services/map/map.service';
import { SidebarsService } from '../../services/sidebars/sidebars.service';
import {RestService} from "../../../rest.service";
import {DroneBasicDataDto} from "../../../../shared/model/drone-basic-data-dto";
import {IInfiniteScrollEvent} from "ngx-infinite-scroll";

@Component({
  selector: 'app-drone-list-sidebar',
  templateUrl: './drone-list-sidebar.component.html',
  styleUrls: ['./drone-list-sidebar.component.scss'],
})
export class DroneListSidebarComponent implements OnInit {

  sidebarsService: SidebarsService;
  mapService: MapService;
  restService: RestService;

  chunkSize: number = 15;
  dronesList: DroneBasicDataDto[] = [];

  iconSvgStyle = {
    'width.px': 48,
    'height.px': 48,
  };
  scrollPanelStyle = { width: '100%', height: '100%' };
  get visible(): boolean {
    return this.sidebarsService.droneListSidebarVisible;
  }

  set visible(value: boolean) {
    this.refresh();
    this.sidebarsService.droneListSidebarVisible = value;
  }

  get dronesRegistrationNumbers(): string[]{
    return this.mapService.currentMapSnapshot.map(drone => drone.basicInfoString);
  }
  getDronesList() {
    return this.restService.getDronesBasicDataByIds(this.dronesRegistrationNumbers.slice(0,Math.min(this.chunkSize, this.dronesRegistrationNumbers.length))).subscribe(response => {
      this.dronesList = response as DroneBasicDataDto[];
    })
  }

  constructor(sidebarsService: SidebarsService, mapService: MapService, restService : RestService) {
    this.sidebarsService = sidebarsService;
    this.mapService = mapService;
    this.restService = restService;
  }

  ngOnInit(): void {
    this.getDronesList();
  }

  close(): void {
    this.visible = false;
  }

  refresh(): void {
    this.mapService.refreshMapSnapshot();
    this.getDronesList();
  }

  onScrollDown($event: IInfiniteScrollEvent) {
    return this.restService.getDronesBasicDataByIds(this.dronesRegistrationNumbers.slice(this.dronesList.length, Math.min(this.dronesList.length + this.chunkSize, this.dronesRegistrationNumbers.length))).subscribe(response => {
      for(let drone of response as DroneBasicDataDto[])
        this.dronesList.push(drone);
    })
  }

  onScrollUp($event: IInfiniteScrollEvent) {
  }
}

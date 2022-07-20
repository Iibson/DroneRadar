import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/internal/Subscription';
import { Drone } from 'src/shared/model/drone';
import { IMapObjectInfoDto } from 'src/shared/model/map-object-info-dto.model';
import { DroneService } from '../../services/drone/drone.service';
import { MapService } from '../../services/map/map.service';
import { SidebarsService } from '../../services/sidebars/sidebars.service';

@Component({
  selector: 'app-drone-info-sidebar',
  templateUrl: './drone-info-sidebar.component.html',
  styleUrls: ['./drone-info-sidebar.component.scss']
})
export class DroneInfoSidebarComponent implements OnInit {

  sidebarsService: SidebarsService;
  mapService: MapService;

  droneInfo: Drone = {} as Drone;
  
  droneInfoSubscriptionL: Subscription = this.droneService.currentDroneInfo.subscribe(res => {
    this.droneInfo =res;
  });
  
    
  iconSvgStyle = {
    'width.px': 48,
    'height.px': 48,
  };

  scrollPanelStyle = { width: '100%', height: '100%' };
  get visible(): boolean {
    return this.sidebarsService.droneInfoSidebarVisible;
  }

  set visible(value: boolean) {
    this.sidebarsService.droneInfoSidebarVisible = value;
  }

  get dronesList(): IMapObjectInfoDto[] {
    return this.mapService.currentMapSnapshot;
  }

  constructor(sidebarsService: SidebarsService, mapService: MapService, private droneService: DroneService) {
    this.sidebarsService = sidebarsService;
    this.mapService = mapService;
  }

  ngOnInit(): void {}

  close(): void {
    this.visible = false;
  }

}

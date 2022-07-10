import { Component, OnInit } from '@angular/core';
import { IMapObjectInfoDto } from 'src/shared/model/map-object-info-dto.model';
import { MapService } from '../../services/map/map.service';
import { SidebarsService } from '../../services/sidebars/sidebars.service';

@Component({
  selector: 'app-drone-list-sidebar',
  templateUrl: './drone-list-sidebar.component.html',
  styleUrls: ['./drone-list-sidebar.component.scss'],
})
export class DroneListSidebarComponent implements OnInit {
  sidebarsService: SidebarsService;
  mapService: MapService;

  iconSvgStyle = {
    'width.px': 48,
    'height.px': 48,
  };
  scrollPanelStyle = { width: '100%', height: '100%' };
  get visible(): boolean {
    return this.sidebarsService.droneListSidebarVisible;
  }

  set visible(value: boolean) {
    this.sidebarsService.droneListSidebarVisible = value;
  }

  get dronesList(): IMapObjectInfoDto[] {
    return this.mapService.currentMapSnapshot;
  }

  constructor(sidebarsService: SidebarsService, mapService: MapService) {
    this.sidebarsService = sidebarsService;
    this.mapService = mapService;
  }

  ngOnInit(): void {}

  close(): void {
    this.visible = false;
  }

  refresh(): void {
    this.mapService.refreshMapSnapshot();
  }
}

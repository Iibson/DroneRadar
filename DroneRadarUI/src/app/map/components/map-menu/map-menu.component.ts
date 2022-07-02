import { Component, OnInit } from '@angular/core';
import { MapService } from '../../services/map/map.service';

@Component({
  selector: 'app-map-menu',
  templateUrl: './map-menu.component.html',
  styleUrls: ['./map-menu.component.scss']
})
export class MapMenuComponent implements OnInit {

  hideDronesButtonPushed: boolean = true;
  svgStyle = {
    'width.px': 48,
    'height.px': 48,
  }

  constructor(private _mapService: MapService) { }

  ngOnInit(): void {
  }

  changeDisplayDrones() {
    this.hideDronesButtonPushed = !this.hideDronesButtonPushed;
    this._mapService.changeDisplayDrones(this.hideDronesButtonPushed);
  }

  clearPaths = () => console.error("Not Implemented!");

}

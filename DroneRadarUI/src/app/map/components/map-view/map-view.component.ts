import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import * as L from 'leaflet';
import { Subscription } from 'rxjs';
import { MapService } from '../../services/map/map.service';
import { RotatedMarker } from 'leaflet-marker-rotation';
import { SidebarsService } from '../../services/sidebars/sidebars.service';
import { DroneService } from '../../services/drone/drone.service';
@Component({
  selector: 'app-map-view',
  templateUrl: './map-view.component.html',
  styleUrls: ['./map-view.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class MapViewComponent implements AfterViewInit, OnInit, OnDestroy {
  map!: L.Map;
  markerGroup!: L.LayerGroup<any>;
  markersMap: Map<number, RotatedMarker> = new Map();
  subs: Subscription[] = [];
  showDroneInfoExpandingButton: boolean = false;

  planeIcon = L.icon({
    iconUrl: 'assets/plane-icon.png',
    iconSize: [32, 32], // size of the icon
    iconAnchor: [16, 16], // point of the icon which will correspond to marker's location
    popupAnchor: [0, -16], // point from which the popup should open relative to the iconAnchor
  });

  constructor(
    private _mapService: MapService,
    private _sidebarsService: SidebarsService,
    private _droneService: DroneService
  ) {}

  ngOnInit(): void {
    this.subs.push(this.observeMapData(), this.observeDroneDisplay());
  }

  ngAfterViewInit(): void {
    this.initMap();
    this.getUserLocation();
  }

  ngOnDestroy(): void {
    this.subs.forEach((subscription) => subscription.unsubscribe());
  }

  initMap(): void {
    this.map = L.map('map', {
      center: [50, 20],
      zoom: 10,
    });

    const tiles = L.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 18,
        minZoom: 3,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );

    tiles.addTo(this.map);
    this.markerGroup = L.layerGroup().addTo(this.map);
  }

  getUserLocation(): void {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          if (position) {
            const longitude = position.coords.longitude;
            const latitude = position.coords.latitude;
            this.map?.flyTo({
              lat: latitude,
              lng: longitude,
            });
          }
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      console.log('Geolocation is not supported by this browser.');
    }
  }

  observeDroneDisplay = (): Subscription =>
    this._mapService
      .getDisplayDronesObservable()
      .subscribe((bool) =>
        this.markersMap.forEach((val, _) => val.setOpacity(100 * Number(bool)))
      );

  observeMapData(): Subscription {
    return this._mapService.observeMapData.subscribe((objects) => {
      this._mapService.latestMapData = objects;
      objects.forEach((obj) => {
        const existingMarker = this.markersMap.get(obj.objectId);
        if (existingMarker) {
          existingMarker.setLatLng({ lat: obj.lat, lng: obj.lon });
          existingMarker.setRotationAngle(obj.heading);
        } else {
          this.markersMap.set(
            obj.objectId,
            new RotatedMarker([obj.lat, obj.lon], {
              icon: this.planeIcon,
              rotationAngle: obj.heading,
              rotationOrigin: 'center',
            })
              .bindPopup(obj.basicInfoString)
              .addTo(this.markerGroup)
              .on('click',(event) => this.markerClick(this, obj.objectId))
          );
        }
      });
      this.markersMap.forEach((value, key) => {
        if (objects.findIndex((x) => x.objectId === key) === -1) {
          this.markerGroup.removeLayer(value);
          this.markersMap.delete(key);
        }
      });
    });
  }

  showDroneList(): void {
    this._sidebarsService.droneListSidebarVisible = true;
  }

  markerClick(event: any, droneId: number) {
    this._droneService.getDroneInfoById(droneId).subscribe(res => {
      this._sidebarsService.droneInfoSidebarVisible = true;
      this.showDroneInfoExpandingButton = true;
      this._droneService.notifyAboutDroneInfo(res);
      console.log(res)
    });
  }
  showSelectedDoneInfo = () => {
    this._sidebarsService.droneInfoSidebarVisible = true;
  }
  showFiltersSidebar(): void{
    this._sidebarsService.filtersSidebarVisible = true;
  }
}

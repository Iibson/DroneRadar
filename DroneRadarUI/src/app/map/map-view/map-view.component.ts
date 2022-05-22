import { AfterViewInit, Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { MapService } from '../map.service';

@Component({
  selector: 'app-map-view',
  templateUrl: './map-view.component.html',
  styleUrls: ['./map-view.component.scss'],
})
export class MapViewComponent implements AfterViewInit, OnInit {
  map!: L.Map;
  markerGroup!: L.LayerGroup<any>;
  planeIcon = L.icon({
    iconUrl: 'assets/plane-icon.png',
    iconSize: [32, 32], // size of the icon
    iconAnchor: [16, 16], // point of the icon which will correspond to marker's location
    popupAnchor: [0,-16], // point from which the popup should open relative to the iconAnchor
  });
  constructor(private _mapService: MapService) {
  }

  ngOnInit(): void {
    this._mapService.observeMapData().subscribe(objects => {
      console.log(objects);
      if(this.markerGroup) {
        this.map.removeLayer(this.markerGroup);
      }
      this.markerGroup = L.layerGroup().addTo(this.map);
      objects.forEach(obj => {
        const marker = L.marker([obj.lat, obj.lon], { icon: this.planeIcon }).bindPopup(obj.basicinfoString);
        marker.addTo(this.markerGroup);
      });
    })
  }

  ngAfterViewInit(): void {
    this.initMap();
    this.getUserLocation();
  }

  initMap(): void {
    this.map = L.map('map', {
      center: [0, 0],
      zoom: 10,
      worldCopyJump: true
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
    L.marker([51.5, -0.09], { icon: this.planeIcon })
      .bindPopup('XD')
      .addTo(this.map);
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
}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapViewComponent } from './components/map-view/map-view.component';
import { MapRoutingModule } from './map-routing.module';
import { MapService } from './services/map/map.service';
import { MapMenuComponent } from './components/map-menu/map-menu.component';
import { AngularSvgIconModule } from 'angular-svg-icon';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [MapViewComponent, MapMenuComponent],
  imports: [CommonModule, MapRoutingModule, HttpClientModule, AngularSvgIconModule.forRoot()],
  providers: [MapService]
})
export class MapModule {}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapViewComponent } from './components/map-view/map-view.component';
import { MapRoutingModule } from './map-routing.module';
import { MapService } from './services/map/map.service';
import { MapMenuComponent } from './components/map-menu/map-menu.component';
import { AngularSvgIconModule } from 'angular-svg-icon';
import { HttpClientModule } from '@angular/common/http';
import { MapExpandingSideButtonComponent } from './components/map-expanding-side-button/map-expanding-side-button.component';
import { DroneListSidebarComponent } from './components/drone-list-sidebar/drone-list-sidebar.component';
import { SidebarModule } from 'primeng/sidebar';
import { SidebarsService } from './services/sidebars/sidebars.service';
import { ButtonModule } from 'primeng/button';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { DroneService } from './services/drone/drone.service';
import { DroneInfoSidebarComponent } from './components/drone-info-sidebar/drone-info-sidebar.component';

@NgModule({
  declarations: [
    MapViewComponent,
    MapMenuComponent,
    MapExpandingSideButtonComponent,
    DroneListSidebarComponent,
    DroneInfoSidebarComponent,
  ],
  imports: [
    CommonModule,
    MapRoutingModule,
    HttpClientModule,
    AngularSvgIconModule.forRoot(),
    SidebarModule,
    ButtonModule,
    ScrollPanelModule,
  ],
  providers: [MapService, SidebarsService, DroneService],
})
export class MapModule {}

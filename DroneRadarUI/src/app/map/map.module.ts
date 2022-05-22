import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapViewComponent } from './map-view/map-view.component';
import { MapRoutingModule } from './map-routing.module';
import { MapService } from './map.service';

@NgModule({
  declarations: [MapViewComponent],
  imports: [CommonModule, MapRoutingModule],
  providers: [MapService]
})
export class MapModule {}

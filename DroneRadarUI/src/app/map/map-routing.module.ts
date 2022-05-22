import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MapViewComponent } from './map-view/map-view.component';

const routes: Routes = [
  {
    path: 'map-view',
    component: MapViewComponent,
  },
  {
    path: '**',
    redirectTo: 'map-view',
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MapRoutingModule {}

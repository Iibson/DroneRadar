import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MapService } from '../../services/map/map.service';
import { SidebarsService } from '../../services/sidebars/sidebars.service';

@Component({
  selector: 'app-filters-sidebar',
  templateUrl: './filters-sidebar.component.html',
  styleUrls: ['./filters-sidebar.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class FiltersSidebarComponent implements OnInit {
  scrollPanelStyle = { width: '100%', height: '100%' };
  filters!: FormGroup;

  get visible(): boolean {
    return this._sidebarsService.filtersSidebarVisible;
  }

  set visible(value: boolean) {
    this._sidebarsService.filtersSidebarVisible = value;
  }

  constructor(private _sidebarsService: SidebarsService, private _mapService: MapService) {
    this.createFiltersFormGroup();
  }

  ngOnInit(): void {}

  close(): void {
    this.visible = false;
  }
  createFiltersFormGroup(): void{
    this.filters = new FormGroup({
      id: new FormControl(''),
      idExt: new FormControl(''),
      country: new FormControl(''),
      type: new FormControl(''),
      model: new FormControl(''),
      registrationNumber: new FormControl(''),
      fuelState: new FormControl(''),
      signal: new FormControl(''),
      frequency: new FormControl(''),
      marking: new FormControl(''),
    });
  }

  resetFilters(): void{
    this.createFiltersFormGroup();
    this._mapService.resetFilters();
  }

  applyFilters(): void{
    this._mapService.applyFilters(this.filters.getRawValue());
  }
}

import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CompareType } from 'src/shared/model/compare-type.enum';
import { FilterDto } from 'src/shared/model/filters-dto.model';
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
  fuelStateRangeValues: any;

  get visible(): boolean {
    return this._sidebarsService.filtersSidebarVisible;
  }

  set visible(value: boolean) {
    this._sidebarsService.filtersSidebarVisible = value;
  }

  get fuelState(): number[] {
    return (this.filters.get('fuelState') as FormControl).value as number[];
  }

  constructor(
    private _sidebarsService: SidebarsService,
    private _mapService: MapService
  ) {
    this.createFiltersFormGroup();
  }

  ngOnInit(): void {}

  close(): void {
    this.visible = false;
  }
  createFiltersFormGroup(): void {
    this.filters = new FormGroup({
      identification: new FormControl(''),
      country: new FormControl(''),
      type: new FormControl(''),
      model: new FormControl(''),
      registrationNumber: new FormControl(''),
      fuelState: new FormControl([0, 10000]),
      signal: new FormControl(''),
      frequency: new FormControl(''),
      marking: new FormControl(''),
    });
  }

  resetFilters(): void {
    this.createFiltersFormGroup();
    this._mapService.resetFilters();
  }

  applyFilters(): void {
    const formRawValue = this.filters.getRawValue();
    const filtersDto: FilterDto[] = Object.keys(formRawValue)
      .map((propertyName) =>
        this.mapPropertyToFilter(formRawValue, propertyName)
      )
      .filter((filter) => filter.compareValues.every((value) => value));
    this._mapService.applyFilters(filtersDto);
  }

  mapPropertyToFilter(formRawValue: any, propertyName: string): FilterDto {
    const betweenProperties = ['fuelState'];
    let compareType = CompareType.Contains;
    let compareValues = [formRawValue[propertyName]];
    if (betweenProperties.indexOf(propertyName) !== -1) {
      compareType = CompareType.Between;
      compareValues = formRawValue[propertyName].map((x: number) =>
        x.toString()
      );
    }
    return {
      propertyName: propertyName,
      compareType: compareType,
      compareValues: compareValues,
    };
  }
}

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SidebarsService {
  droneListSidebarVisible: boolean;
  droneInfoSidebarVisible: boolean;
  filtersSidebarVisible: boolean;

  constructor() {
    this.droneListSidebarVisible = false;
    this.droneInfoSidebarVisible = false;
    this.filtersSidebarVisible = false;
  }
}

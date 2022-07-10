import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SidebarsService {
  droneListSidebarVisible: boolean;

  constructor() {
    this.droneListSidebarVisible = false;
  }
}

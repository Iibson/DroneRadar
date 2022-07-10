import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DroneListSidebarComponent } from './drone-list-sidebar.component';

describe('DroneListSidebarComponent', () => {
  let component: DroneListSidebarComponent;
  let fixture: ComponentFixture<DroneListSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DroneListSidebarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DroneListSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

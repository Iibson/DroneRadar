import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DroneInfoSidebarComponent } from './drone-info-sidebar.component';

describe('DroneInfoSidebarComponent', () => {
  let component: DroneInfoSidebarComponent;
  let fixture: ComponentFixture<DroneInfoSidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DroneInfoSidebarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DroneInfoSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

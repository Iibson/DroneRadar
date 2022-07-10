import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapExpandingSideButtonComponent } from './map-expanding-side-button.component';

describe('MapExpandingSideButtonComponent', () => {
  let component: MapExpandingSideButtonComponent;
  let fixture: ComponentFixture<MapExpandingSideButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MapExpandingSideButtonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapExpandingSideButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

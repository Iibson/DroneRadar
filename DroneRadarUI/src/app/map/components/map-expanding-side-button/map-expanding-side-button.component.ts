import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-map-expanding-side-button',
  templateUrl: './map-expanding-side-button.component.html',
  styleUrls: ['./map-expanding-side-button.component.scss'],
})
export class MapExpandingSideButtonComponent implements OnInit {
  @Input() buttonText: string = 'Text';
  @Input() iconClass: string = 'pi pi-arrow-right';
  @Input() side: string = 'left';
  @Input() topPosition: string = '150px';
  @Output() onClick: EventEmitter<void> = new EventEmitter();
  svgStyle = {
    'width.px': 16,
    'height.px': 16,
  };
  constructor() {}

  ngOnInit(): void {}

}

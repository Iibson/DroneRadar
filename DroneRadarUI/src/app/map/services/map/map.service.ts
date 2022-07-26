import { Injectable } from '@angular/core';
import { RxStomp, RxStompConfig } from '@stomp/rx-stomp';
import { Message } from '@stomp/stompjs';
import { BehaviorSubject, map, Observable, Subject } from 'rxjs';
import { FiltersDto } from 'src/shared/model/filters-dto.model';
import { IMapObjectInfoDto } from '../../../../shared/model/map-object-info-dto.model';
import * as uuid from 'uuid';
@Injectable({
  providedIn: 'root',
})
export class MapService {
  latestMapData!: IMapObjectInfoDto[];
  clientId: string;
  private _currentMapSnapshot!: IMapObjectInfoDto[];
  private webSocketEndpoint = 'ws://localhost:8080';
  private stompClient: RxStomp;
  private displayDronesSbj = new BehaviorSubject<boolean>(true);

  constructor() {
    this.stompClient = new RxStomp();
    this.stompClient.configure(this.getStompConfig());
    this.stompClient.activate();
    this.clientId = uuid.v4();
    this.observeMapData = new Subject();
    this.registerClientAndObserveMapData();
  }
  get currentMapSnapshot(): IMapObjectInfoDto[] {
    if (!this._currentMapSnapshot) this.refreshMapSnapshot();

    return this._currentMapSnapshot;
  }
  getDisplayDronesObservable = (): Observable<boolean> =>
    this.displayDronesSbj.asObservable();

  changeDisplayDrones = (value: boolean): void =>
    this.displayDronesSbj.next(value);

  getStompConfig(): RxStompConfig {
    return {
      brokerURL: this.webSocketEndpoint,
      heartbeatIncoming: 0, // Typical value 0 - disabled
      heartbeatOutgoing: 20000, // Typical value 20000 - every 20 seconds
      reconnectDelay: 200, // milliseconds
      debug: function (msg: string) {
        console.log(msg);
      },
    };
  }

  observeMapData: Subject<IMapObjectInfoDto[]>;

  refreshMapSnapshot(): void {
    this._currentMapSnapshot = JSON.parse(
      JSON.stringify(this.latestMapData ?? null)
    );
  }

  applyFilters(filters: FiltersDto): void {
    this.stompClient.publish({
      destination: '/server/' + this.clientId + '/apply-filters',
      body: JSON.stringify(filters),
    });
  }
  resetFilters(): void{
    this.stompClient.publish({
      destination: '/server/' + this.clientId + '/reset-filters'
    });
  }
  registerClientAndObserveMapData() {
    this.stompClient
      .watch('/client/' + this.clientId + '/map-data')
      .pipe(
        map((rawMessage: Message) => {
          return JSON.parse(rawMessage.body);
        })
      )
      .subscribe((result) => {
        this.observeMapData.next(result);
      });
    this.stompClient.publish({
      destination: '/server/register',
      body: this.clientId,
    });
  }
}

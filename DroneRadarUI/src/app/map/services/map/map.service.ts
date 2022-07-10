import { Injectable } from '@angular/core';
import { RxStomp, RxStompConfig } from '@stomp/rx-stomp';
import { Message } from '@stomp/stompjs';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { IMapObjectInfoDto } from '../../../../shared/model/map-object-info-dto.model';

@Injectable({
  providedIn: 'root',
})
export class MapService {
  latestMapData!: IMapObjectInfoDto[];
  private _currentMapSnapshot!: IMapObjectInfoDto[];
  private webSocketEndpoint = 'ws://localhost:8080';
  private stompClient: RxStomp;
  private displayDronesSbj = new BehaviorSubject<boolean>(true);

  constructor() {
    this.stompClient = new RxStomp();
    this.stompClient.configure(this.getStompConfig());
    this.stompClient.activate();
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

  observeMapData(): Observable<IMapObjectInfoDto[]> {
    return this.stompClient.watch('/client/map-data').pipe(
      map((rawMessage: Message) => {
        return JSON.parse(rawMessage.body);
      })
    );
  }

  refreshMapSnapshot(): void {
    this._currentMapSnapshot = JSON.parse(
      JSON.stringify(this.latestMapData ?? null)
    );
  }
}

// src/app/services/vecino.service.ts (ACTUALIZADO)

import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {Vecino} from '../model/vecino';

@Injectable({
  providedIn: 'root'
})
export class VecinoService {
  private url = environment.apiUrl + '/vecino';
  private httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  registrar(vecino: Vecino){
    return this.httpClient.post<Vecino>(this.url + '/registrar', vecino);
  }

  modificar(vecino: Vecino) {
    return this.httpClient.put<Vecino>(this.url + '/modificar', vecino);
  }

  eliminarCuenta(id:number) {
    return this.httpClient.delete(this.url + '/eliminar/' + id,  { responseType: 'text' as 'json' });
  }

  buscarPorDNI(dni:string){
    return this.httpClient.post<Vecino>(this.url + '/buscarPorDNI', dni);
  }

  buscarPorID(id:number){
    return this.httpClient.post<Vecino>(this.url + '/buscarPorID', id);
  }

  ranking(filtros:any){
    return this.httpClient.post<Vecino[]>(this.url + '/ranking', filtros);
  }

  /*----*/

  private _icono = new BehaviorSubject<number>(Number(localStorage.getItem('icono') || 0));
  icono$ = this._icono.asObservable();

  setIcono(icono: number) {
    localStorage.setItem('icono', String(icono));
    this._icono.next(icono);
  }
}

// src/app/services/vecino.service.ts (ACTUALIZADO)

import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
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

  modificar(perfilData: any): Observable<any> {
    return this.httpClient.put(this.url + '/modificar', perfilData);
  }

  eliminarCuenta(): Observable<any> {
    return this.httpClient.delete(this.url + '/eliminar');
  }

  buscarPorDNI(dni:string){
    return this.httpClient.post<Vecino>(this.url + '/buscarPorDNI', dni);
  }

  buscarPorID(id:number){
    return this.httpClient.post<Vecino>(this.url + '/buscarPorID', id);
  }

  listarVecinosPorEvento(idEvento:number){
    return this.httpClient.post<Vecino[]>(this.url + '/listarVecinosPorEvento', idEvento);
  }

  ranking(filtros:any){
    return this.httpClient.post<Vecino[]>(this.url + '/ranking', filtros);
  }
}

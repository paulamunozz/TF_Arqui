import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Evento} from '../model/evento';
import {Observable, Subject} from 'rxjs';
import {CantidadEventosLogradosDTO} from '../model/reportes/cantidad-eventos-logrados-dto';

@Injectable({
  providedIn: 'root'
})
export class EventoService {
  private url = environment.apiUrl + '/evento';
  private httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  registrar(evento:Evento){
    return this.httpClient.post(this.url + '/registrar', evento);
  }

  modificar(evento:Evento){
    return this.httpClient.put(this.url + '/modificar', evento);
  }

  eliminar(id:number){
    return this.httpClient.delete(this.url + '/eliminar/' + id);
  }

  detalle(id:number): Observable<Evento>{
    return this.httpClient.get<Evento>(this.url + '/detalle/' + id);
  }

  listarPorDistrito(filtros:any):Observable<Evento[]>{
    return this.httpClient.post<Evento[]>(this.url + '/listarYfiltrar', filtros);
  }

  listarPorVecino(filtros:any):Observable<Evento[]>{
    return this.httpClient.post<Evento[]>(this.url + '/listarPorVecino', filtros);
  }

  cantidadEventosLogrados(distrito:string):Observable<CantidadEventosLogradosDTO>{
    return this.httpClient.post<CantidadEventosLogradosDTO>(this.url + '/cantidadEventosLogrados', distrito);
  }
}

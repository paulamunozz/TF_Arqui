import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Municipalidad} from '../model/municipalidad';

@Injectable({
  providedIn: 'root'
})
export class MunicipalidadService {
  private url = environment.apiUrl + '/municipalidad';
  private httpClient: HttpClient = inject(HttpClient);

  constructor() {
  }

  modificarContrasena(idMunicipalidad: number, nuevaContrasena: string): Observable<string> {
    const body = {
      id: idMunicipalidad,
      contrasena: nuevaContrasena
    };
    return this.httpClient.put(this.url + '/modificarContrasena', body, { responseType: 'text' });
  }

  buscarPorId(idUsuario: number): Observable<Municipalidad> {
    return this.httpClient.post<Municipalidad>(this.url + '/buscarXid', idUsuario);
  }

  buscarPorCodigo(codigoUsuario: string): Observable<Municipalidad> {
    return this.httpClient.post<Municipalidad>(this.url + '/buscarXcodigo', codigoUsuario);
  }

  rankingMunicipalidades(): Observable<Municipalidad[]> {
    return this.httpClient.get<Municipalidad[]>(this.url + '/ranking');
  }
}

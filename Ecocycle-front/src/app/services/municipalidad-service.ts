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

  constructor() {}

// 1. PUT /modificarContrasena (Requiere Rol MUNICIPALIDAD)
  // El backend espera un cuerpo con 'id' (Integer) y 'contrasena' (String).
  modificarContrasena(idMunicipalidad: number, nuevaContrasena: string): Observable<string> {
    const body = {
      id: idMunicipalidad,
      contrasena: nuevaContrasena
    };
    // El backend devuelve un String (un mensaje de éxito/error)
    return this.httpClient.put(this.url + '/modificarContrasena', body, { responseType: 'text' });
  }

  // 2. GET /buscarPorId (Requiere Rol MUNICIPALIDAD)
  // Asumiendo que el backend fue cambiado a @RequestParam o que este es un POST:
  buscarPorId(idUsuario: number): Observable<Municipalidad> {
    // Si usas GET con @RequestParam (RECOMENDADO):
    return this.httpClient.post<Municipalidad>(this.url + '/buscarXid', idUsuario);

    /* Si el backend SIGUE EN @RequestBody (NO RECOMENDADO):
    return this.http.post<MunicipalidadDTO>(`${this.apiUrl}/buscarPorId`, idUsuario);
    */
  }

  // 3. GET /buscarXcodigo (Requiere Rol ADMIN)
  // Asumiendo que el backend fue cambiado a @RequestParam (para el String codigoUsuario):
  buscarPorCodigo(codigoUsuario: string): Observable<Municipalidad> {
    return this.httpClient.post<Municipalidad>(this.url + '/buscarXcodigo', codigoUsuario);
  }

  rankingMunicipalidades(): Observable<Municipalidad[]> {
    return this.httpClient.get<Municipalidad[]>(this.url + '/ranking');
  }
}

// src/app/services/vecino.service.ts (ACTUALIZADO)

import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VecinoService {
  private url = environment.apiUrl + '/vecino';
  private httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  obtenerPerfil(): Observable<any> {
    // URL ejemplo: /api/vecino/detalle
    return this.httpClient.get<any>(this.url + '/detalle');
  }

  modificarPerfil(perfilData: any): Observable<any> {
    // URL ejemplo: /api/vecino/modificar
    return this.httpClient.put(this.url + '/modificar', perfilData);
  }

  eliminarCuenta(): Observable<any> {
    // URL ejemplo: /api/vecino/eliminar
    // Asumimos que el backend identifica al vecino por la sesión/token para eliminarlo.
    return this.httpClient.delete(this.url + '/eliminar');
  }
}

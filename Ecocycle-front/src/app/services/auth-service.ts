// src/app/services/auth.service.ts

import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // 🚨 Asumimos que la ruta para el AuthController es '/auth'
  private url = environment.apiUrl + '/auth';
  private httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  /**
   * Cierra la sesión del usuario.
   * Envía una solicitud al AuthController para invalidar el token/sesión.
   */
  cerrarSesion(): Observable<any> {
    // URL ejemplo: /api/auth/logout (o /api/auth/cerrarsesion)
    // Usaremos '/logout' como convención.
    return this.httpClient.post(this.url + '/logout', {});
  }
}

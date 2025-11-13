import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Logro} from '../model/logro';

@Injectable({
  providedIn: 'root'
})
export class LogroService {
  private url = environment.apiUrl + '/logro';
  private httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  listarLogrosPorVecino(id: number): Observable<Logro[]> {
    return this.httpClient.post<Logro[]>(this.url + '/listarPorVecino', id);

  }
}

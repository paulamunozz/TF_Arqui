import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CantidadResiduosDTO} from '../model/reportes/cantidad-residuos-dto';

@Injectable({
  providedIn: 'root'
})
export class ReciclajeService {
  private url = environment.apiUrl + '/reciclaje';
  private httpClient: HttpClient = inject(HttpClient);


  cantidadPorTipoResiduo(distrito:string):Observable<CantidadResiduosDTO[]>{
    return this.httpClient.post<CantidadResiduosDTO[]>(this.url + '/cantidadPorTipo', distrito);
  }
}

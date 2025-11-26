import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CantidadResiduosDTO} from '../model/reportes/cantidad-residuos-dto';
import {Reciclaje} from '../model/reciclaje';

@Injectable({
  providedIn: 'root'
})
export class ReciclajeService {
  private url = environment.apiUrl + '/reciclaje';
  private httpClient: HttpClient = inject(HttpClient);

  registrar(reciclaje:Reciclaje){
    return this.httpClient.post<Reciclaje>(this.url + '/registrar', reciclaje);
  }

  modificar(reciclaje: Reciclaje){
    return this.httpClient.put<Reciclaje>(this.url + '/modificar', reciclaje);
  }

  eliminar(id: number){
    return this.httpClient.delete(this.url + '/eliminar/' + id);
  }

  listarPorVecino(filtros: any){
    return this.httpClient.post<Reciclaje[]>(this.url + '/vecino', filtros);
  }

  listarPorDistrito(filtros: any){
    return this.httpClient.post<Reciclaje[]>(this.url + '/distrito', filtros);
  }

  cantidadPorTipoResiduo(distrito:string){
    return this.httpClient.post<CantidadResiduosDTO[]>(this.url + '/cantidadPorTipo', distrito);
  }
}

import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {EventoXVecino} from '../model/evento-x-vecino';
import {Comentario} from '../model/reportes/comentario';
import {CantidadesVecinosPorEventoDTO} from '../model/reportes/cantidades-vecinos-por-evento-dto';

@Injectable({
  providedIn: 'root'
})
export class EventoXVecinoService {
  private url: string = environment.apiUrl + '/exv';
  private httpClient: HttpClient = inject(HttpClient);

  constructor() {
  }

  registrar(exv:EventoXVecino){
    return this.httpClient.post(this.url + '/registrar', exv);
  }

  modificar(exv:EventoXVecino){
    return this.httpClient.put(this.url + '/modificar', exv);
  }

  eliminar(idEXV:number){
    return this.httpClient.delete(this.url + '/eliminar/' + idEXV, { responseType: 'text' as 'json' });
  }

  buscarPorEventoYVecino(idEvento:number, idVecino:number){
    return this.httpClient.get<EventoXVecino>(this.url + '/buscarPorEventoYVecino/'+`${idEvento}/${idVecino}`)
  }

  comentarios(eventoId:number){
    return this.httpClient.post<Comentario[]>(this.url + '/comentarios', eventoId);
  }

  estadisticasVecinosPorEvento(eventoId:number){
    return this.httpClient.post<CantidadesVecinosPorEventoDTO>(this.url + '/estadisticasVecinosPorEvento', eventoId);
  }
}

import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {EventoService} from '../../services/evento-service';
import {Evento} from '../../model/evento';

@Component({
  selector: 'app-us21-us31-vecino-detalle-evento-disponible',
  imports: [
    RouterLink
  ],
  templateUrl: './vecino-detalle-evento-disponible.html',
  styleUrl: './vecino-detalle-evento-disponible.css',
})
export class VecinoDetalleEventoDisponible {
  private eventoService: EventoService = inject(EventoService);
  private router = inject(Router);

  evento: Evento = new Evento();

  id:number | undefined;
  constructor(private route:ActivatedRoute) { }
  ngOnInit(){
    this.route.params.subscribe(params => {
      this.id = +params['id'];

      this.eventoService.detalle(this.id).subscribe({
        next: (data) => {
          this.evento = data;
          console.log("Evento cargado: ", data);
        },
        error: (error) => {
          console.log(error);
        }
      })
    })
  }
  comentarios=[
    {id:'1', nombre:'Paula Muñoz', comentario:'Participé activamente en la campaña de reciclaje de vidrio.'},
    {id:'2', nombre:'Mae Villachica', comentario:'Fue una buena experiencia para aprender más sobre el reciclaje.'},
    {id:'3', nombre:'José', comentario:'Me gustó ayudar en la jornada de reciclaje del barrio.'}]
}

import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {EventoService} from '../../services/evento-service';
import {Evento} from '../../model/evento';
import {DatePipe} from '@angular/common';
import {EventoXVecinoService} from '../../services/evento-x-vecino-service';
import {EventoXVecino} from '../../model/evento-x-vecino';
import {MatTableDataSource} from '@angular/material/table';
import {Comentario} from '../../model/reportes/comentario';

@Component({
  selector: 'app-us21-us31-vecino-detalle-evento-disponible',
  imports: [
    RouterLink,
    DatePipe
  ],
  templateUrl: './vecino-detalle-evento-disponible.html',
  styleUrl: './vecino-detalle-evento-disponible.css',
})
export class VecinoDetalleEventoDisponible {
  private eventoService: EventoService = inject(EventoService);
  private exvService: EventoXVecinoService = inject(EventoXVecinoService);
  private router = inject(Router);

  evento: Evento = new Evento();
  comentarios: MatTableDataSource<Comentario> = new MatTableDataSource<Comentario>();

  id:number;
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

    this.listarComentarios()
  }

  listarComentarios() {
    this.exvService.comentarios(this.id).subscribe({
      next: (data) => {
        this.comentarios.data = data;
      }
    })
  }

  unirseEvento(){
    let exv = new EventoXVecino()
    exv.eventoId = this.id;
    exv.vecinoId = 1;

    this.exvService.registrar(exv).subscribe({
      next: (data) => {
        console.log(data);
        this.router.navigate(['/mis-eventos/' + this.id]);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}

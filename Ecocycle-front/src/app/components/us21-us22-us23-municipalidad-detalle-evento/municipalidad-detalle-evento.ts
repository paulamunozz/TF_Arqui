import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {EventoService} from '../../services/evento-service';
import {Evento} from '../../model/evento';
import {MatTableDataSource} from '@angular/material/table';
import {Comentario} from '../../model/reportes/comentario';
import {EventoXVecinoService} from '../../services/evento-x-vecino-service';
import {EventoXVecino} from '../../model/evento-x-vecino';

@Component({
  selector: 'app-us21-us22-us23-municipalidad-detalle-evento',
  imports: [],
  templateUrl: './municipalidad-detalle-evento.html',
  styleUrl: './municipalidad-detalle-evento.css',
  standalone: true
})
export class MunicipalidadDetalleEvento {
  private eventoService: EventoService = inject(EventoService);
  private exvService: EventoXVecinoService = inject(EventoXVecinoService);
  private router = inject(Router);

  evento: Evento = new Evento();
  comentarios:MatTableDataSource<Comentario> = new MatTableDataSource<Comentario>();

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

  editarEvento(){
    console.log('ID a editar:', this.id);
    this.router.navigate(['/modificar-evento', this.id]);
  }

  eliminar(id:number){
    this.eventoService.eliminar(id).subscribe({
      next: (data) => {
        console.log(data);
        this.router.navigate(['/eventos']);
      }
    })
  }

  popUpVisible = false;
  mostrarPopUp()
  {
    this.popUpVisible = true;
  }
  cerrarPopUp(){
    this.popUpVisible = false;
  }

  listarComentarios(){
    this.exvService.comentarios(this.id).subscribe({
      next: (data) => {
        this.comentarios.data = data;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  eliminarComentario(idEXV:number){
    let exv = new EventoXVecino();
    exv.id = idEXV;
    exv.comentario = null;
    this.exvService.modificar(exv).subscribe({
      next: (data) => {
        this.listarComentarios();
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}

import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {EventoService} from '../../services/evento-service';
import {Evento} from '../../model/evento';

@Component({
  selector: 'app-us21-us22-us23-municipalidad-detalle-evento',
  imports: [],
  templateUrl: './municipalidad-detalle-evento.html',
  styleUrl: './municipalidad-detalle-evento.css',
  standalone: true
})
export class MunicipalidadDetalleEvento {
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

  comentarios=[
    {id:'1', nombre:'Paula Muñoz', comentario:'Participé activamente en la campaña de reciclaje de vidrio.'},
    {id:'2', nombre:'Mae Villachica', comentario:'Fue una buena experiencia para aprender más sobre el reciclaje.'},
    {id:'3', nombre:'José', comentario:'Me gustó ayudar en la jornada de reciclaje del barrio.'}]

}

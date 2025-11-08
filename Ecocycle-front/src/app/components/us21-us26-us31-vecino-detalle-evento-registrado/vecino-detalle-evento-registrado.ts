import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {EventoService} from '../../services/evento-service';
import {Evento} from '../../model/evento';

@Component({
  selector: 'app-us21-us26-us31-vecino-detalle-evento-registrado',
  imports: [
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './vecino-detalle-evento-registrado.html',
  styleUrl: './vecino-detalle-evento-registrado.css',
})
export class VecinoDetalleEventoRegistrado {
  private eventoService: EventoService = inject(EventoService);
  private router = inject(Router);

  evento: Evento = new Evento();
  id:number | undefined;

  formComentario: FormGroup;
  private fb = inject(FormBuilder);
  constructor(private route:ActivatedRoute) {
    this.formComentario = this.fb.group({ comentario: ['', Validators.required] });
  }

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

  popUpRetiro = false;
  mostrarRetiro() {
    this.popUpRetiro = true;
  }
  cerrarRetiro(){
    this.popUpRetiro = false;
  }

  imgSrcEditar='/editar-1';
  imgSrcEliminar='/tacho-1';

  accionesComentario = false;
  publicarComentario(){
    this.accionesComentario = true;
  }
  editarEliminarComentario(){
    this.accionesComentario = false;
  }

  comentarios=[
    {id:'1', nombre:'Paula Muñoz', comentario:'Participé activamente en la campaña de reciclaje de vidrio.'},
    {id:'2', nombre:'Mae Villachica', comentario:'Fue una buena experiencia para aprender más sobre el reciclaje.'},
    {id:'3', nombre:'José', comentario:'Me gustó ayudar en la jornada de reciclaje del barrio.'}]
}

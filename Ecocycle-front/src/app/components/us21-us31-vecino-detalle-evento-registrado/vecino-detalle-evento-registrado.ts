import {Component, inject} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {EventoService} from '../../services/evento-service';
import {Evento} from '../../model/evento';
import {EventoXVecinoService} from '../../services/evento-x-vecino-service';
import {MatTableDataSource} from '@angular/material/table';
import {Comentario} from '../../model/reportes/comentario';
import {EventoXVecino} from '../../model/evento-x-vecino';
import {DatePipe} from '@angular/common';
import {MatCard, MatCardContent} from '@angular/material/card';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput} from '@angular/material/input';
import {MatDialog} from '@angular/material/dialog';
import {VecinoRetiroEvento} from '../us26-vecino-retiro-evento/vecino-retiro-evento';

@Component({
  selector: 'app-us21-us31-vecino-detalle-evento-registrado',
  imports: [
    ReactiveFormsModule,
    DatePipe,
    MatCard,
    MatCardContent,
    MatButton,
    MatInput,
    MatFormField
  ],
  templateUrl: './vecino-detalle-evento-registrado.html',
  styleUrl: './vecino-detalle-evento-registrado.css',
})
export class VecinoDetalleEventoRegistrado {
  private eventoService: EventoService = inject(EventoService);
  private exvService:EventoXVecinoService = inject(EventoXVecinoService);
  private router = inject(Router);
  private userId = Number(localStorage.getItem('userId'));

  evento: Evento = new Evento();
  exv:EventoXVecino = new EventoXVecino();
  comentarios : MatTableDataSource<Comentario> = new MatTableDataSource<Comentario>();
  id:number;
  accionesComentario:boolean = false;
  dialog : MatDialog = inject(MatDialog);
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

    this.exvService.buscarPorEventoYVecino(this.id, this.userId).subscribe({
      next: (data) => {
        this.exv = data;
        if(this.exv.comentario != null){
          this.accionesComentario = true;
          this.formComentario.patchValue({ comentario: data.comentario });
        }
      }
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

  publicarComentario(){
    console.log('Valor del comentario:', this.formComentario.value);

    let exv = new EventoXVecino();
    this.exvService.buscarPorEventoYVecino(this.id, this.userId).subscribe({
      next: (data) => {
        exv = data;
        exv.comentario = this.formComentario.value.comentario;
        this.exvService.modificar(exv).subscribe({
          next: (data) => {
            this.listarComentarios();
            this.accionesComentario = true;
          },
          error: (error) => {
            console.log(error);
          }
        })
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  eliminarComentario(){
    let exv = new EventoXVecino();
    this.exvService.buscarPorEventoYVecino(this.id, this.userId).subscribe({
      next: (data) => {
        exv = data;
        exv.comentario = null;
        this.exvService.modificar(exv).subscribe({
          next: (data) => {
            this.listarComentarios();
            this.formComentario.patchValue({ comentario: null });
            this.accionesComentario = false;
          },
          error: (error) => {
            console.log(error);
          }
        })
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  openDialog() {
    const dialogoEliminar=this.dialog.open(VecinoRetiroEvento);
    dialogoEliminar.afterClosed().subscribe({
      next: (data) => {
        if (data){
          this.exvService.eliminar(this.exv.id).subscribe({
            next: data => {
              alert(data)
              this.router.navigate(['/mis-eventos']);
            },
            error: error => {
              console.log(error);
            }
          })
        }
      }
    })
  }
}

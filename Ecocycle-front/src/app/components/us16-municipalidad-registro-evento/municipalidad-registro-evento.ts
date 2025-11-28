import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {Evento} from '../../model/evento';
import {EventoService} from '../../services/evento-service';
import {MatFormField, MatLabel, MatSuffix} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatSelect, MatOption} from '@angular/material/select';
import {
  MatDatepicker,
  MatDatepickerInput,
  MatDatepickerModule,
  MatDatepickerToggle
} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatButton} from '@angular/material/button';
import {MatCard, MatCardContent} from '@angular/material/card';

@Component({
  selector: 'app-us16-municipalidad-registro-evento',
  imports: [ReactiveFormsModule, MatFormField, MatLabel, MatInput, MatSelect, MatOption, MatDatepicker, MatDatepickerInput, MatDatepickerToggle, MatSuffix,
    MatDatepickerModule,
    MatNativeDateModule, MatButton, MatCard, MatCardContent,],
  templateUrl: './municipalidad-registro-evento.html',
  styleUrl: './municipalidad-registro-evento.css',
})
export class MunicipalidadRegistroEvento {
  formRegistro: FormGroup;
  private fb = inject(FormBuilder);
  private eventoService: EventoService = inject(EventoService);
  private router = inject(Router);
  private userId = Number(localStorage.getItem('userId'));

  constructor() {
    this.formRegistro = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      tipo: ['', Validators.required],
      metodo: ['', Validators.required],
      fechaInicio: ['', Validators.required],
      fechaFin: ['', Validators.required],
      pesoObjetivo: ['', Validators.required],
      bonificacion: ['', Validators.required],
    });
  }

  onSubmit(){
    if(this.formRegistro.valid){
      let evento = new Evento();
      evento = this.formRegistro.value;
      evento.municipalidadId = this.userId;

      console.log("Datos leidos del form:",evento);
      this.eventoService.registrar(evento).subscribe({
        next: data => {
          console.log("Data insertada:",data);
          this.router.navigate(['/eventos']);
        }
      });
    }
  }
}

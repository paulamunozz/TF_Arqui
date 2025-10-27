import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-us16-municipalidad-registro-evento',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './municipalidad-registro-evento.html',
  styleUrl: './municipalidad-registro-evento.css',
})
export class MunicipalidadRegistroEvento {
  formRegistro: FormGroup;
  private fb = inject(FormBuilder);
  constructor() {
    this.formRegistro = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      tipo: ['', Validators.required],
      metodo: ['', Validators.required],
      fechaInicio: ['', Validators.required],
      fechaFin: ['', Validators.required],
      pesoObjetivo: ['', Validators.required],
      bonificacion: ['', Validators.required]
    });
  }
}

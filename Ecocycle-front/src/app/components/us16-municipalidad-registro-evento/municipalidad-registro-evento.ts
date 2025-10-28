import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-us16-municipalidad-registro-evento',
  imports: [ReactiveFormsModule],
  templateUrl: './municipalidad-registro-evento.html',
  styleUrl: './municipalidad-registro-evento.css',
})
export class MunicipalidadRegistroEvento {
  formRegistro: FormGroup;
  private fb = inject(FormBuilder);
  private router = inject(Router);

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

  registrar() {
    if (this.formRegistro.valid) {
      const nombreEvento = this.formRegistro.value.nombre;

      this.router.navigate(['/evento-confirmacion'], {
        state: { nombreEvento: nombreEvento },
      });
    } else {
      alert('Por favor, complete todos los campos requeridos.');
    }
  }
}

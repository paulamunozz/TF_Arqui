import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-us05-vecino-registro',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './vecino-registro.html',
  styleUrl: './vecino-registro.css',
})
export class VecinoRegistro {
  formRegistro: FormGroup;
  private fb = inject(FormBuilder);

  constructor() {
    this.formRegistro = this.fb.group({
      dni: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      nombre: ['', Validators.required],
      edad: ['', [Validators.required, Validators.min(18)]],
      genero: ['', Validators.required],
      distrito: ['', Validators.required],
      contrasena: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit() {
    if (this.formRegistro.valid) {
      console.log('Datos del formulario:', this.formRegistro.value);
      alert('Cuenta creada exitosamente 🎉');
    } else {
      alert('Por favor, completa correctamente todos los campos.');
    }
  }
}

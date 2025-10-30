import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-vecino-autenticacion',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './vecino-autenticacion.html',
  styleUrl: './vecino-autenticacion.css',
})
export class VecinoAutenticacion {
  formLogin: FormGroup;
  private fb = inject(FormBuilder);

  constructor() {
    this.formLogin = this.fb.group({
      dni: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      contrasena: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit() {
    if (this.formLogin.valid) {
      console.log('Inicio de sesión con:', this.formLogin.value);
      alert('Inicio de sesión exitoso');
    } else {
      alert('Por favor, ingresa un DNI válido (8 dígitos) y una contraseña de al menos 6 caracteres.');
    }
  }
}

import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import {Router} from '@angular/router';
import {EventoService} from '../../services/evento-service';
import {VecinoService} from '../../services/vecino-service';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';

@Component({
  selector: 'app-us01-municipalidad-vecino-autenticacion',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, MatFormField, MatLabel, MatInput],
  templateUrl: './vecino-autenticacion.html',
  styleUrl: './vecino-autenticacion.css',
})
export class VecinoAutenticacion {
  formLogin: FormGroup;
  private fb = inject(FormBuilder);
  private vecinoService : VecinoService = inject(VecinoService);
  private securityService: EventoService = inject(EventoService);
  private router = inject(Router);

  constructor() {
    this.formLogin = this.fb.group({
      dni: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      contrasena: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.formLogin.valid) {
      this.vecinoService.buscarPorDNI(this.formLogin.controls['dni'].value).subscribe({
        next: vecino => {
          console.log(vecino);
          this.router.navigate(['inicio-muni']);
        },
        error: (err) => {
          console.error('Error al buscar usuario:', err);
          alert('No se pudo iniciar sesión. Verifica el DNI.');
        }
      });

    } else {
      alert('Por favor, ingresa un DNI válido (8 dígitos) y una contraseña.');
    }
  }
}

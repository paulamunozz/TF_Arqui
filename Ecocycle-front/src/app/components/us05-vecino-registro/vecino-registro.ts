import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, FormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatSelect} from '@angular/material/select';
import {MatOption} from '@angular/material/core';
import {VecinoService} from '../../services/vecino-service';
import {Vecino} from '../../model/vecino';

@Component({
  selector: 'app-us05-vecino-registro',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, RouterLink, MatFormField, MatLabel, MatInput, MatSelect, MatOption],
  templateUrl: './vecino-registro.html',
  styleUrl: './vecino-registro.css',
})
export class VecinoRegistro {
  private vecinoService: VecinoService = inject(VecinoService);
  private router: Router = inject(Router);

  formRegistro: FormGroup;
  private fb = inject(FormBuilder);

  constructor() {
    this.formRegistro = this.fb.group({
      dni: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      nombre: ['', Validators.required],
      edad: ['', [Validators.required, Validators.min(15)]],
      genero: ['', Validators.required],
      distrito: ['', Validators.required],
      contrasena: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.formRegistro.valid) {
      console.log('Datos del formulario:', this.formRegistro.value);
      let vecino = new Vecino();
      vecino = this.formRegistro.value;
      this.vecinoService.registrar(vecino).subscribe({
        next: data => {
          console.log(data);
          this.router.navigate(['/inicio-vecino']);
          alert('Cuenta creada exitosamente 🎉');
        },
        error: error => {
          console.log(error);
        }
      })
    }
    else {
      alert('Por favor, completa correctamente todos los campos.');
    }
  }
}

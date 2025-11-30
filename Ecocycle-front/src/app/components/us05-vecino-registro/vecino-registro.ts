import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, FormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatSelect} from '@angular/material/select';
import {MatOption} from '@angular/material/core';
import {VecinoService} from '../../services/vecino-service';
import {Vecino} from '../../model/vecino';
import {User} from '../../model/user';
import {Auth} from '../../model/auth';
import {LoginService} from '../../services/login-service';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-us05-vecino-registro',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, RouterLink, MatFormField, MatLabel, MatInput, MatSelect, MatOption, MatButton],
  templateUrl: './vecino-registro.html',
  styleUrl: './vecino-registro.css',
})
export class VecinoRegistro {
  private vecinoService: VecinoService = inject(VecinoService);
  private loginService: LoginService = inject(LoginService);
  private router: Router = inject(Router);
  private user: User = new User();

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
          this.user.username = vecino.dni;
          this.user.password = vecino.contrasena;

          this.loginService.login(this.user).subscribe({
            next: (auth:Auth) => {
              localStorage.setItem('rol', auth.roles[0]);
              localStorage.setItem('userId', String(data.idVecino));
              this.router.navigate(['inicio-vecino']);
            }
          })
          alert('Cuenta creada exitosamente 🎉');
        },
        error: error => {
          console.log(error);
          alert(error.error?.message || 'Error desconocido');
        }
      })
    }
    else {
      alert('Por favor, completa correctamente todos los campos.');
    }
  }
}

import {ChangeDetectionStrategy, Component, inject, signal} from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import {Router} from '@angular/router';
import {VecinoService} from '../../services/vecino-service';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {User} from '../../model/user';
import {Auth} from '../../model/auth';
import {MunicipalidadService} from '../../services/municipalidad-service';
import {LoginService} from '../../services/login-service';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-us01-municipalidad-vecino-autenticacion',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './vecino-autenticacion.html',
  styleUrl: './vecino-autenticacion.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class VecinoAutenticacion {
  formLogin: FormGroup;
  private fb = inject(FormBuilder);
  private vecinoService : VecinoService = inject(VecinoService);
  private municipalidadService: MunicipalidadService = inject(MunicipalidadService);
  private loginService : LoginService = inject(LoginService);
  private router = inject(Router);

  constructor() {
    this.formLogin = this.fb.group({
      dni: ['', [Validators.required]],
      contrasena: ['', Validators.required]
    });
  }

  ngOnInit() {
    if(localStorage.getItem('token')!=null){
      localStorage.clear();
    }
  }

  onSubmit() {
    if (this.formLogin.valid) {
      const user : User = new User();
      user.username = this.formLogin.value.dni;
      user.password = this.formLogin.value.contrasena;

      this.loginService.login(user).subscribe({
        next: (data:Auth) => {
          console.log("Login response ROLs:", data.roles);
          console.log("Login response ROL:", data.roles[0]);
          localStorage.setItem('rol', data.roles[0]);

          if(localStorage.getItem('rol')=="ROLE_VECINO"){
            this.vecinoService.buscarPorDNI(this.formLogin.controls['dni'].value).subscribe({
              next: vecino => {
                console.log(vecino);
                localStorage.setItem('userId', String(vecino.idVecino));
                console.log(localStorage.getItem('rol'));
                console.log(localStorage.getItem('userId'));
                console.log(localStorage.getItem('token'));

                this.vecinoService.buscarPorID(vecino.idVecino).subscribe({
                  next: result => {
                    this.vecinoService.setIcono(result.icono);
                  },
                  error: error => {
                    console.log(error);
                  }
                })
                this.router.navigate(['inicio-vecino']);
              },
              error: (err) => {
                console.error('Error al buscar usuario:', err);
                alert('No se pudo iniciar sesión. Verifica el DNI.');
              }
            });
          }
          if(localStorage.getItem('rol')=="ROLE_MUNICIPALIDAD"){
            console.log(this.formLogin.value);
            this.municipalidadService.buscarPorCodigo(this.formLogin.controls['dni'].value).subscribe({
              next: municipalidad => {
                console.log(municipalidad);
                localStorage.setItem('userId', String(municipalidad.idMunicipalidad));
                console.log(localStorage.getItem('rol'));
                console.log(localStorage.getItem('userId'));
                console.log(localStorage.getItem('token'));
                this.router.navigate(['inicio-muni']);
              },
              error: (err) => {
                console.log('Error capturado en componente:', err.message);
                //console.error('Error al buscar municipalidad:', err);
                alert('No se pudo iniciar sesión. Verifica el código.');
              }
            });
          }

        },
        error: (err) => {
          console.log("Login response ROL:", err);
        }
      })

    } else {
      alert('Por favor, ingresa un DNI válido (8 dígitos) y una contraseña.');
    }
  }

  hide = signal(true);
  clickEvent(event: MouseEvent) {
    event.stopPropagation();
    this.hide.set(!this.hide());
  }
}

import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MatCard, MatCardContent} from '@angular/material/card';
import {MatFormField, MatLabel, MatSuffix} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MunicipalidadService} from '../../services/municipalidad-service';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';

@Component({
  selector: 'app-us37-municipalidad-modificacion-contrasena',
  imports: [
    MatCard,
    MatCardContent,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    MatIcon,
    MatIconButton,
    MatSuffix
  ],
  templateUrl: './municipalidad-modificacion-contrasena.html',
  styleUrl: './municipalidad-modificacion-contrasena.css',
})
export class MunicipalidadModificacionContrasena {
  formConstrasena: FormGroup;
  private fb: FormBuilder = inject(FormBuilder);
  private router: Router = inject(Router);
  private municipalidadService: MunicipalidadService = inject(MunicipalidadService);

  constructor() {
    this.formConstrasena = this.fb.group({
      contrasena: ['', Validators.required]
    })
  }

  onSubmit(){
    if (this.formConstrasena.invalid) {
      console.warn('Formulario inválido. Complete la contraseña.');
      return;
    }

    const idMunicipalidad = Number(localStorage.getItem('userId'));
    if (!idMunicipalidad) {
      console.error('ID de Municipalidad no encontrado. ¿Está logueado?');
      return;
    }

    const nuevaContrasena = this.formConstrasena.value.contrasena;

    // 2. Llamar al servicio para modificar la contraseña
    this.municipalidadService.modificarContrasena(idMunicipalidad, nuevaContrasena)
      .subscribe({
        next: (responseMessage) => {
          // El backend devuelve un String (mensaje de éxito)
          console.log('Contraseña actualizada con éxito:', responseMessage);
          // 3. Redirigir o mostrar mensaje de éxito
          alert('Contraseña actualizada correctamente.');
          this.router.navigate(['/inicio-muni']); // Reemplaza con tu ruta de destino
        },
        error: (err) => {
          // 4. Manejar el error (ej: token vencido, error en el backend)
          console.error('Error al actualizar la contraseña:', err);
          alert('Error al actualizar la contraseña. Verifique sus permisos.');
        }
      });
  }

  onCancel(): void {
    this.router.navigate(['/inicio-muni']); // Reemplaza con tu ruta de destino
  }

  hidePassword = true;

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }
}

import { Component, ViewChild, TemplateRef, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { HttpClientModule } from '@angular/common/http';
import {VecinoService} from '../../services/vecino-service';
import {AuthService} from '../../services/auth-service';

@Component({
  selector: 'app-us06-perfil-modificar-eliminar',
  templateUrl: './perfil-modificar-eliminar.html',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
    MatDividerModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatDialogModule,
  ],
  styleUrls: ['./perfil-modificar-eliminar.css']
})
export class PerfilModificarEliminarComponent implements OnInit {

  @ViewChild('eliminarCuentaDialog') eliminarCuentaDialog!: TemplateRef<any>;
  @ViewChild('cerrarSesionDialog') cerrarSesionDialog!: TemplateRef<any>;

  isEditing: boolean = false;
  perfilForm: FormGroup;


  datosPerfil: any = {
    dni: '',
    nombre: 'Cargando...',
    edad: 0,
    genero: '',
    distrito: '',
    puesto: '-',
    puntajeAcumulado: 0
  };

  generos: string[] = ['Masculino', 'Femenino', 'Otro'];
  distritos: string[] = ['Miraflores', 'Surco', 'San Isidro', 'Barranco', 'La Molina', 'Surquillo', 'San Borja'];


  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    public dialog: MatDialog,
    private router: Router,
    private vecinoService: VecinoService,
    private authService: AuthService
  ) {
    this.perfilForm = this.fb.group({
      dni: [{ value: '', disabled: true }, [Validators.required, Validators.pattern(/^\d{8}$/)]], // DNI no debería ser editable
      nombre: ['', Validators.required],
      edad: ['', [Validators.required, Validators.min(18)]],
      genero: ['', Validators.required],
      distrito: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cargarPerfil(): void {
    this.vecinoService.obtenerPerfil().subscribe({
      next: (data: { [key: string]: any; }) => {
        this.datosPerfil = data;
        this.perfilForm.patchValue(data);
        this.perfilForm.disable();
      },
      error: (error: any) => {
        console.error('Error al cargar perfil:', error);
        this.snackBar.open('Error al cargar perfil. Inténtalo más tarde.', 'Cerrar', { duration: 5000 });
      }
    });
  }


  get dniControl(): AbstractControl | null {
    return this.perfilForm.get('dni');
  }

  toggleEdit(modo: boolean): void {
    this.isEditing = modo;
    if (modo) {
      this.perfilForm.enable();
      this.perfilForm.get('dni')?.disable();
    } else {
      this.perfilForm.patchValue(this.datosPerfil);
      this.perfilForm.disable();
    }
  }

  guardarPerfil(): void {
    if (this.perfilForm.valid) {
      const datosActualizados = this.perfilForm.getRawValue();

      this.vecinoService.modificarPerfil(datosActualizados).subscribe({
        next: () => {
          this.datosPerfil = { ...this.datosPerfil, ...datosActualizados };
          this.toggleEdit(false);
          this.snackBar.open('Perfil actualizado correctamente.', 'Cerrar', { duration: 3000 });
        },
        error: (error: any) => {
          console.error('Error al actualizar el perfil:', error);
          this.snackBar.open('Error al guardar perfil.', 'Cerrar', { duration: 5000 });
        }
      });
    }
  }

  abrirDialogoEliminarCuenta(): void {
    const dialogRef = this.dialog.open(this.eliminarCuentaDialog, { width: '400px' });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'eliminar') {
        this.vecinoService.eliminarCuenta().subscribe({
          next: () => {
            this.snackBar.open('Cuenta eliminada correctamente.', 'Cerrar', { duration: 2000 });
            this.router.navigate(['/registro']);
          },
          error: (error: any) => {
            console.error('Error al eliminar la cuenta:', error);
            this.snackBar.open('Error al intentar eliminar la cuenta.', 'Cerrar', { duration: 5000 });
          }
        });
      }
    });
  }

  abrirDialogoCerrarSesion(): void {
    const dialogRef = this.dialog.open(this.cerrarSesionDialog, { width: '400px' });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'cerrar') {
        this.authService.cerrarSesion().subscribe({
          next: () => {
            this.snackBar.open('La sesión fue cerrada correctamente.', 'Cerrar', { duration: 2000 });
            this.router.navigate(['/login']);
          },
          error: (error: any) => {
            console.warn('Error al notificar cierre de sesión. Forzando logout local.', error);
            this.snackBar.open('La sesión fue cerrada correctamente.', 'Cerrar', { duration: 2000 });
            this.router.navigate(['/login']);
          }
        });
      }
    });
  }
}

import {Component, inject} from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
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
import {VecinoService} from '../../services/vecino-service';
import {Vecino} from '../../model/vecino';

@Component({
  selector: 'app-us07-vecino-perfil',
  templateUrl: './vecino-perfil.html',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatDividerModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatDialogModule,
  ],
  styleUrls: ['./vecino-perfil.css']
})
export class VecinoPerfil {

  private snackBar: MatSnackBar =inject(MatSnackBar);
  private router: Router = inject(Router);
  private vecinoService: VecinoService = inject(VecinoService);
  private userId = Number(localStorage.getItem('userId'));
  vecino = new Vecino();

  fotos = [
    '/icono-default.png',
    '/icono-1.png',
    '/icono-2.png',
    '/icono-3.png'
  ];

  constructor() {}

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cargarPerfil(): void {
    this.vecinoService.buscarPorID(this.userId).subscribe({
      next: (data) => {
        this.vecino = data;
        console.log(this.vecino);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  eliminarVisible = false;
  mostrarEliminar()
  {
    this.eliminarVisible = true;
  }
  cerrarEliminar(){
    this.eliminarVisible = false;
  }

  cerrarVisible = false;
  mostrarCerrar()
  {
    this.cerrarVisible = true;
  }
  cerrarCerrar(){
    this.cerrarVisible = false;
  }

  cerrarSesion() {
    this.router.navigate(['/login']);
  }

  eliminarCuenta(){
    this.vecinoService.eliminarCuenta(this.userId).subscribe({
      next: () => {
        this.snackBar.open('Cuenta eliminada correctamente.', 'Cerrar', { duration: 2000 });
        this.router.navigate(['/registro']);
      },
      error: (error: any) => {
        console.error('Error al eliminar la cuenta:', error);
      }
    });
  }
}

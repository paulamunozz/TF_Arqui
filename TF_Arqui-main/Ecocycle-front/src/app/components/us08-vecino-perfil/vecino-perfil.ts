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
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {VecinoService} from '../../services/vecino-service';
import {Vecino} from '../../model/vecino';
import {VecinoRetiroEvento} from '../us26-vecino-retiro-evento/vecino-retiro-evento';
import {VecinoEliminacionCuenta} from '../us07-vecino-eliminacion-cuenta/vecino-eliminacion-cuenta';
import {VecinoCerrarSesion} from '../us02-vecino-cerrar-sesion/vecino-cerrar-sesion';

@Component({
  selector: 'app-us08-vecino-perfil',
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
  dialog : MatDialog = inject(MatDialog);

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

  cerrarSesion(){
    const dialogoCerrar=this.dialog.open(VecinoCerrarSesion);
    dialogoCerrar.afterClosed().subscribe({
      next: (data) => {
        if (data){
          this.router.navigate(['/']);
        }
      }
    })
  }

  eliminarCuenta(){
    const dialogoEliminar=this.dialog.open(VecinoEliminacionCuenta);
    dialogoEliminar.afterClosed().subscribe({
      next: (data) => {
        if (data){
          this.vecinoService.eliminarCuenta(this.userId).subscribe({
            next: data => {
              this.snackBar.open('Cuenta eliminada correctamente.', 'Cerrar', { duration: 2000 });
              this.router.navigate(['/registro']);
            },
            error: error => {
              console.log(error);
            }
          })
        }
      }
    })
  }
}

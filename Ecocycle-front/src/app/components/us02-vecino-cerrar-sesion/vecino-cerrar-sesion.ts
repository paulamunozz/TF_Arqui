import { Component } from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from '@angular/material/dialog';

@Component({
  selector: 'app-us02-vecino-cerrar-sesion',
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
    MatDialogClose
  ],
  templateUrl: './vecino-cerrar-sesion.html',
  styleUrl: './vecino-cerrar-sesion.css',
})
export class VecinoCerrarSesion {

}
